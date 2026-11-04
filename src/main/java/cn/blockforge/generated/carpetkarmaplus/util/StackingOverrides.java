package cn.blockforge.generated.carpetkarmaplus.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_9334;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * /stack set|remove 写入的物品堆叠上限。
 * 只影响 ItemStack.getMaxCount 的结果，具体在哪些容器里生效由 ContainerLimits 决定。
 */
public final class StackingOverrides {
   public static final int MIN = 1;
   public static final int MAX = 64;
   private static final String FILE_NAME = "carpet-karma-plus-stacking.properties";
   private static final Logger LOGGER = LoggerFactory.getLogger("carpet-karma-plus");
   private static final Map<class_1792, Integer> OVERRIDES = new LinkedHashMap<>();
   private static boolean loaded;

   private StackingOverrides() {
   }

   private static Path file() {
      return FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
   }

   public static synchronized void load() {
      if (loaded) {
         return;
      }

      loaded = true;
      OVERRIDES.clear();
      Path path = file();
      if (!Files.isRegularFile(path)) {
         return;
      }

      try {
         for (String raw : Files.readAllLines(path, StandardCharsets.UTF_8)) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
               continue;
            }

            int split = line.indexOf('=');
            if (split <= 0) {
               continue;
            }

            class_2960 id = class_2960.method_12829(line.substring(0, split).trim());
            if (id == null) {
               continue;
            }

            int count;
            try {
               count = Integer.parseInt(line.substring(split + 1).trim());
            } catch (NumberFormatException ignored) {
               continue;
            }

            if (count < MIN) {
               continue;
            }

            class_1792 item = Compat.itemById(id);
            if (item != null) {
               OVERRIDES.put(item, count);
            }
         }
      } catch (IOException exception) {
         LOGGER.warn("[carpet karma Addition] 读取堆叠设置失败", exception);
      }
   }

   public static synchronized void save() {
      List<String> lines = new ArrayList<>();
      lines.add("# 由 /stack set <物品ID> <数量> 写入，可用 /stack remove <物品ID> 删除");

      for (Map.Entry<class_1792, Integer> entry : OVERRIDES.entrySet()) {
         class_2960 id = class_7923.field_41178.method_10221(entry.getKey());
         if (id != null) {
            lines.add(id + "=" + entry.getValue());
         }
      }

      try {
         Path path = file();
         Files.createDirectories(path.getParent());
         Files.write(path, lines, StandardCharsets.UTF_8);
      } catch (IOException exception) {
         LOGGER.warn("[carpet karma Addition] 保存堆叠设置失败", exception);
      }
   }

   public static int maxOf(class_1792 item) {
      if (!loaded) {
         load();
      }

      Integer value = OVERRIDES.get(item);
      return value == null ? 0 : value;
   }

   public static boolean has(class_1792 item) {
      return OVERRIDES.containsKey(item);
   }

   public static synchronized void set(class_1792 item, int count) {
      load();
      OVERRIDES.put(item, count);
      save();
   }

   public static synchronized boolean remove(class_1792 item) {
      load();
      if (OVERRIDES.remove(item) == null) {
         return false;
      }

      save();
      return true;
   }

   public static synchronized Map<class_1792, Integer> snapshot() {
      load();
      return new LinkedHashMap<>(OVERRIDES);
   }

   /** 这个堆的原版堆叠上限（绕过本模组对 getMaxCount 的改动）。 */
   public static int vanillaMax(class_1799 stack) {
      Integer value = (Integer)stack.method_58695(class_9334.field_50071, 1);
      return value == null ? 1 : value;
   }
}