package me.hankung.legacyenhance.mixin.betterchat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

@Mixin(ChatMessageC2SPacket.class)
public class ChatMessageC2SPacketMixin {
    @ModifyConstant(method = {"<init>(Ljava/lang/String;)V", "read"}, constant = @Constant(intValue = 100))
    private int legacy$useExtendedChatLength(int original) {
        return LegacyEnhance.maxChatLength;
    }
}
