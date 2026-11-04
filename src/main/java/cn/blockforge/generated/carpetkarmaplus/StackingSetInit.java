package cn.blockforge.generated.carpetkarmaplus;

import cn.blockforge.generated.carpetkarmaplus.command.StackCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

/** 负责读取堆叠设置文件并把 /stack 指令挂上去。 */
public final class StackingSetInit implements ModInitializer {
   public void onInitialize() {
      CommandRegistrationCallback.EVENT.register(
         (dispatcher, registryAccess, environment) -> StackCommand.register(dispatcher, registryAccess)
      );
   }
}