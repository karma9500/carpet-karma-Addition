package cn.blockforge.generated.carpetkarmaplus.command;

import cn.blockforge.generated.carpetkarmaplus.util.StackingOverrides;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import java.util.Map;
import java.util.StringJoiner;
import net.minecraft.class_1792;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2287;
import net.minecraft.class_2290;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_7157;
import net.minecraft.class_7923;

public final class StackCommand {
   private StackCommand() {
   }

   public static void register(CommandDispatcher<class_2168> dispatcher, class_7157 registryAccess) {
      dispatcher.register(
         class_2170.method_9247("stack")
            .requires(source -> source.method_9259(0))
            .then(
               class_2170.method_9247("set")
                  .then(
                     class_2170.method_9244("item", class_2287.method_9776(registryAccess))
                        .then(
                           class_2170.method_9244("count", IntegerArgumentType.integer(StackingOverrides.MIN, StackingOverrides.MAX))
                              .executes(StackCommand::setMax)
                        )
                  )
            )
            .then(
               class_2170.method_9247("remove")
                  .then(class_2170.method_9244("item", class_2287.method_9776(registryAccess)).executes(StackCommand::remove))
            )
            .then(class_2170.method_9247("list").executes(StackCommand::list))
      );
   }

   private static int setMax(CommandContext<class_2168> context) {
      class_2168 source = context.getSource();
      if (!Toggles.stackingSet()) {
         source.method_9213(literal("先开启规则：/carpet stackingSet true"));
         return 0;
      }

      class_1792 item = context.getArgument("item", class_2290.class).method_9785();
      int count = IntegerArgumentType.getInteger(context, "count");
      StackingOverrides.set(item, count);
      source.method_9226(
         () -> literal("已把 " + idOf(item) + " 的堆叠上限设为 " + count + "；只在玩家背包、假人背包和末影箱里生效，其它容器仍按原版"), false
      );
      return count;
   }

   private static int remove(CommandContext<class_2168> context) {
      class_2168 source = context.getSource();
      class_1792 item = context.getArgument("item", class_2290.class).method_9785();
      if (StackingOverrides.remove(item)) {
         source.method_9226(() -> literal("已把 " + idOf(item) + " 的堆叠上限恢复成原版"), false);
         return 1;
      }

      source.method_9213(literal(idOf(item) + " 本来就没有设置过堆叠上限"));
      return 0;
   }

   private static int list(CommandContext<class_2168> context) {
      class_2168 source = context.getSource();
      Map<class_1792, Integer> all = StackingOverrides.snapshot();
      if (all.isEmpty()) {
         source.method_9226(() -> literal("当前没有任何自定义堆叠上限"), false);
         return 0;
      }

      StringJoiner joiner = new StringJoiner("，");
      all.forEach((item, count) -> joiner.add(idOf(item) + "=" + count));
      source.method_9226(() -> literal("当前自定义堆叠上限：" + joiner), false);
      return all.size();
   }

   private static String idOf(class_1792 item) {
      class_2960 id = class_7923.field_41178.method_10221(item);
      return id == null ? String.valueOf(item) : id.toString();
   }

   private static class_2561 literal(String text) {
      return class_2561.method_43470(text);
   }
}