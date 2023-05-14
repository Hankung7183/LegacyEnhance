package me.hankung.legacyenhance.mixin.downscaletexture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.resource.AbstractFileResourcePack;

@Mixin(AbstractFileResourcePack.class)
public abstract class AbstractFileResourcePackMixin {
    
    @Shadow protected abstract InputStream openFile(String name) throws IOException;

    @Inject(method = "getIcon", at = @At("HEAD"), cancellable = true)
    private void downscalePackImage(CallbackInfoReturnable<BufferedImage> cir) throws IOException {
        if (!LegacyEnhance.CONFIG.performanceDownscaleTexture.get()) return;

        BufferedImage image = TextureUtil.create(this.openFile("pack.png"));
        if (image == null) {
            cir.setReturnValue(null);
            return;
        }

        if (image.getWidth() <= 64 && image.getHeight() <= 64) {
            cir.setReturnValue(image);
            return;
        }

        BufferedImage downscaledIcon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = downscaledIcon.getGraphics();
        graphics.drawImage(image, 0, 0, 64, 64, null);
        graphics.dispose();
        cir.setReturnValue(downscaledIcon);
    }

}
