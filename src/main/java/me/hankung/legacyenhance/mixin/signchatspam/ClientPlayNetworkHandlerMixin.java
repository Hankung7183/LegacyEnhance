package me.hankung.legacyenhance.mixin.signchatspam;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.text.Text;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Redirect(method = "onUpdateSign", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=Unable to locate sign at ", ordinal = 0)), at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ClientPlayerEntity;sendMessage(Lnet/minecraft/text/Text;)V", ordinal = 0))
    private void legacy$removeDebugMessage(ClientPlayerEntity instance, Text text) {
    }
}
