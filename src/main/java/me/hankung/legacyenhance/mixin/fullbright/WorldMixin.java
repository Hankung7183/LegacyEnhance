package me.hankung.legacyenhance.mixin.fullbright;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.utils.FullbrightTicker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

@Mixin(World.class)
public class WorldMixin {

    @Inject(method = "method_8539", at = @At("HEAD"), cancellable = true)
    private void legacy$checkLightFor_fullbright(CallbackInfoReturnable<Boolean> cir) {
        if (this.legacy$checkFullbright()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = {
            "getLuminance", "getLightLevelWithCheck", "getLightAtPos",
            "getLightLevel(Lnet/minecraft/util/math/BlockPos;)I", "getLightLevel(Lnet/minecraft/util/math/BlockPos;Z)I"
    }, at = @At("HEAD"), cancellable = true)
    private void legacy$getLight_fullbright(CallbackInfoReturnable<Integer> cir) {
        if (this.legacy$checkFullbright()) {
            cir.setReturnValue(15);
        }
    }

    @Unique
    private boolean legacy$checkFullbright() {
        return MinecraftClient.getInstance().isOnThread() && FullbrightTicker.isFullbright();
    }

}
