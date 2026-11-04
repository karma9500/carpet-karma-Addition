package cn.blockforge.generated.carpetkarmaplus.util;

import net.minecraft.class_1263;
import net.minecraft.class_1661;
import net.minecraft.class_1730;
import net.minecraft.class_1799;

/**
 * 决定"某个容器的一格能放几个这些东西"。
 * 玩家背包、假人背包（同样是 PlayerInventory）和末影箱走自定义上限；
 * 其它一切容器一律按原版堆叠规则。
 */
public final class ContainerLimits {
   private ContainerLimits() {
   }

   public static boolean isPlayerSide(class_1263 inventory) {
      return inventory instanceof class_1661 || inventory instanceof class_1730;
   }

   /** 该容器一格的上限；返回 -1 表示不需要干预，交给原版逻辑。 */
   public static int limitFor(class_1263 inventory, class_1799 stack) {
      if (isPlayerSide(inventory)) {
         return Toggles.stackingSet() ? stack.method_7914() : -1;
      }

      if (ShulkerStacks.isEmptyShulkerBox(stack)) {
         return 1;
      }

      return Toggles.stackingSet() ? StackingOverrides.vanillaMax(stack) : -1;
   }
}