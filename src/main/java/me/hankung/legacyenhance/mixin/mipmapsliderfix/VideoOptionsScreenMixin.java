package me.hankung.legacyenhance.mixin.mipmapsliderfix;

import org.spongepowered.asm.mixin.Mixin;

import me.hankung.legacyenhance.utils.mipmapslider.IGameOptions;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenMixin extends Screen {
    @Override
    public void removed() {
        super.removed();
        ((IGameOptions) client.options).legacy$onSettingsGuiClosed();
    }
}
