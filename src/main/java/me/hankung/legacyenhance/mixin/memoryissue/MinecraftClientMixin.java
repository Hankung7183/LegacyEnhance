package me.hankung.legacyenhance.mixin.memoryissue;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.world.ClientWorld;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    public ClientWorld world;

    @Shadow
    public GameRenderer gameRenderer;

    @Inject(method = "connect(Lnet/minecraft/client/world/ClientWorld;Ljava/lang/String;)V", at = @At("HEAD"))
    private void legacy$clearLoadedMaps(ClientWorld worldClientIn, String loadingMessage, CallbackInfo ci) {
        if (worldClientIn != this.world) {
            this.gameRenderer.getMapRenderer().clearStateTextures();
        }
    }

}
