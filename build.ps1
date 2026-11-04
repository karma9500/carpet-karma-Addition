# carpet karma Addition 构建脚本
#
# 用法:
#   ./build.ps1 -McJar "C:\...\client-intermediary.jar" -LibDir "C:\...\libs"
#
# -McJar  : 对应 MC 版本的 intermediary 客户端 jar
#           （Fabric 通常缓存在 profiles\<实例>\.fabric\remappedJars\minecraft-<版本>-<loader>\ 下）
# -LibDir : 依赖 jar 目录，需要包含
#           sponge-mixin、fabric-loader、carpet、fabric-api（需解包，javac 读不了嵌套 jar）、
#           commons-lang3、asm、asm-tree、slf4j-api、brigadier、datafixerupper
#
# 产物: build/carpet-karma-addition.jar

param(
    [Parameter(Mandatory = $true)][string]$McJar,
    [Parameter(Mandatory = $false)][string]$LibDir = "",
    [string]$OutJar = "build/carpet-karma-addition.jar"
)

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Definition
$classes = Join-Path $root 'build/classes'
$javac = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin/javac.exe' } else { 'javac' }

if (-not (Test-Path $McJar)) { throw "找不到 MC jar: $McJar" }

if (Test-Path $classes) { Remove-Item $classes -Recurse -Force }
New-Item -ItemType Directory -Force $classes | Out-Null

$cp = $McJar
if ($LibDir -and (Test-Path $LibDir)) {
    $cp = $cp + ';' + ((Get-ChildItem -LiteralPath $LibDir -Filter '*.jar' | ForEach-Object { $_.FullName }) -join ';')
}

$sources = Get-ChildItem -Recurse -File (Join-Path $root 'src/main/java') -Filter '*.java' |
    Select-Object -ExpandProperty FullName

Write-Host "编译 $($sources.Count) 个源文件..."
& $javac --release 21 -encoding UTF-8 -proc:none -nowarn -cp $cp -d $classes @sources
if ($LASTEXITCODE -ne 0) { throw "javac 失败（退出码 $LASTEXITCODE）" }

# 打包
Add-Type -AssemblyName System.IO.Compression.FileSystem
Add-Type -AssemblyName System.IO.Compression
$outPath = Join-Path $root $OutJar
New-Item -ItemType Directory -Force (Split-Path $outPath) | Out-Null
if (Test-Path $outPath) { Remove-Item $outPath -Force }

$res = Join-Path $root 'src/main/resources'
$zip = [System.IO.Compression.ZipFile]::Open($outPath, [System.IO.Compression.ZipArchiveMode]::Create)
foreach ($item in @(
    @{ dir = $res; prefix = '' },
    @{ dir = $classes; prefix = '' }
)) {
    $base = (Get-Item $item.dir).FullName
    foreach ($f in (Get-ChildItem -Recurse -File $item.dir)) {
        $rel = $f.FullName.Substring($base.Length + 1).Replace('\', '/')
        $entry = $zip.CreateEntry($rel, [System.IO.Compression.CompressionLevel]::Optimal)
        $s = $entry.Open()
        $fs = [System.IO.File]::OpenRead($f.FullName)
        $fs.CopyTo($s)
        $fs.Dispose()
        $s.Dispose()
    }
}
$zip.Dispose()

Write-Host "完成: $outPath"
