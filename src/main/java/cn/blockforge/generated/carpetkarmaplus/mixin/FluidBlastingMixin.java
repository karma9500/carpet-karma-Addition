package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.FluidBlast;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_1541;
import net.minecraft.class_1701;
import net.minecraft.class_1922;
import net.minecraft.class_1927;
import net.minecraft.class_2338;
import net.minecraft.class_2404;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3610;
import net.minecraft.class_5362;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 流体爆破：TNT 爆炸时，把爆炸范围内的水和岩浆清掉。
 *
 * <p>这里只负责"记一笔"，**不改原版的返回值** —— 爆炸射线该怎么被水吸收还是怎么吸收，
 * 所以地面、黑曜石、远古残骸这些固体方块完全不受影响（这是和上一版最关键的差别：
 * 上一版把抗性改成 0，射线穿过水继续往下打，水下会被炸出一个大坑，TNT 脚下被掏空
 * 就会出现你说的穿模）。
 *
 * <p>真正的清除在 {@link FluidBlast#flush()}：本 tick 结束时按半径把流体方块换成空气。
 *
 * <p>{@code ExplosionBehavior#getBlastResistance} 在 1.21 ~ 1.21.10 全版本同名同签名
 * （1.21.2 的爆炸重构只动了 ExplosionImpl），所以这一条注入覆盖所有正式版。
 */
@Mixin({class_5362.class})
public abstract class FluidBlastingMixin {
   @Inject(
      method = {"method_29555(Lnet/minecraft/class_1927;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;Lnet/minecraft/class_2680;Lnet/minecraft/class_3610;)Ljava/util/Optional;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void carpetkarmaplus$blastFluids(
      class_1927 explosion, class_1922 view, class_2338 pos, class_2680 state, class_3610 fluid, CallbackInfoReturnable<Optional<Float>> cir
   ) {
      if (!Toggles.fluidBlasting()) {
         return;
      }

      Optional<Float> original = cir.getReturnValue();
      if (original == null || !original.isPresent()) {
         // 空气：原版这里不扣能量，也不做任何事
         return;
      }

      if (!isTnt(explosion)) {
         return;
      }

      // 只有真碰到流体才记一笔：水/岩浆本体的方块，或者含水方块
      boolean fluidBlock = state.method_26204() instanceof class_2404;
      boolean waterlogged = !fluid.method_15769();
      if (!fluidBlock && !waterlogged) {
         return;
      }

      if (view instanceof class_3218 world) {
         FluidBlast.record(explosion, world);
      }
   }

   private static boolean isTnt(class_1927 explosion) {
      class_1297 source = explosion.method_46406();
      return source instanceof class_1541 || source instanceof class_1701;
   }
}