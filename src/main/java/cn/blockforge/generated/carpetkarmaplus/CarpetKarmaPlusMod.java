package cn.blockforge.generated.carpetkarmaplus;

import cn.blockforge.generated.carpetkarmaplus.compat.CarpetKarmaPlusExtension;
import cn.blockforge.generated.carpetkarmaplus.util.Compat;
import cn.blockforge.generated.carpetkarmaplus.util.Toggles;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents.AfterDeath;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1541;
import net.minecraft.class_1639;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_3218;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CarpetKarmaPlusMod implements ModInitializer {
   public static final String MOD_ID = "carpet-karma-plus";
   public static final Logger LOGGER = LoggerFactory.getLogger("carpet-karma-plus");
   private static final float WITHER_SKULL_CHANCE = 0.025F;
   private static final float WITHER_SKULL_CHANCE_WITH_LOOTING = 0.055F;

   public void onInitialize() {
      if (!FabricLoader.getInstance().isModLoaded("carpet")) {
         LOGGER.warn("[Carpet Karma Addition] 没有找到 Carpet，本模组的功能将全部关闭");
      } else {
         try {
            CarpetKarmaPlusExtension.register();
         } catch (Throwable var2) {
            LOGGER.error("[Carpet Karma Addition] 没能把规则注册到 Carpet，功能将全部关闭", var2);
         }

         registerWitherSkullDrop();
      }
   }

   private static void registerWitherSkullDrop() {
      ServerLivingEntityEvents.AFTER_DEATH.register((AfterDeath)(entity, damageSource) -> {
         try {
            if (!(entity instanceof class_1639 skeleton)) {
               return;
            }

            if (!Toggles.tntWitherSkullDrop() || !killedByTnt(damageSource)) {
               return;
            }

            if (!(Compat.worldOf(skeleton) instanceof class_3218 world)) {
               return;
            }

            float chance = Toggles.tntLooting() ? 0.055F : 0.025F;
            if (world.method_8409().method_43057() < chance) {
               Compat.dropStack(skeleton, world, new class_1799(class_1802.field_8791));
            }
         } catch (Throwable var5) {
            LOGGER.warn("[Carpet Karma Addition] 凋灵骷髅头颅掉落处理失败", var5);
         }
      });
   }

   private static boolean killedByTnt(class_1282 damageSource) {
      if (damageSource == null) {
         return false;
      } else {
         class_1297 attacker = damageSource.method_5529();
         return attacker instanceof class_1541 ? true : damageSource.method_5526() instanceof class_1541;
      }
   }
}