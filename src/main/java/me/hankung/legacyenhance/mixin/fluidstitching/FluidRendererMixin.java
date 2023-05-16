package me.hankung.legacyenhance.mixin.fluidstitching;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.render.block.FluidRenderer;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
    @ModifyConstant(method = "render", constant = @Constant(floatValue = 0.001F))
    private float legacy$fixFluidStitching(float original) {
        return 0.0F;
    }
}
