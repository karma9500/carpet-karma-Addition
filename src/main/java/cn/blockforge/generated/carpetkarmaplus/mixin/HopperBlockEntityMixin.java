package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.ContainerLimits;
import net.minecraft.class_1263;
import net.minecraft.class_1278;
import net.minecraft.class_1799;
import net.minecraft.class_2350;
import net.minecraft.class_2614;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_2614.class})
public abstract class HopperBlockEntityMixin {
   @Inject(
      method = {"method_11253(Lnet/minecraft/class_1263;Lnet/minecraft/class_1263;Lnet/minecraft/class_1799;ILnet/minecraft/class_2350;)Lnet/minecraft/class_1799;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void carpetkarmaplus$limitTransferIntoContainers(
      class_1263 from, class_1263 to, class_1799 stack, int slot, class_2350 side, CallbackInfoReturnable<class_1799> cir
   ) {
      int limit = ContainerLimits.limitFor(to, stack);
      if (limit < 0) {
         return;
      }

      if (!to.method_5437(slot, stack)) {
         return;
      }

      if (to instanceof class_1278 sided && !sided.method_5492(slot, stack, side)) {
         return;
      }

      class_1799 target = to.method_5438(slot);
      int already;
      if (target.method_7960()) {
         already = 0;
      } else {
         if (!class_1799.method_31577(target, stack)) {
            return;
         }

         already = target.method_7947();
      }

      int space = limit - already;
      if (space <= 0) {
         cir.setReturnValue(stack);
         return;
      }

      // 原版调用方传进来的本来就是 removeStack(slot, 1) 的 1 个，这里固定搬 1 个，
      // 避免被别的调用方（模组/旧版本）传整摞时一次搬一格。
      int moved = 1;
      if (target.method_7960()) {
         to.method_5447(slot, stack.method_46651(moved));
      } else {
         target.method_7933(moved);
      }

      stack.method_7934(moved);
      to.method_5431();
      cir.setReturnValue(stack);
   }
}