package cn.blockforge.generated.carpetkarmaplus.mixin.lithium;

import cn.blockforge.generated.carpetkarmaplus.util.ContainerLimits;
import net.minecraft.class_1263;
import net.minecraft.class_1278;
import net.minecraft.class_1799;
import net.minecraft.class_2350;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   targets = {"net.caffeinemc.mods.lithium.common.hopper.HopperHelper"}
)
public abstract class HopperHelperMixin {
   @Inject(
      method = {"tryMoveSingleItem(Lnet/minecraft/class_1263;Lnet/minecraft/class_1799;Lnet/minecraft/class_2350;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void carpetKarmaPlus$limitTransferIntoContainers(
      class_1263 to, class_1799 stack, class_2350 direction, CallbackInfoReturnable<Boolean> cir
   ) {
      int limit = ContainerLimits.limitFor(to, stack);
      if (limit < 0) {
         return;
      }

      class_1278 sided = to instanceof class_1278 ? (class_1278)to : null;
      int[] slots = sided != null && direction != null ? sided.method_5494(direction) : null;
      int size = slots != null ? slots.length : to.method_5439();

      for (int index = 0; index < size; index++) {
         int slot = slots != null ? slots[index] : index;
         if (!to.method_5437(slot, stack)) {
            continue;
         }

         if (sided != null && !sided.method_5492(slot, stack, direction)) {
            continue;
         }

         class_1799 current = to.method_5438(slot);
         int already;
         if (current.method_7960()) {
            already = 0;
         } else {
            if (!class_1799.method_31577(current, stack)) {
               continue;
            }

            already = current.method_7947();
         }

         int space = limit - already;
         if (space <= 0) {
            continue;
         }

         // 漏斗每次操作只搬 1 个（和原版一致）。注意这里的 stack 是漏斗自己那一整摞，
         // 不是 1 个：Lithium 的 push 路径直接把机器人的库存摞传进来，
         // 所以绝不能用 stack.method_7947() 当搬运量，否则一次搬满一格。
         int moved = 1;
         if (current.method_7960()) {
            to.method_5447(slot, stack.method_7971(moved));
         } else {
            current.method_7933(moved);
            stack.method_7934(moved);
         }

         cir.setReturnValue(Boolean.TRUE);
         return;
      }

      cir.setReturnValue(Boolean.FALSE);
   }
}