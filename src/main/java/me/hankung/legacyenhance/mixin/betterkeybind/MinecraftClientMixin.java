package me.hankung.legacyenhance.mixin.betterkeybind;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "closeScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MouseInput;lockMouse()V"))
    private void legacy$makeKeysReRegister(CallbackInfo ci) {
        if (LegacyEnhance.CONFIG.miscellaneousBetterKeybind.get() && !MinecraftClient.IS_MAC) {
            updateKeyBindState();
        }
    }

    private void updateKeyBindState() {
        for (KeyBinding keybinding : KeyBindingAccessor.getKEYS()) {
            try {
                final int keyCode = keybinding.getCode();
                KeyBinding.setKeyPressed(keyCode, keyCode < 256 && Keyboard.isKeyDown(keyCode));
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }

}
