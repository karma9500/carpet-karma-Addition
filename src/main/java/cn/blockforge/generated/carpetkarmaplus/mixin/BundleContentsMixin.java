package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.ShulkerStacks;
import net.minecraft.class_1799;
import net.minecraft.class_9276;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_9276.class})
public abstract class BundleContentsMixin {
   @Inject(
      method = {"method_57423"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void carpetKarmaPlus$shulkerFillsBundle(class_1799 var0, CallbackInfoReturnable<Fraction> var1) {
      if (ShulkerStacks.isEmptyShulkerBox(var0)) {
         var1.setReturnValue(Fraction.ONE);
      }
   }
}
