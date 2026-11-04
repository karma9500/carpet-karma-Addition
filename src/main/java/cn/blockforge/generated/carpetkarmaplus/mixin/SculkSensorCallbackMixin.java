package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Suppressors;
import net.minecraft.class_2338;
import net.minecraft.class_3218;
import net.minecraft.class_5712;
import net.minecraft.class_6880;
import net.minecraft.class_5712.class_7397;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   targets = {"net/minecraft/class_5704$class_8241"}
)
public abstract class SculkSensorCallbackMixin {
   @Inject(
      method = {"method_32970"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void carpetkarmaplus$muteNearSuppressor(
      class_3218 world, class_2338 pos, class_6880<class_5712> event, class_7397 emitter, CallbackInfoReturnable<Boolean> cir
   ) {
      if (Suppressors.suppresses(world, pos)) {
         cir.setReturnValue(false);
      }
   }
}
