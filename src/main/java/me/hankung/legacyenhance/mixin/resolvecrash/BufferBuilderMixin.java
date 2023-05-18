package me.hankung.legacyenhance.mixin.resolvecrash;

import java.nio.IntBuffer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin {

    @Shadow
    private IntBuffer intBuffer;

    @Shadow
    private VertexFormat format;

    @Inject(method = "end", at = @At(value = "INVOKE", target = "Ljava/nio/ByteBuffer;limit(I)Ljava/nio/Buffer;"))
    private void legacy$resetBuffer(CallbackInfo ci) {
        this.intBuffer.position(0);
    }

    @Inject(method = "next", at = @At("HEAD"))
    private void legacy$adjustBuffer(CallbackInfo ci) {
        this.intBuffer.position(this.intBuffer.position() + this.format.getVertexSizeInteger());
    }

}
