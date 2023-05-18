package me.hankung.legacyenhance.mixin.enableblending;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void legacy$enableBlending(CallbackInfo ci) {
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
    }
}
