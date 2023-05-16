package me.hankung.legacyenhance.mixin.windowsimefix;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.screen.Screen;

@Mixin(Screen.class)
public class ScreenMixin {
    @Redirect(method = "handleKeyboard", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", remap = false))
    private boolean legacy$checkCharacter() {
        return Keyboard.getEventKey() == 0 && Keyboard.getEventCharacter() >= ' ' || Keyboard.getEventKeyState();
    }
}
