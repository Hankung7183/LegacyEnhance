package me.hankung.legacyenhance.mixin.ingamemultiplayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin {

	@Shadow
	public abstract void connect(ServerInfo entry);

	@Inject(method = {"connect(Lnet/minecraft/client/network/ServerInfo;)V"}, at = {@At("HEAD")}, cancellable = true)
	private void onConnect(ServerInfo entry, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.getCurrentServerEntry() != null && client.world == null && client.currentScreen instanceof ConnectScreen) {
			ci.cancel();
		}

		if (client.getCurrentServerEntry() != null && client.world != null) {
			client.world.disconnect();
			client.connect(null);
		}
	}

}
