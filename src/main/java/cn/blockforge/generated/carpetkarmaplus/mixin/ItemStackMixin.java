package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.ShulkerStacks;
import cn.blockforge.generated.carpetkarmaplus.util.StackingOverrides;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import cn.blockforge.generated.carpetkarmaplus.util.XpRepair;
import java.util.function.Consumer;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_3218;
import net.minecraft.class_3222;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1799.class})
public abstract class ItemStackMixin {
   @Inject(
      method = {"method_7914()I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void carpetkarmaplus$customMaxCount(CallbackInfoReturnable<Integer> cir) {
      class_1799 self = (class_1799)(Object)this;
      int vanilla = StackingOverrides.vanillaMax(self);
      int base = vanilla;
      if (Toggles.stackingSet()) {
         int override = StackingOverrides.maxOf(self.method_7909());
         if (override > 0) {
            base = override;
         }
      }

      if (base == vanilla && ShulkerStacks.isEmptyShulkerBox(self)) {
         base = ShulkerStacks.LIMIT;
      }

      int result = Math.max(base, Math.min(99, self.method_7947()));
      if (result != vanilla) {
         cir.setReturnValue(result);
      }
   }

   @Inject(
      method = {"method_61652(ILnet/minecraft/class_3222;Ljava/util/function/Consumer;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void carpetkarmaplus$payDurabilityWithExperience(int newDamage, class_3222 player, Consumer<class_1792> breakCallback, CallbackInfo ci) {
      if (XpRepair.payWithExperience((class_1799)(Object)this, player, newDamage)) {
         ci.cancel();
      }
   }

   /** 1.21 / 1.21.1 走的是带 ServerWorld 的旧 damage 重载。 */
   @Inject(
      method = {"method_7956(ILnet/minecraft/class_3218;Lnet/minecraft/class_3222;Ljava/util/function/Consumer;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void carpetkarmaplus$payDurabilityWithExperienceLegacy(
      int newDamage, class_3218 world, class_3222 player, Consumer<class_1792> breakCallback, CallbackInfo ci
   ) {
      if (XpRepair.payWithExperience((class_1799)(Object)this, player, newDamage)) {
         ci.cancel();
      }
   }
}