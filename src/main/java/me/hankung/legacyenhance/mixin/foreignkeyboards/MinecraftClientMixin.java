package me.hankung.legacyenhance.mixin.foreignkeyboards;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(method = "handleKeyInput", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventCharacter()C", remap = false))
    private char legacy$resolveForeignKeyboards() {
        return (char) (Keyboard.getEventCharacter() + 256);
    }
}
