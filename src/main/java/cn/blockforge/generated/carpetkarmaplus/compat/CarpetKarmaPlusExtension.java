package cn.blockforge.generated.carpetkarmaplus.compat;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.api.settings.SettingsManager;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CarpetKarmaPlusExtension implements CarpetExtension {
   private static final Logger LOGGER = LoggerFactory.getLogger("carpet-karma-plus");
   private static boolean rulesParsed;

   private CarpetKarmaPlusExtension() {
   }

   public static void register() {
      CarpetServer.manageExtension(new CarpetKarmaPlusExtension());
   }

   public void onGameStarted() {
      if (!rulesParsed) {
         SettingsManager manager = CarpetServer.settingsManager;
         if (manager == null) {
            LOGGER.error("[carpet karma Addition] Carpet 的设置系统还没准备好，功能保持关闭");
         } else {
            try {
               manager.parseSettingsClass(KarmaRules.class);
               rulesParsed = true;
               LOGGER.info("[carpet karma Addition] 已向 /carpet 注册 CKP 分类规则，默认全部关闭");
            } catch (Throwable var3) {
               LOGGER.error("[carpet karma Addition] 规则注册失败，功能保持关闭", var3);
            }
         }
      }
   }

   public Map<String, String> canHasTranslations(String language) {
      return KarmaTranslations.forLanguage(language);
   }

   public String version() {
      return modVersion();
   }

   private static String modVersion() {
      return FabricLoader.getInstance()
         .getModContainer("carpet-karma-plus")
         .map(container -> container.getMetadata().getVersion().getFriendlyString())
         .orElse("1.0.0");
   }
}
