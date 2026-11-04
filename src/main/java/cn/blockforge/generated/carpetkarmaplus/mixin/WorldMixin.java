package cn.blockforge.generated.carpetkarmaplus.mixin;

import cn.blockforge.generated.carpetkarmaplus.util.Collisions;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1937.class})
public abstract class WorldMixin {
   @Inject(
      method = {"method_66349(Lnet/minecraft/class_1297;Lnet/minecraft/class_238;)Ljava/util/List;"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 0
   )
   private void carpetutilities$skipFakePlayers(class_1297 entity, class_238 box, CallbackInfoReturnable<List<class_1297>> cir) {
      List<class_1297> crammed = (List<class_1297>)cir.getReturnValue();
      if (crammed != null && !crammed.isEmpty()) {
         List<class_1297> filtered = new ArrayList<>(crammed.size());
         boolean changed = false;

         for (class_1297 other : crammed) {
            if (Collisions.shouldCollide(entity, other)) {
               filtered.add(other);
            } else {
               changed = true;
            }
         }

         if (changed) {
            cir.setReturnValue(filtered);
         }
      }
   }
}
