package cn.blockforge.generated.carpetkarmaplus.compat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class KarmaTranslations {
   private static final String ID = "carpet";
   private static final Map<String, String> ZH_CN = new HashMap<>();
   private static final Map<String, String> ZH_TW = new HashMap<>();
   private static final Map<String, String> EN_US = new HashMap<>();

   private KarmaTranslations() {
   }

   private static void rule(Map<String, String> var0, String var1, String var2, String var3) {
      var0.put("carpet.rule." + var1 + ".name", var2);
      var0.put("carpet.rule." + var1 + ".desc", var3);
   }

   public static Map<String, String> forLanguage(String var0) {
      if (var0 == null) {
         return EN_US;
      } else {
         String var1 = var0.toLowerCase(Locale.ROOT);
         if (!var1.startsWith("zh_tw") && !var1.startsWith("zh_hk") && !var1.startsWith("zh_hant")) {
            return var1.startsWith("zh") ? ZH_CN : EN_US;
         } else {
            return ZH_TW;
         }
      }
   }

   static {
      rule(ZH_CN, "xpRepairWithExperience", "经验修补修复工具", "每次工具真要掉耐久时，先扣掉身上的经验把这次耐久损失抵掉（经验不够就照原版掉耐久）");
      rule(ZH_TW, "xpRepairWithExperience", "經驗修補修復工具", "每次工具真要掉耐久時，先扣掉身上的經驗把這次耐久損失抵掉（經驗不夠就照原版掉耐久）");
      rule(EN_US, "xpRepairWithExperience", "XP Mending Repair", "Whenever a mending tool would lose durability, pay with the player's experience instead");
      rule(ZH_CN, "fakePlayerNoCollision", "假人不与玩家碰撞", "Carpet 假人和真玩家贴在一起时，不会互相推开，也不算挤压伤害");
      rule(ZH_TW, "fakePlayerNoCollision", "假人不與玩家碰撞", "Carpet 假人和真玩家貼在一起時，不會互相推開，也不算擠壓傷害");
      rule(EN_US, "fakePlayerNoCollision", "Fake Player No Collision", "Carpet fake players and real players no longer push each other around");
      rule(
         ZH_CN, "stackableEmptyShulkerBoxes", "空潜影盒堆叠", "只有玩家背包（含快捷栏、副手）里能叠到 64 个；放进任何容器——箱子、木桶、漏斗、熔炉、发射器、投掷器、潜影盒、末影箱、船/矿车、收纳袋……——都只能一个占一格，漏斗/投掷器/发射器等自动输送的也一样"
      );
      rule(
         ZH_TW, "stackableEmptyShulkerBoxes", "空潛影盒堆疊", "只有玩家背包（含快捷欄、副手）裡能疊到 64 個；放進任何容器——箱子、木桶、漏斗、熔爐、發射器、投擲器、潛影盒、終界箱、船/礦車、收納袋……——都只能一個佔一格，漏斗/投擲器/發射器等自動輸送的也一樣"
      );
      rule(
         EN_US,
         "stackableEmptyShulkerBoxes",
         "Stackable Empty Shulkers",
         "Empty shulker boxes stack to 64 only inside the player's own inventory; in any other container (chests, barrels, hoppers, furnaces, dispensers, droppers, shulker boxes, ender chests, boats/minecarts, bundles...) they always take one slot each, including items moved by hoppers, droppers and dispensers"
      );
      rule(ZH_CN, "namedSoundSuppressor", "命名声音抑制器", "把箱子或陷阱箱命名为「声音抑制器」或 SoundSuppressor 再放下，周围 16 格内的幽匿感测体就不再响应振动");
      rule(ZH_TW, "namedSoundSuppressor", "命名聲音抑制器", "把箱子或陷阱箱命名為「聲音抑制器」或 SoundSuppressor 再放下，周圍 16 格內的幽匿感測體就不再回應振動");
      rule(
         EN_US,
         "namedSoundSuppressor",
         "Named Sound Suppressor",
         "Name a chest or trapped chest '声音抑制器' or 'SoundSuppressor'; sculk sensors within 16 blocks stop hearing vibrations"
      );
      rule(ZH_CN, "tntLooting", "TNT 掠夺", "TNT 炸死生物时按掠夺 III 计算掉落数量，不管 TNT 是谁点的");
      rule(ZH_TW, "tntLooting", "TNT 掠奪", "TNT 炸死生物時按掠奪 III 計算掉落數量，不管 TNT 是誰點的");
      rule(EN_US, "tntLooting", "TNT Looting", "Mobs killed by TNT drop loot as if the killer had Looting III");
      rule(ZH_CN, "tntWitherSkullDrop", "TNT 炸死凋灵骷髅掉头", "被 TNT 炸死的凋灵骷髅有 2.5% 概率掉头颅；同时开了 TNT 掠夺时提高到 5.5%");
      rule(ZH_TW, "tntWitherSkullDrop", "TNT 炸死凋零骷髏掉頭", "被 TNT 炸死的凋零骷髏有 2.5% 機率掉頭顱；同時開了 TNT 掠奪時提高到 5.5%");
      rule(EN_US, "tntWitherSkullDrop", "TNT Wither Skull Drop", "Wither skeletons killed by TNT drop a skull with 2.5% chance (5.5% when TNT Looting is on)");
      rule(ZH_CN, "boneMealAllGrowth", "催熟万物", "开启后，骨粉可催生发光地衣、藤蔓、五种活珊瑚、仙人掌和甘蔗。珊瑚需要处于水中；无年龄属性的方块会向周围随机生长。");
      rule(ZH_TW, "boneMealAllGrowth", "催熟萬物", "開啟後，骨粉可催生發光地衣、藤蔓、五種活珊瑚、仙人掌和甘蔗。珊瑚需要處於水中；沒有年齡屬性的方塊會向周圍隨機生長。");
      rule(
         EN_US,
         "boneMealAllGrowth",
         "Bonemeal All Growth",
         "Bonemeal grows glow lichen, vines, all five live coral types, cactus, and sugar cane. Coral must be underwater; blocks without age properties spread randomly to nearby positions."
      );
      rule(ZH_CN, "packedIceMeltsToWater", "浮冰化水", "开启后，用不带精准采集的工具破坏浮冰，浮冰会像冰一样化成水，且不再掉落浮冰；带精准采集的工具则照常完整掉落。下界里只会直接消失。");
      rule(ZH_TW, "packedIceMeltsToWater", "浮冰化水", "開啟後，用不帶精準採集的工具破壞浮冰，浮冰會像冰一樣化成水，且不再掉落浮冰；帶精準採集的工具則照常完整掉落。地獄裡只會直接消失。");
      rule(
         EN_US,
         "packedIceMeltsToWater",
         "Packed Ice Melts To Water",
         "Breaking packed ice without Silk Touch melts it into water and drops nothing, just like ice; with Silk Touch it still drops packed ice as usual. In the Nether it simply vanishes."
      );
      rule(
         ZH_CN,
         "stackingSet",
         "堆叠设置",
         "开启后用 /stack set <物品ID> <数量> 设置任意物品的堆叠上限（1~64，对原版不能堆叠的物品同样有效），这个上限只在玩家背包、假人背包和末影箱里生效，其它容器一律按原版；/stack remove <物品ID> 恢复原版，/stack list 查看当前设置"
      );
      rule(
         ZH_TW,
         "stackingSet",
         "堆疊設置",
         "開啟後用 /stack set <物品ID> <數量> 設置任意物品的堆疊上限（1~64，對原版不能堆疊的物品同樣有效），這個上限只在玩家背包、假人背包和終界箱裡生效，其它容器一律按原版；/stack remove <物品ID> 恢復原版，/stack list 查看當前設置"
      );
      rule(
         EN_US,
         "stackingSet",
         "Stacking Set",
         "While on, /stack set <item> <count> gives any item (including normally unstackable ones) a custom stack size from 1 to 64. It only applies inside the player's inventory, fake player inventories and ender chests; every other container keeps vanilla stacking rules. /stack remove <item> restores vanilla, /stack list shows current settings."
      );
      rule(
         ZH_CN,
         "liquidExplosion",
         "液体爆炸",
         "开启后，TNT 爆炸无视爆炸抗性：水和岩浆会被直接炸掉（原版它们抗性 100，会把爆炸能量吃光），黑曜石、远古残骸这类防爆方块也照样炸掉；基岩、屏障、命令方块这种「不可破坏」的方块仍然不动"
      );
      rule(
         ZH_TW,
         "liquidExplosion",
         "液體爆炸",
         "開啟後，TNT 爆炸無視爆炸抗性：水和岩漿會被直接炸掉（原版它們抗性 100，會把爆炸能量吃光），黑曜石、遠古殘骸這類防爆方塊也照樣炸掉；基岩、屏障、命令方塊這種「不可破壞」的方塊仍然不動"
      );
      rule(
         EN_US,
         "liquidExplosion",
         "Liquid Explosion",
         "TNT explosions ignore blast resistance: water and lava are destroyed outright (vanilla gives them resistance 100, which eats all the blast power), and blast-proof blocks such as obsidian and ancient debris break too. Truly unbreakable blocks (bedrock, barrier, command blocks) still survive."
      );
   }
}
