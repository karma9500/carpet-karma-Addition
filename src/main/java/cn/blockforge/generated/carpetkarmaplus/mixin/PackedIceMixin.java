package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import java.util.Collections;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_9636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_2248.class})
public abstract class PackedIceMixin {
   @Inject(
      method = {"method_9609"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void carpetKarmaPlus$noPackedIceDrop(
      class_2680 var0, class_3218 var1, class_2338 var2, class_2586 var3, class_1297 var4, class_1799 var5, CallbackInfoReturnable<List<class_1799>> var6
   ) {
      if (Toggles.packedIceMeltsToWater()
         && var4 instanceof class_1657
         && var0.method_26204() == class_2246.field_10225
         && !class_1890.method_60138(var5, class_9636.field_51555)) {
         var6.setReturnValue(Collections.emptyList());
      }
   }

   @Inject(
      method = {"method_9556"},
      at = {@At("TAIL")}
   )
   private void carpetKarmaPlus$meltPackedIce(
      class_1937 var1, class_1657 var2, class_2338 var3, class_2680 var4, class_2586 var5, class_1799 var6, CallbackInfo var7
   ) {
      if (Toggles.packedIceMeltsToWater()
         && !var1.method_8608()
         && var4.method_26204() == class_2246.field_10225
         && !class_1890.method_60138(var6, class_9636.field_51555)) {
         if (var1.method_8597().comp_644()) {
            var1.method_8650(var3, false);
         } else {
            class_2680 var8 = var1.method_8320(var3.method_10074());
            if (var8.method_51366() || var8.method_51176()) {
               var1.method_8501(var3, class_2246.field_10382.method_9564());
            }
         }
      }
   }
}
