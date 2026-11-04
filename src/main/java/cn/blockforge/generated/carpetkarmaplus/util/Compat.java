package cn.blockforge.generated.carpetkarmaplus.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_169;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3218;
import net.minecraft.class_47;
import net.minecraft.class_7923;

/**
 * 1.21.x 跨版本兼容层。
 *
 * 1.21 到 1.21.10 之间 Minecraft 改过几个内部方法名（1.21.2 改掉落/伤害、1.21.5 改碰撞查询、
 * 1.21.9 改 Entity#getWorld 等）。这些方法名是编译期写死的，换版本就会 NoSuchMethodError。
 * 这里改成“按参数/返回类型反射找一次，然后缓存”，同一个 jar 就能在 1.21~1.21.10 上跑。
 */
public final class Compat {
   private static boolean worldResolved;
   private static Method worldGetter;
   private static boolean dropResolved;
   private static Method dropWithWorld;
   private static Method dropOnlyStack;
   private static boolean lootResolved;
   private static Method lootGetter;
   private static boolean registryResolved;
   private static Method registryGetter;
   private static boolean successResolved;
   private static class_1269 successValue;

   private Compat() {
   }

   /** Entity 的 getWorld / getEntityWorld。 */
   public static class_1937 worldOf(class_1297 entity) {
      if (!worldResolved) {
         worldResolved = true;
         worldGetter = find(
            class_1297.class, new Class<?>[0], class_1937.class, new String[]{"method_73183", "method_37908", "method_5770"}
         );
      }

      if (worldGetter == null || entity == null) {
         return null;
      }

      try {
         return (class_1937)worldGetter.invoke(entity);
      } catch (Throwable ignored) {
         return null;
      }
   }

   /** Entity#dropStack，1.21.2 之前没有 ServerWorld 参数。 */
   public static boolean dropStack(class_1297 entity, class_3218 world, class_1799 stack) {
      if (!dropResolved) {
         dropResolved = true;
         dropWithWorld = find(
            class_1297.class,
            new Class<?>[]{class_3218.class, class_1799.class},
            class_1542.class,
            new String[]{"method_5775", "method_5699"}
         );
         dropOnlyStack = find(class_1297.class, new Class<?>[]{class_1799.class}, class_1542.class, new String[]{"method_5775", "method_5699"});
      }

      try {
         if (dropWithWorld != null) {
            dropWithWorld.invoke(entity, world, stack);
            return true;
         }

         if (dropOnlyStack != null) {
            dropOnlyStack.invoke(entity, stack);
            return true;
         }
      } catch (Throwable ignored) {
      }

      return false;
   }

   /** LootContext#get(LootContextParameter)。 */
   public static Object lootGet(class_47 context, class_169 parameter) {
      if (!lootResolved) {
         lootResolved = true;
         lootGetter = find(class_47.class, new Class<?>[]{class_169.class}, Object.class, new String[]{"method_65013", "method_296"});
      }

      if (lootGetter == null || context == null) {
         return null;
      }

      try {
         return lootGetter.invoke(context, parameter);
      } catch (Throwable ignored) {
         return null;
      }
   }

   /** Registry#get(Identifier)，返回物品；找不到返回 null。 */
   public static class_1792 itemById(class_2960 id) {
      if (!registryResolved) {
         registryResolved = true;
         registryGetter = find(class_2378.class, new Class<?>[]{class_2960.class}, Object.class, new String[]{"method_63535", "method_10223"});
      }

      if (registryGetter == null || id == null) {
         return null;
      }

      try {
         Object value = registryGetter.invoke(class_7923.field_41178, id);
         return value instanceof class_1792 item ? item : null;
      } catch (Throwable ignored) {
         return null;
      }
   }

   /** ActionResult.SUCCESS（1.21.2 之后字段类型换了子类，只能反射读）。 */
   public static class_1269 actionSuccess() {
      if (!successResolved) {
         successResolved = true;
         try {
            Field field = class_1269.class.getField("field_5812");
            Object value = field.get(null);
            successValue = value instanceof class_1269 result ? result : null;
         } catch (Throwable ignored) {
            successValue = null;
         }
      }

      return successValue;
   }

   private static Method find(Class<?> owner, Class<?>[] params, Class<?> returnType, String[] names) {
      for (String name : names) {
         for (Method method : owner.getMethods()) {
            if (!method.getName().equals(name)) {
               continue;
            }

            Class<?>[] actual = method.getParameterTypes();
            if (actual.length != params.length) {
               continue;
            }

            boolean same = true;

            for (int i = 0; i < actual.length; i++) {
               if (actual[i] != params[i]) {
                  same = false;
                  break;
               }
            }

            if (!same) {
               continue;
            }

            if (returnType != null && !returnType.isAssignableFrom(method.getReturnType())) {
               continue;
            }

            try {
               method.setAccessible(true);
            } catch (Throwable ignored) {
            }

            return method;
         }
      }

      return null;
   }
}