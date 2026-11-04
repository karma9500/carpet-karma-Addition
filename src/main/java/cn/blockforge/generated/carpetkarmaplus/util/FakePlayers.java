package cn.blockforge.generated.carpetkarmaplus.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.class_1297;
import net.minecraft.class_1657;

public final class FakePlayers {
   private static final Set<Class<?>> CHECKED = ConcurrentHashMap.newKeySet();
   private static final Set<Class<?>> FAKE = ConcurrentHashMap.newKeySet();

   private FakePlayers() {
   }

   public static boolean isFakePlayer(class_1297 entity) {
      if (!(entity instanceof class_1657)) {
         return false;
      } else if (entity instanceof FakePlayer) {
         return true;
      } else {
         Class<?> type = entity.getClass();
         if (CHECKED.contains(type)) {
            return FAKE.contains(type);
         } else {
            boolean fake = false;

            for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
               String simpleName = current.getSimpleName();
               if (simpleName.equals("EntityPlayerMPFake") || simpleName.equals("FakePlayerEntity") || simpleName.equals("FakePlayer")) {
                  fake = true;
                  break;
               }
            }

            CHECKED.add(type);
            if (fake) {
               FAKE.add(type);
            }

            return fake;
         }
      }
   }
}
