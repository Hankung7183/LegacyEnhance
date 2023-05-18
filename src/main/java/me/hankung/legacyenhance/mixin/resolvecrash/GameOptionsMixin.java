package me.hankung.legacyenhance.mixin.resolvecrash;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    /**
     * @author asbyth
     * @reason Resolve Chat Key bound to a unicode char causing crashes while creative inventory
     */
    @Overwrite
    public static boolean isPressed(KeyBinding key) {
        int keyCode = key.getCode();
        if (keyCode != 0 && keyCode < 256) {
            return keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
        } else {
            return false;
        }
    }

}
