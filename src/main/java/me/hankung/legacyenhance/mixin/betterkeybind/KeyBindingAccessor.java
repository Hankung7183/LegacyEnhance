package me.hankung.legacyenhance.mixin.betterkeybind;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.option.KeyBinding;

@Mixin(KeyBinding.class)
public class KeyBindingAccessor {
    @Accessor
    static List<KeyBinding> getKEYS() {
        throw new UnsupportedOperationException("Mixin failed to inject!");
    }
}
