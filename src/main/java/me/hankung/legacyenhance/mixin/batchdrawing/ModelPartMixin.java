package me.hankung.legacyenhance.mixin.batchdrawing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.ModelPart;

@Mixin(ModelPart.class)
public class ModelPartMixin {

    @Shadow
    private boolean compiledList;

    private boolean legacy$compiledState;

    @Inject(method = "render", at = @At("HEAD"))
    private void legacy$resetCompiled(float j, CallbackInfo ci) {
        if (legacy$compiledState != LegacyEnhance.CONFIG.performanceBatchModel.get()) {
            this.compiledList = false;
        }
    }

    @Inject(method = "compileList", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/Tessellator;getBuffer()Lnet/minecraft/client/render/BufferBuilder;"))
    private void legacy$beginRendering(CallbackInfo ci) {
        this.legacy$compiledState = LegacyEnhance.CONFIG.performanceBatchModel.get();
        if (LegacyEnhance.CONFIG.performanceBatchModel.get()) {
            Tessellator.getInstance().getBuffer().begin(7, VertexFormats.ENTITY);
        }
    }

    @Inject(method = "compileList", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEndList()V", remap = false))
    private void legacy$draw(CallbackInfo ci) {
        if (LegacyEnhance.CONFIG.performanceBatchModel.get()) {
            Tessellator.getInstance().draw();
        }
    }

}
