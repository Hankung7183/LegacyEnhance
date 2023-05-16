package me.hankung.legacyenhance.mixin.resolvebuttonclick;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.DeathScreen;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {

    @Shadow
    private int ticksSinceDeath;

    @Inject(method = "init", at = @At("HEAD"))
    private void legacy$allowClickable(CallbackInfo ci) {
        this.ticksSinceDeath = 0;
    }

}
