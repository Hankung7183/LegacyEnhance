package me.hankung.legacyenhance.mixin.betterchat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.gui.screen.ChatScreen;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @ModifyConstant(method = "init", constant = @Constant(intValue = 100))
    private int legacy$useExtendedChatLength(int original) {
        return LegacyEnhance.maxChatLength;
    }
}
