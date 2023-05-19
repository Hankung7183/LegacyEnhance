package me.hankung.legacyenhance.mixin.skiptwitch;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.TwitchStreamProvider;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Redirect(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/TwitchStreamProvider;update()V"))
    private void legacy$skipTwitchCode1(TwitchStreamProvider instance) {
    }

    @Redirect(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/TwitchStreamProvider;submit()V"))
    private void legacy$skipTwitchCode2(TwitchStreamProvider instance) {
    }

}
