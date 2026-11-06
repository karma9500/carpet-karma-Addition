# carpet karma Addition

[Carpet](https://github.com/gnembon/fabric-carpet)（地毯模组）的附属，给生电/技术服补了 9 个开关，**全部走 `/carpet` 指令、全部分类在 `CKP` 下、默认全部关闭**。

支持 **Minecraft 1.21 ~ 1.21.10** 全部正式版，一个 jar 通吃。

---

## 功能一览

| 规则名（`/carpet` 里精确匹配，区分大小写） | 名称 | 作用 |
|---|---|---|
| `xpRepairWithExperience` | 经验修补修复工具 | 带经验修补的工具真要掉耐久时，先扣玩家经验把这次损耗抵掉（经验不够就照原版掉耐久） |
| `fakePlayerNoCollision` | 假人不与玩家碰撞 | Carpet 假人与真玩家贴在一起不会互相推开，也不算挤压伤害 |
| `stackableEmptyShulkerBoxes` | 空潜影盒堆叠 | 只有玩家背包（含快捷栏、副手）能叠到 64；任何容器里一格只放一个，漏斗/投掷器/发射器自动输送的也一样 |
| `namedSoundSuppressor` | 命名声音抑制器 | 把箱子/陷阱箱命名为「声音抑制器」或 `SoundSuppressor` 再放下，周围 16 格内幽匿感测体不再响应振动 |
| `tntLooting` | TNT 掠夺 | TNT 炸死的生物按掠夺 III 计算掉落数量，不管 TNT 是谁点的 |
| `tntWitherSkullDrop` | TNT 炸死凋灵骷髅掉头 | 被 TNT 炸死的凋灵骷髅 2.5% 掉头颅；同时开 TNT 掠夺时 5.5% |
| `boneMealAllGrowth` | 催熟万物 | 骨粉可催生发光地衣、藤蔓、五种活珊瑚、仙人掌、甘蔗；珊瑚需在水下 |
| `packedIceMeltsToWater` | 浮冰化水 | 不带精准采集挖浮冰会像冰一样化成水且不掉落；带精准采集照常掉落 |
| `stackingSet` | 堆叠设置 | 见下一节，用 `/stack` 自定义任意物品的堆叠上限（权限等级 0，人人可用） |
| `fluidBlasting` | 流体爆破 | TNT 爆炸清除范围内的水和岩浆，固体方块完全不受影响（见下节） |
| `noSnowAccumulation` | 下雪不积雪 | 下雪时不再生成新的雪层，已有雪层也不加厚；结冰、炼药锅不受影响（见下下节） |

开启方式：

```
/carpet boneMealAllGrowth true
/carpet setDefault stackingSet true     # 让新世界默认开启
```

> `/carpet` 本身的最低权限是 OP（Carpet 的全局设置 `carpetCommandPermissionLevel` 只提供 `ops / 2 / 4`）。想让普通玩家免 OP 也能用，就用 `setDefault` 把规则默认打开。

---

## 堆叠设置（`stackingSet`）

| 指令 | 作用 |
|---|---|
| `/stack set <物品ID> <数量>` | 设置堆叠上限，数量 1~64 |
| `/stack remove <物品ID>` | 删掉设置，恢复原版 |
| `/stack list` | 列出当前所有设置 |

```
/stack set minecraft:diamond_sword 16    # 16 把剑叠成一摞
/stack set minecraft:potion 8            # 药水也能叠（同效果才合并）
/stack remove minecraft:diamond_sword    # 变回原版 1
```

生效范围：

| 位置 | 行为 |
|---|---|
| 玩家背包（快捷栏、副手、盔甲槽） | 按 `/stack` 设的上限 |
| 假人背包（Carpet bot 用的就是 `PlayerInventory`） | 同上 |
| 末影箱 | 同上 |
| 其它一切容器（箱子、木桶、漏斗、熔炉、发射器、投掷器、潜影盒、船/矿车、合成格、收纳袋…） | **一律原版上限** |

设置保存在 `config/carpet-karma-plus-stacking.properties`，格式 `物品ID=数量`，可直接手改。

几个实现细节：

- 上限改小之后，背包里已经存在的超限堆**不会丢也不会被判定非法**（`getMaxCount` 对超限堆按实际数量兜底），拆开后就回不去了。
- 只有组件完全相同的物品才会合并，两把附魔/耐久不同的剑依然不叠。
- 容器侧是靠 `Slot#getMaxItemCount` 在**返回时向下压**实现的，不会破坏结果槽、盔甲槽这类子类自身的重写。
- 漏斗/投掷器这类自动输送单独处理（含 Lithium 的 `HopperHelper` 优化路径），否则会把超限堆推进容器。
- 漏斗**在容器之间搬运时每次仍然只走 1 个**（原版速度），本项目只决定「这一格还能不能再塞一个」，不改变吞吐。
- 漏斗**捡地上的掉落物仍是原版行为**：一次把那一摞吸进来，再按每格上限分配（空潜影盒依然一格 1 个，多出来的留在地上）。

---

## 流体爆破（`fluidBlasting`）

```
/carpet fluidBlasting true
```

开启后，**TNT 爆炸会把它范围内的水和岩浆清掉**，其它方块一点不受影响。

| 目标 | 原版 | 开启后 |
|---|---|---|
| 水、流动的水 | 抗性 100，把爆炸能量吃光，完全炸不动 | 半径内的水被清成空气 |
| 岩浆、流动的岩浆 | 同上 | 半径内的岩浆被清成空气 |
| 石头、泥土、黑曜石、远古残骸、基岩… | 正常 | **和原版一模一样，完全不受影响** |

实现上很克制：**爆炸本身完全按原版跑**（射线照旧被水吸收，不会有"水里 TNT 突然炸出一个大坑"这种副作用），我们在爆炸碰到流体时记下爆炸中心与威力，本 tick 结束时按半径「威力 × 2」（TNT 是 8 格）把范围内的流体方块清成空气。

几点说明：

- 只对 **TNT**（点燃的 TNT 实体、TNT 矿车）生效；苦力怕、床、末地水晶、风弹等其它爆炸不受影响。
- 水被清掉之后，相邻的水会按原版机制流回来（这是流体物理，不是没生效）。想彻底抽干一片水域，得连着炸或者先用方块封边；**岩浆流得慢**，炸完能维持很久的空腔，最直观。
- 深水里也照样生效：清理是按半径扫的，不依赖射线能打多远，所以海底爆炸同样会把那一团水清掉。
- **任何来源的 TNT 都算**：手动点燃、发射器点燃、活塞复制出来的、被传送过的、别的模组生成的，只要爆炸源是 TNT 实体就生效（TNT 矿车同样生效）。判定挂在 TNT 实体自己的爆炸入口上，不依赖爆炸行为对象，所以复制/传送过的 TNT 也不会漏。

---

## 下雪不积雪（`noSnowAccumulation`）

```
/carpet noSnowAccumulation true
```

开启后，下雪时地面不再形成新的雪层，已经存在的雪层也不会再变厚。

实现上只拦截了一个判断：原版 ServerWorld#tickIceAndSnow 里
if (maxLayers > 0 && biome.canSetSnow(this, pos)) 这行，我们把 canSetSnow 重定向成 alse。
所以：

- 雪：不再生成、不再加厚 ✅
- 结冰（水面冻成冰）：照常 ✅
- 炼药锅接水 / 接细雪：照常 ✅
- 其它天气行为：都不受影响 ✅

实测（无头 Fabric 1.21.10 服务端，雪原超平坦世界，/fillbiome 逐个切换群系，每群系探测 256 个方块位置）：

| 群系 | 规则关 | 规则开 |
|---|---|---|
| snowy_plains | 积雪 | **不积雪** |
| snowy_taiga | 积雪 | **不积雪** |
| ice_spikes | 积雪 | **不积雪** |
| snowy_beach | 积雪 | **不积雪** |
| snowy_slopes | 积雪 | **不积雪** |
| grove | 积雪 | **不积雪** |
| rozen_peaks | 积雪 | **不积雪** |
| jagged_peaks | 积雪 | **不积雪** |

8 个会下雪的群系全部覆盖。

---
## 安装

1. 装好对应 MC 版本的 [Fabric Loader](https://fabricmc.net/) 与 [Fabric API](https://modrinth.com/mod/fabric-api)
2. 装 [Carpet](https://modrinth.com/mod/carpet)
3. 把 `carpet-karma-addition-x.y.z.jar` 丢进 `mods/`
4. 可选：[Mod Menu](https://modrinth.com/mod/modmenu)（看封面/模组列表）、[Lithium](https://modrinth.com/mod/lithium)（漏斗优化兼容已内置）

---

## 版本支持

| MC | 状态 |
|---|---|
| 1.21 ~ 1.21.10 | 支持（依赖声明 `minecraft >= 1.21`） |

做法上：类名用 Fabric 的 **intermediary 中间名** 编译，再对跨版本改过名的成员用反射兜底（`util/Compat.java`）。已经做过全量静态核对——jar 里引用的 84 个 Minecraft 成员、11 个 mixin 注入点在 1.21 ~ 1.21.10 共 11 个正式版的映射表里全部存在。

已知的两处版本差异：

- `ItemStack#damage` 在 1.21.2 拆出了新重载，经验修补功能对 1.21/1.21.1 走的是旧的 `method_7956`，两个注入器都挂了。
- `World#getCrammedEntities` 是 1.21.5 才有的，所以「假人不与玩家碰撞」里的挤压列表过滤在 1.21 ~ 1.21.4 不生效；推挤过滤（`Entity#pushAwayFrom`）在所有版本都生效。

---

## 从源码构建

这不是 Gradle 工程（源码是从字节码还原后手工维护的），用 `build.ps1` 直接调 `javac`：

```powershell
./build.ps1 -McJar "C:\path\to\client-intermediary.jar" -LibDir "C:\path\to\libs"
```

- `-McJar`：对应 MC 版本的 **intermediary** 客户端 jar（Fabric 会在 `.fabric/remappedJars/` 下缓存一份）
- `-LibDir`：放着 `sponge-mixin`、`fabric-loader`、`carpet`、`fabric-api`（需解包，`javac` 读不了嵌套 jar）、`commons-lang3`、`asm` 的目录
- 产物：`build/carpet-karma-addition.jar`

---

## 目录结构

```
src/main/java/cn/blockforge/generated/carpetkarmaplus/
  CarpetKarmaPlusMod.java      主入口（注册 Carpet 扩展 + 凋灵骷髅掉头）
  StackingSetInit.java         /stack 指令入口
  command/StackCommand.java    set / remove / list
  compat/                      Carpet 规则、翻译、扩展、Lithium 插件
  Growth.java                  催熟万物的生长逻辑
  util/                        开关、容器上限、堆叠表、跨版本兼容层
  mixin/                       各功能的注入点
src/main/resources/            fabric.mod.json、mixin 配置、图标
```

新增一条规则只要动三处：`compat/KarmaRules.java`（字段 + `@Rule(categories={"CKP"})`）、`compat/KarmaTranslations.java`（名称与说明）、`util/Toggles.java`（统一开关）。

---

## 已知限制

- 堆叠设置存在本地配置文件，服务端和客户端各读各的。单机/局域网（客户端内置服务端同文件）没问题；**独立服 + 远程客户端**时客户端看到的堆叠数可能与服务端不一致（要做得加自定义封包同步）。
- `/stack` 是服务端指令，纯客户端装了不会出现在单人以外的服务器上。

---

## 许可证

MIT，见 [LICENSE](LICENSE)。

图标为使用者自备素材，如用于公开分发请自行确认素材授权。