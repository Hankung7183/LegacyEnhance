package me.hankung.legacyenhance.mixin.batchdrawing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.TexturedQuad;

@Mixin(TexturedQuad.class)
public class TexturedQuadMixin {

    @Unique
    private boolean legacy$drawOnSelf;

    @Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferBuilder;begin(ILnet/minecraft/client/render/VertexFormat;)V"))
    private void legacy$beginDraw(BufferBuilder renderer, int glMode, VertexFormat format) {
        this.legacy$drawOnSelf = !((BufferBuilderAccessor) renderer).isDrawing();
        if (this.legacy$drawOnSelf || !LegacyEnhance.CONFIG.performanceBatchModel.get()) {
            renderer.begin(glMode, VertexFormats.POSITION_TEXTURE_NORMAL);
        }
    }

    @Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;draw()V"))
    private void legacy$endDraw(Tessellator tessellator) {
        if (this.legacy$drawOnSelf || !LegacyEnhance.CONFIG.performanceBatchModel.get()) {
            tessellator.draw();
        }
    }

}
