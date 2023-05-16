package me.hankung.legacyenhance.mixin.logspamfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;

@Mixin(targets = "net.minecraft.network.NetworkThreadUtils$1")
public class NetworkThreadUtilsMixin {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;apply(Lnet/minecraft/network/listener/PacketListener;)V"))
    private void patcher$ignorePacketsFromClosedConnections(Packet packet, PacketListener listener) {
        if (listener instanceof ClientPlayNetworkHandler) {
            if (((ClientPlayNetworkHandler) listener).getClientConnection().isOpen()) {
                packet.apply(listener);
            }
        } else {
            packet.apply(listener);
        }
    }
}
