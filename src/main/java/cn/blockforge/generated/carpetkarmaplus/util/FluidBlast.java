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
      recordAt(world, center.field_1352, center.field_1351, center.field_1350, explosion.method_55107());
   }

   /**
    * 按坐标记一次爆炸。
    *
    * <p>同一发 TNT 可能同时被"TNT 实体爆炸入口"和"爆炸行为对象"两处记到，
    * 这里用 2 格内的距离去重，避免同一发爆炸扫两遍。
    */
   public static void recordAt(class_3218 world, double x, double y, double z, float power) {
      List<double[]> list = PENDING.computeIfAbsent(world, key -> new ArrayList<>());
      for (double[] existing : list) {
         if (existing[3] == power) {
            double dx = existing[0] - x;
            double dy = existing[1] - y;
            double dz = existing[2] - z;
            if (dx * dx + dy * dy + dz * dz <= 4.0) {
               return;
            }
         }
      }

      list.add(new double[]{x, y, z, power});
   }

   /** 附近 3×3×3 内有没有流体方块（用于廉价判断，避免给整片地下做全量扫描）。 */
   public static boolean hasFluidNear(class_3218 world, double x, double y, double z) {
      int cx = (int)Math.floor(x);
      int cy = (int)Math.floor(y);
      int cz = (int)Math.floor(z);
      for (int dx = -1; dx <= 1; dx++) {
         for (int dy = -1; dy <= 1; dy++) {
            for (int dz = -1; dz <= 1; dz++) {
               class_2338 pos = class_2338.method_49637(cx + dx, cy + dy, cz + dz);
               if (world.method_8320(pos).method_26204() instanceof class_2404) {
                  return true;
               }
            }
         }
      }

      return false;
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