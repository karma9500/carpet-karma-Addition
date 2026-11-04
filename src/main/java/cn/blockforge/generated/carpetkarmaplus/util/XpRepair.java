package cn.blockforge.generated.carpetkarmaplus.util;

import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_3218;
import net.minecraft.class_3222;

public final class XpRepair {
   private XpRepair() {
   }

   public static boolean payWithExperience(class_1799 stack, class_3222 player, int newDamage) {
      if (Toggles.xpRepairWithExperience() && stack != null && !stack.method_7960() && player != null) {
         int currentDamage = stack.method_7919();
         int lost = newDamage - currentDamage;
         if (lost <= 0) {
            return false;
         } else if (player.method_56992()) {
            return false;
         } else {
            class_3218 world = player.method_51469();
            int perPoint = class_1890.method_60168(world, stack, 1);
            if (perPoint <= 1) {
               return false;
            } else {
               int cost = (lost + perPoint - 1) / perPoint;
               if (player.field_7495 < cost) {
                  return false;
               } else {
                  player.method_7255(-cost);
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }
}
