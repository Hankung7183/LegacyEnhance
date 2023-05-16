package me.hankung.legacyenhance.mixin.savesettings;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;

@Mixin(SettingsScreen.class)
public class SettingsScreenMixin extends Screen {
    @Override
    public void removed() {
        client.options.save();
    }
}
