package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import net.minecraft.class_1959;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import net.minecraft.class_4538;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 下雪不积雪。
 *
 * <p>原版下雪成雪的入口是 {@code ServerWorld#tickIceAndSnow}（`method_52370`），
 * 里面这段：
 * <pre>
 * if (this.isRaining()) {
 *    int maxLayers = gameRules.getInt(snowAccumulationHeight);
 *    if (maxLayers > 0 &amp;&amp; biome.canSetSnow(this, pos)) { ...放雪/加厚... }
 *    // 后面是炼药锅接水/接细雪
 * }
 * </pre>
 *
 * <p>我们只把 {@code Biome#canSetSnow} 这个判断重定向成 false，
 * 于是"放雪/加厚"整段被跳过，而**结冰、炼药锅接水、其它天气行为一个都不动**。
 * 比直接 cancel 整个方法精确得多。
 */
@Mixin({class_3218.class})
public abstract class SnowAccumulationMixin {
   @Redirect(
      method = {"method_52370(Lnet/minecraft/class_2338;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/class_1959;method_8696(Lnet/minecraft/class_4538;Lnet/minecraft/class_2338;)Z"
      ),
      require = 0
   )
   private boolean carpetkarmaplus$noSnowAccumulation(class_1959 biome, class_4538 world, class_2338 pos) {
      if (Toggles.noSnowAccumulation()) {
         return false;
      }

      return biome.method_8696(world, pos);
   }
}