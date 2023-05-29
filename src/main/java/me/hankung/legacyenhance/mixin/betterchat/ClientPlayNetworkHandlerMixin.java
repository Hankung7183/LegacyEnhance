package me.hankung.legacyenhance.mixin.betterchat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    private String lastMessage = "";
    private int line, amount;

    @Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = true)
    private void legacy$compactchat(ChatMessageS2CPacket packet, CallbackInfo ci) {
        if (LegacyEnhance.CONFIG.betterchatCompact.get()) {
            Text message = packet.getMessage();
            ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
            if (lastMessage.equals(message.asUnformattedString())) {
                chatHud.removeMessage(line);
                amount++;
                lastMessage = message.asUnformattedString();
                message.append(Formatting.GRAY + " [x" + amount + "]");
            } else {
                amount = 1;
                lastMessage = message.asUnformattedString();
            }

            line++;

            chatHud.addMessage(message, line);

            if (line > 256) {
                line = 0;
            }

            ci.cancel();
        }
    }

}
