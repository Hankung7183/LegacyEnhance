package me.hankung.legacyenhance.mixin.packetspam;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

@Mixin(targets = "net.minecraft.client.gui.screen.SoundsScreen$SoundButtonWidget")
public class SoundsScreenMixin {

    @Redirect(method = "mouseDragged(Lnet/minecraft/client/MinecraftClient;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;save()V"))
    private void legacy$cancelSaving(GameOptions instance) {
    }

    @Inject(method = "mouseReleased(II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V"))
    private void legacy$save(int mouseX, int mouseY, CallbackInfo ci) {
        MinecraftClient.getInstance().options.save();
    }

}
