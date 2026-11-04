package cn.blockforge.generated.carpetkarmaplus.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_2595;

public final class Suppressors {
   public static final int RADIUS = 16;
   private static final String[] NAMES = new String[]{"声音抑制器", "SoundSuppressor"};
   private static final Map<class_1937, Set<class_2338>> POSITIONS = Collections.synchronizedMap(new WeakHashMap<>());

   private Suppressors() {
   }

   public static boolean isSuppressorName(class_2561 name) {
      if (name == null) {
         return false;
      } else {
         String text = name.getString().trim();

         for (String valid : NAMES) {
            if (valid.equals(text)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isActive(class_1937 world, class_2338 pos) {
      return world != null && pos != null ? world.method_8321(pos) instanceof class_2595 chest && isSuppressorName(chest.method_5797()) : false;
   }

   public static void add(class_1937 world, class_2338 pos) {
      if (world != null && pos != null) {
         POSITIONS.computeIfAbsent(world, key -> ConcurrentHashMap.newKeySet()).add(pos.method_10062());
      }
   }

   public static boolean suppresses(class_1937 world, class_2338 pos) {
      if (Toggles.namedSoundSuppressor() && world != null && pos != null) {
         Set<class_2338> positions = POSITIONS.get(world);
         if (positions != null && !positions.isEmpty()) {
            int maxDistanceSquared = 256;
            boolean found = false;
            Set<class_2338> stale = null;

            for (class_2338 candidate : positions) {
               long dx = (long)candidate.method_10263() - pos.method_10263();
               long dy = (long)candidate.method_10264() - pos.method_10264();
               long dz = (long)candidate.method_10260() - pos.method_10260();
               if (dx * dx + dy * dy + dz * dz <= maxDistanceSquared && world.method_8477(candidate)) {
                  if (isActive(world, candidate)) {
                     found = true;
                  } else {
                     if (stale == null) {
                        stale = new HashSet<>();
                     }

                     stale.add(candidate);
                  }
               }
            }

            if (stale != null) {
               positions.removeAll(stale);
            }

            return found;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
