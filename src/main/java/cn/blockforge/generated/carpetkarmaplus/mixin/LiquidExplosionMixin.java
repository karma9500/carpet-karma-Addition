package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_1541;
import net.minecraft.class_1701;
import net.minecraft.class_1922;
import net.minecraft.class_1927;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_3610;
import net.minecraft.class_5362;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 液体爆炸：TNT 爆炸时把"爆炸抗性"（blast resistance）当 0 处理。
 *
 * <p>原版流程：爆炸沿射线每走 0.3 格就查一次
 * {@code ExplosionBehavior#getBlastResistance(explosion, world, pos, state, fluid)}，
 * 返回的值会扣掉爆炸能量（{@code power -= (resistance + 0.3) * 0.3}），
 * 抗性太高（水/岩浆 100、黑曜石 1200）能量当场就扣没了，
 * 于是"水根本炸不动、黑曜石炸不掉"。把返回值改成 0，
 * 水和岩浆就会像普通方块一样被炸掉，防爆方块也不再免疫。
 *
 * <p>这个方法是 1.21 ~ 1.21.10 全版本共用的（1.21.2 的爆炸重构没动它），
 * 所以一条注入就能覆盖所有正式版。
 */
@Mixin({class_5362.class})
public abstract class LiquidExplosionMixin {
   /** 大于这个抗性的一律当"不可破坏"放行原版：基岩、屏障、命令方块都是 3600000。 */
   private static final float INDESTRUCTIBLE = 100000.0F;

   @Inject(
      method = {"method_29555(Lnet/minecraft/class_1927;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;Lnet/minecraft/class_2680;Lnet/minecraft/class_3610;)Ljava/util/Optional;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void carpetkarmaplus$tntIgnoresBlastResistance(
      class_1927 explosion, class_1922 view, class_2338 pos, class_2680 state, class_3610 fluid, CallbackInfoReturnable<Optional<Float>> cir
   ) {
      if (!Toggles.liquidExplosion()) {
         return;
      }

      Optional<Float> original = cir.getReturnValue();
      if (original == null || !original.isPresent()) {
         // 空气：原版这里本来就不扣能量，保持原样（否则会缩小爆炸范围）
         return;
      }

      if (original.get() >= INDESTRUCTIBLE) {
         // 基岩/屏障/命令方块这类"根本不可破坏"的方块不碰
         return;
      }

      if (!isTnt(explosion)) {
         return;
      }

      cir.setReturnValue(Optional.of(0.0F));
   }

   private static boolean isTnt(class_1927 explosion) {
      class_1297 source = explosion.method_46406();
      return source instanceof class_1541 || source instanceof class_1701;
   }
}
