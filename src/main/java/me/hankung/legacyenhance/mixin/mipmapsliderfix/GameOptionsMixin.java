package me.hankung.legacyenhance.mixin.mipmapsliderfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.util.concurrent.ListenableFuture;

import me.hankung.legacyenhance.utils.mipmapslider.IGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements IGameOptions {

    @Shadow
    protected MinecraftClient client;

    private boolean legacy$needsResourceRefresh;

    @Override
    public void legacy$onSettingsGuiClosed() {
        if (legacy$needsResourceRefresh) {
            client.reloadResourcesConcurrently();
            legacy$needsResourceRefresh = false;
        }
    }

    @Redirect(method = "setValue", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;reloadResourcesConcurrently()Lcom/google/common/util/concurrent/ListenableFuture;"))
    private ListenableFuture<Object> legacy$reloadResourcesConcurrently(MinecraftClient instance) {
        legacy$needsResourceRefresh = true;
        return null;
    }

}
