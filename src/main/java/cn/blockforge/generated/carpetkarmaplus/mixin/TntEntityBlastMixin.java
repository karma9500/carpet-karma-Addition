package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Compat;
import cn.blockforge.generated.carpetkarmaplus.util.FluidBlast;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import java.lang.reflect.Field;
import net.minecraft.class_1297;
import net.minecraft.class_1541;
import net.minecraft.class_3218;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 流体爆破的"保底入口"：直接挂在 TNT 实体自己的爆炸方法上。
 *
 * <p>为什么不能只靠 {@code ExplosionBehavior#getBlastResistance}：
 * TNT 有好几种爆炸行为对象 —— 普通点燃用的是 {@code EntityExplosionBehavior}（会调 super，
 * 能被拦到），而**活塞复制/传送过的 TNT** 用的是 {@code TntEntity.TELEPORTED_EXPLOSION_BEHAVIOR}
 * （`class_1541$1`），它自己重写了那两个方法；TNT 矿车又是另一条路径。
 * 只要哪天某个行为对象不再调 super，按行为对象的注入就整个失效。
 *
 * <p>所以这里换成挂在 {@code TntEntity#explode}（`method_6971`）的 HEAD：
 * 不管这发 TNT 是点燃的、活塞复制的、传送的、还是别的模组生成的，
 * 只要它是 TNT 实体爆炸，就一定会经过这里。
 *
 * <p>为了不给无流体的场景做无用功，先做一次 3×3×3 的廉价检测；
 * 真正的全量扫描仍然只在 {@link FluidBlast#flush()} 里按半径做。
 */
@Mixin({class_1541.class})
public abstract class TntEntityBlastMixin {
   private static final float DEFAULT_POWER = 4.0F;
   private static Field powerField;
   private static boolean powerResolved;

   @Inject(method = {"method_6971"}, at = {@At("HEAD")}, require = 0)
   private void carpetkarmaplus$recordTntBlast(CallbackInfo ci) {
      try {
         if (!Toggles.fluidBlasting()) {
            return;
         }

         class_1297 self = (class_1297)(Object)this;
         if (!(Compat.worldOf(self) instanceof class_3218 world)) {
            return;
         }

         double x = self.method_23317();
         double y = self.method_23318();
         double z = self.method_23321();
         if (!FluidBlast.hasFluidNear(world, x, y, z)) {
            return;
         }

         FluidBlast.recordAt(world, x, y + 0.0625, z, power(self));
      } catch (Throwable ignored) {
         // 保底入口出问题不能影响 TNT 正常爆炸
      }
   }

   /** 读取 TNT 自己的爆炸威力（`explosion_power`，默认 4）。读不到就用默认值。 */
   private static float power(class_1297 entity) {
      if (!powerResolved) {
         powerResolved = true;
         try {
            powerField = class_1541.class.getDeclaredField("field_52496");
            powerField.setAccessible(true);
         } catch (Throwable ignored) {
            powerField = null;
         }
      }

      if (powerField != null) {
         try {
            return powerField.getFloat(entity);
         } catch (Throwable ignored) {
            return DEFAULT_POWER;
         }
      }

      return DEFAULT_POWER;
   }
}