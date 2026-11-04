package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.Growth;
import net.minecraft.class_1752;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1752.class})
public abstract class BoneMealMixin {
   @Inject(
      method = {"method_7720"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void carpetKarmaPlus$boneMealAllGrowth(class_1799 var0, class_1937 var1, class_2338 var2, CallbackInfoReturnable<Boolean> var3) {
      if (Growth.boneMeal(var0, var1, var2)) {
         if (var1 instanceof class_3218) {
            var0.method_7934(1);
         }

         var3.setReturnValue(Boolean.TRUE);
      }
   }
}
