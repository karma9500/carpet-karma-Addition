package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Collisions;
import net.minecraft.class_1297;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_1297.class})
public abstract class EntityMixin {
   @Inject(
      method = {"method_5697(Lnet/minecraft/class_1297;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void carpetutilities$skipFakePlayerPush(class_1297 other, CallbackInfo ci) {
      class_1297 self = (class_1297)(Object)this;
      if (!Collisions.shouldCollide(self, other)) {
         ci.cancel();
      }
   }
}
