package cn.blockforge.generated.carpetkarmaplus.util;

import cn.blockforge.generated.carpetkarmaplus.compat.KarmaRules;
import net.fabricmc.loader.api.FabricLoader;

public final class Toggles {
   private static final boolean CARPET_PRESENT = FabricLoader.getInstance().isModLoaded("carpet");

   private Toggles() {
   }

   public static boolean xpRepairWithExperience() {
      return CARPET_PRESENT && KarmaRules.xpRepairWithExperience;
   }

   public static boolean fakePlayerNoCollision() {
      return CARPET_PRESENT && KarmaRules.fakePlayerNoCollision;
   }

   public static boolean stackableEmptyShulkerBoxes() {
      return CARPET_PRESENT && KarmaRules.stackableEmptyShulkerBoxes;
   }

   public static boolean namedSoundSuppressor() {
      return CARPET_PRESENT && KarmaRules.namedSoundSuppressor;
   }

   public static boolean tntLooting() {
      return CARPET_PRESENT && KarmaRules.tntLooting;
   }

   public static boolean tntWitherSkullDrop() {
      return CARPET_PRESENT && KarmaRules.tntWitherSkullDrop;
   }

   public static boolean packedIceMeltsToWater() {
      return CARPET_PRESENT && KarmaRules.packedIceMeltsToWater;
   }

   public static boolean stackingSet() {
      return CARPET_PRESENT && KarmaRules.stackingSet;
   }

   public static boolean liquidExplosion() {
      return CARPET_PRESENT && KarmaRules.liquidExplosion;
   }
}
