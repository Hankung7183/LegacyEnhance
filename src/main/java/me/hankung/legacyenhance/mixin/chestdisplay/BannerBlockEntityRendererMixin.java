package me.hankung.legacyenhance.mixin.chestdisplay;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.google.common.collect.Lists;

import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.texture.ColorMaskTexture;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

@Mixin(BannerBlockEntityRenderer.class)
public class BannerBlockEntityRendererMixin {

    @Shadow
    @Final
    private static Map<String, BannerBlockEntityRenderer.TextureIdHolder> textures;

    @Shadow
    @Final
    private static Identifier BASE_TEXTURE;

    /**
     * @author asbyth
     * @reason Resolve banners in chests not displaying once cache is full
     */
    @Overwrite
    private Identifier getTexture(BannerBlockEntity banner) {
        String texture = banner.getTextureIdentifier();

        if (texture.isEmpty()) {
            return null;
        } else {
            BannerBlockEntityRenderer.TextureIdHolder timedTexture = textures.get(texture);
            if (timedTexture == null) {
                if (textures.size() >= 256 && !this.legacy$freeCacheSlot()) {
                    return BASE_TEXTURE;
                }

                List<BannerBlockEntity.BannerPattern> patternList = banner.getPatterns();
                List<DyeColor> colorList = banner.getColors();
                List<String> patternPath = Lists.newArrayList();

                for (BannerBlockEntity.BannerPattern pattern : patternList) {
                    patternPath.add("textures/entity/banner/" + pattern.getName() + ".png");
                }

                timedTexture = new BannerBlockEntityRenderer.TextureIdHolder();
                timedTexture.texture = new Identifier(texture);
                MinecraftClient.getInstance().getTextureManager().loadTexture(timedTexture.texture,
                        new ColorMaskTexture(BASE_TEXTURE, patternPath, colorList));
                textures.put(texture, timedTexture);
            }

            timedTexture.field_11012 = System.currentTimeMillis();
            return timedTexture.texture;
        }
    }

    @Unique
    private boolean legacy$freeCacheSlot() {
        long start = System.currentTimeMillis();
        Iterator<String> iterator = textures.keySet().iterator();

        while (iterator.hasNext()) {
            String next = iterator.next();
            BannerBlockEntityRenderer.TextureIdHolder timedTexture = textures.get(next);

            if ((start - timedTexture.field_11012) > 5000L) {
                MinecraftClient.getInstance().getTextureManager().close(timedTexture.texture);
                iterator.remove();
                return true;
            }
        }

        return textures.size() < 256;
    }

}
