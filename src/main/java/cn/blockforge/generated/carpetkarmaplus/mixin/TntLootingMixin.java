package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Compat;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import net.minecraft.class_125;
import net.minecraft.class_1541;
import net.minecraft.class_1799;
import net.minecraft.class_181;
import net.minecraft.class_47;
import net.minecraft.class_5658;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_125.class})
public abstract class TntLootingMixin {
   private static final int TNT_LOOTING_LEVEL = 3;
   @Shadow
   private class_5658 field_1082;
   @Shadow
   private int field_1083;

   @Inject(
      method = {"method_522(Lnet/minecraft/class_1799;Lnet/minecraft/class_47;)Lnet/minecraft/class_1799;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void carpetkarmaplus$tntLooting(class_1799 stack, class_47 context, CallbackInfoReturnable<class_1799> cir) {
      if (Toggles.tntLooting()) {
         if (Compat.lootGet(context, class_181.field_1230) instanceof class_1541) {
            int extra = Math.round(3.0F * this.field_1082.method_32454(context));
            stack.method_7933(extra);
            if (this.field_1083 > 0) {
               stack.method_58408(this.field_1083);
            }

            cir.setReturnValue(stack);
         }
      }
   }
}