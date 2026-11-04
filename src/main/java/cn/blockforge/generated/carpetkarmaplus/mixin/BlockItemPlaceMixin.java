package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Compat;
import cn.blockforge.generated.carpetkarmaplus.util.Suppressors;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import net.minecraft.class_1269;
import net.minecraft.class_1747;
import net.minecraft.class_1750;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1747.class})
public abstract class BlockItemPlaceMixin {
   @Inject(
      method = {"method_7712(Lnet/minecraft/class_1750;)Lnet/minecraft/class_1269;"},
      at = {@At("RETURN")}
   )
   private void carpetkarmaplus$trackSoundSuppressor(class_1750 context, CallbackInfoReturnable<class_1269> cir) {
      if (Toggles.namedSoundSuppressor()) {
         class_1269 success = Compat.actionSuccess();
         if (success != null && cir.getReturnValue() == success) {
            class_1937 world = context.method_8045();
            if (!world.method_8608()) {
               class_2338 pos = context.method_8037();
               if (Suppressors.isActive(world, pos)) {
                  Suppressors.add(world, pos);
               }
            }
         }
      }
   }
}