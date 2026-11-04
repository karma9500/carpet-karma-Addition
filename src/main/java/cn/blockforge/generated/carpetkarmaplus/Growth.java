package cn.blockforge.generated.carpetkarmaplus;

import cn.blockforge.generated.carpetkarmaplus.compat.KarmaRules;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2266;
import net.minecraft.class_2297;
import net.minecraft.class_2299;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2523;
import net.minecraft.class_2541;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3486;
import net.minecraft.class_5777;

public final class Growth {
   private Growth() {
   }

   private static boolean isWater(class_1937 var0, class_2338 var1) {
      return var0.method_8320(var1).method_26227().method_15767(class_3486.field_15517);
   }

   private static boolean hasWaterAround(class_1937 var0, class_2338 var1) {
      for (class_2350 var5 : class_2350.values()) {
         if (isWater(var0, var1.method_10093(var5))) {
            return true;
         }
      }

      return false;
   }

   private static boolean canGrowInto(class_2680 var0, class_1937 var1, class_2338 var2, boolean var3) {
      class_2680 var4 = var1.method_8320(var2);
      boolean var5 = var4.method_26215() || var3 && var4.method_26227().method_15767(class_3486.field_15517);
      return var5 && var0.method_26184(var1, var2);
   }

   private static boolean growCaneOrCactus(class_2680 var0, class_2248 var1, class_1937 var2, class_2338 var3, boolean var4) {
      class_2338 var5 = var3;

      for (int var6 = 0; var6 < 256; var6++) {
         class_2338 var7 = var5.method_10093(class_2350.field_11033);
         if (var2.method_8320(var7).method_26204() != var1) {
            break;
         }

         var5 = var7;
      }

      class_2338 var10 = var5;

      int var11;
      for (var11 = 1; var11 < 3; var11++) {
         class_2338 var8 = var10.method_10084();
         if (var2.method_8320(var8).method_26204() != var1) {
            break;
         }

         var10 = var8;
      }

      if (var11 >= 3) {
         return false;
      } else {
         class_2338 var12 = var10.method_10084();
         if (var2.method_8320(var12).method_26215() && var0.method_26184(var2, var12)) {
            class_2680 var9 = var4 ? (class_2680)var0.method_11657(class_2266.field_10709, 0) : (class_2680)var0.method_11657(class_2523.field_11610, 0);
            if (!var2.method_8652(var12, var9, 3)) {
               return false;
            } else {
               var2.method_8652(var3, var9, 3);
               return true;
            }
         } else {
            return false;
         }
      }
   }

   private static boolean growLichen(class_2680 var0, class_1937 var1, class_2338 var2, ThreadLocalRandom var3) {
      ArrayList<class_2338> var4 = new ArrayList<>();

      for (int var5 = -2; var5 <= 2; var5++) {
         for (int var6 = -2; var6 <= 2; var6++) {
            if (var5 != 0 || var6 != 0) {
               var4.add(var2.method_10069(var5, 0, var6));
            }
         }
      }

      Collections.shuffle(var4, (Random)var3);
      int var9 = var3.nextInt(1, 3);
      int var10 = 0;

      for (class_2338 var8 : var4) {
         if (canGrowInto(var0, var1, var8, true) && var1.method_8652(var8, var0, 3)) {
            if (++var10 >= var9) {
               return true;
            }
         }
      }

      return var10 > 0;
   }

   private static boolean growVineDown(class_2680 var0, class_1937 var1, class_2338 var2) {
      class_2338 var3 = var2.method_10093(class_2350.field_11033);
      return canGrowInto(var0, var1, var3, false) && var1.method_8652(var3, var0, 3);
   }

   private static boolean growCoralFan(class_2680 var0, class_1937 var1, class_2338 var2, ThreadLocalRandom var3) {
      ArrayList<class_2338> var4 = new ArrayList<>();

      for (int var5 = -1; var5 <= 1; var5++) {
         for (int var6 = -1; var6 <= 1; var6++) {
            if (var5 != 0 || var6 != 0) {
               var4.add(var2.method_10069(var5, 0, var6));
            }
         }
      }

      Collections.shuffle(var4, (Random)var3);
      int var9 = var3.nextInt(1, 3);
      int var10 = 0;

      for (class_2338 var8 : var4) {
         if (isWater(var1, var8) && hasWaterAround(var1, var8) && var0.method_26184(var1, var8) && var1.method_8652(var8, var0, 3)) {
            if (++var10 >= var9) {
               return true;
            }
         }
      }

      return var10 > 0;
   }

   public static boolean boneMeal(class_1799 var0, class_1937 var1, class_2338 var2) {
      if (KarmaRules.boneMealAllGrowth && var1 != null) {
         class_2680 var3 = var1.method_8320(var2);
         class_2248 var4 = var3.method_26204();
         boolean var5 = var4 instanceof class_2266;
         boolean var6 = var4 instanceof class_2523;
         boolean var7 = var4 instanceof class_5777;
         boolean var8 = var4 instanceof class_2541;
         boolean var9 = var4 instanceof class_2297 || var4 instanceof class_2299;
         if (!var5 && !var6 && !var7 && !var8 && !var9) {
            return false;
         } else if (var9 && !isWater(var1, var2) && !hasWaterAround(var1, var2)) {
            return false;
         } else if (!(var1 instanceof class_3218)) {
            return true;
         } else if (!var5 && !var6) {
            ThreadLocalRandom var10 = ThreadLocalRandom.current();
            if (var7) {
               return growLichen(var3, var1, var2, var10);
            } else {
               return var8 ? growVineDown(var3, var1, var2) : growCoralFan(var3, var1, var2, var10);
            }
         } else {
            return growCaneOrCactus(var3, var4, var1, var2, var5);
         }
      } else {
         return false;
      }
   }
}
