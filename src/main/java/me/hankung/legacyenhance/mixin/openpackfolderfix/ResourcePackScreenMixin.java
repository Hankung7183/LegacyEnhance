package me.hankung.legacyenhance.mixin.openpackfolderfix;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ResourcePackScreen;

import java.awt.Desktop;

@Mixin(ResourcePackScreen.class)
public class ResourcePackScreenMixin {

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Ljava/lang/Runtime;getRuntime()Ljava/lang/Runtime;", ordinal = 1, shift = At.Shift.BEFORE), cancellable = true)
    private void legacy$fixFolderOpening(CallbackInfo ci) throws IOException {
        Desktop.getDesktop().open(MinecraftClient.getInstance().getResourcePackLoader().getResourcePackDir());
        ci.cancel();
    }

}
