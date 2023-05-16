package me.hankung.legacyenhance.mixin.resetunicodefont;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.LanguageOptionsScreen;

@Mixin(LanguageOptionsScreen.class)
public class LanguageOptionsScreenMixin extends Screen {
    @Override
    public void removed() {
        client.inGameHud.getChatHud().reset();
    }
}
