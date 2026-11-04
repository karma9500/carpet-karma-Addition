package cn.blockforge.generated.carpetkarmaplus.util;

import net.minecraft.class_1747;
import net.minecraft.class_1799;
import net.minecraft.class_2480;
import net.minecraft.class_9288;
import net.minecraft.class_9334;

public final class ShulkerStacks {
   public static final int LIMIT = 64;

   private ShulkerStacks() {
   }

   public static boolean isEmptyShulkerBox(class_1799 stack) {
      if (Toggles.stackableEmptyShulkerBoxes() && stack != null && !stack.method_7960()) {
         if (stack.method_7909() instanceof class_1747 blockItem && blockItem.method_7711() instanceof class_2480) {
            class_9288 contents = (class_9288)stack.method_58694(class_9334.field_49622);
            return contents == null || contents.method_59712().findAny().isEmpty();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
