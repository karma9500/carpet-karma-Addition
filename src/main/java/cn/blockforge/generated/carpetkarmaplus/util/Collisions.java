package cn.blockforge.generated.carpetkarmaplus.util;

import net.minecraft.class_1297;
import net.minecraft.class_1657;

public final class Collisions {
   private Collisions() {
   }

   public static boolean shouldCollide(class_1297 moving, class_1297 other) {
      if (moving == null || other == null || moving == other) {
         return true;
      } else if (!Toggles.fakePlayerNoCollision()) {
         return true;
      } else {
         boolean movingFake = FakePlayers.isFakePlayer(moving);
         boolean otherFake = FakePlayers.isFakePlayer(other);
         if (movingFake == otherFake) {
            return true;
         } else {
            class_1297 real = movingFake ? other : moving;
            return !(real instanceof class_1657);
         }
      }
   }
}
