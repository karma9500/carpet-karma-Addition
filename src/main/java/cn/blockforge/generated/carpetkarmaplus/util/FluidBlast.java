package cn.blockforge.generated.carpetkarmaplus.util;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_1927;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2404;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3218;

/**
 * 流体爆破的落地实现。
 *
 * <p>爆炸本身完全走原版（射线照旧被水吸收，所以地面、黑曜石这些固体方块
 * 一点都不会被牵连），我们在爆炸发生时记下中心与威力，等到这一 tick 结束
 * 再把半径内的水/岩浆方块清成空气。
 *
 * <p>半径 = 威力 × 2（TNT 威力 4 → 半径 8 格），和原版爆炸的可见范围一致。
 */
public final class FluidBlast {
   private static final Map<class_3218, List<double[]>> PENDING = new IdentityHashMap<>();

   private FluidBlast() {
   }

   /** 记下一次爆炸；同一个 tick 里同一位置同威力只记一次。 */
   public static void record(class_1927 explosion, class_3218 world) {
      class_243 center = explosion.method_55109();
      float power = explosion.method_55107();
      List<double[]> list = PENDING.computeIfAbsent(world, key -> new ArrayList<>());
      for (double[] existing : list) {
         if (existing[0] == center.field_1352
            && existing[1] == center.field_1351
            && existing[2] == center.field_1350
            && existing[3] == power) {
            return;
         }
      }

      list.add(new double[]{center.field_1352, center.field_1351, center.field_1350, power});
   }

   /** 每 tick 结束时清空待处理队列。 */
   public static void flush() {
      if (PENDING.isEmpty()) {
         return;
      }

      List<Map.Entry<class_3218, List<double[]>>> entries = new ArrayList<>(PENDING.entrySet());
      PENDING.clear();
      for (Map.Entry<class_3218, List<double[]>> entry : entries) {
         clear(entry.getKey(), entry.getValue());
      }
   }

   private static void clear(class_3218 world, List<double[]> blasts) {
      class_2680 air = class_2246.field_10124.method_9564();
      for (double[] blast : blasts) {
         int radius = Math.max(1, (int)Math.round(blast[3] * 2.0));
         int cx = (int)Math.floor(blast[0]);
         int cy = (int)Math.floor(blast[1]);
         int cz = (int)Math.floor(blast[2]);

         for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
               // 没加载的区块直接跳过，避免卡服
               if (world.method_8497(x >> 4, z >> 4) == null) {
                  continue;
               }

               for (int y = cy - radius; y <= cy + radius; y++) {
                  class_2338 pos = class_2338.method_49637(x, y, z);
                  if (world.method_8320(pos).method_26204() instanceof class_2404) {
                     world.method_8501(pos, air);
                  }
               }
            }
         }
      }
   }
}