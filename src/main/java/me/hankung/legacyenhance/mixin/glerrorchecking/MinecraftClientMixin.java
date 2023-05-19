package me.hankung.legacyenhance.mixin.glerrorchecking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    private boolean glErrors;

    @Inject(method = "initializeGame", at = @At("TAIL"))
    private void legacy$disableGlErrorChecking(CallbackInfo ci) {
        this.glErrors = false;
    }

}
