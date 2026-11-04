package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.ContainerLimits;
import cn.blockforge.generated.carpetkarmaplus.util.ShulkerStacks;
import cn.blockforge.generated.carpetkarmaplus.util.StackingOverrides;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1735.class})
public abstract class SlotMixin {
   /**
    * 玩家背包 / 假人背包 / 末影箱不用管：那边的上限就是 ItemStack.getMaxCount()。
    * 其它容器在这里把结果往下压到原版堆叠上限（空潜影盒压到 1），
    * 用 RETURN 注入是为了不破坏结果槽、盔甲槽这些子类自己的重写。
    */
   @Inject(
      method = {"method_7676(Lnet/minecraft/class_1799;)I"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void carpetkarmaplus$clampContainerSlot(class_1799 stack, CallbackInfoReturnable<Integer> cir) {
      class_1735 self = (class_1735)(Object)this;
      if (ContainerLimits.isPlayerSide(self.field_7871)) {
         return;
      }

      int limit;
      if (ShulkerStacks.isEmptyShulkerBox(stack)) {
         limit = 1;
      } else if (Toggles.stackingSet()) {
         limit = StackingOverrides.vanillaMax(stack);
      } else {
         return;
      }

      if (cir.getReturnValueI() > limit) {
         cir.setReturnValue(limit);
      }
   }
}