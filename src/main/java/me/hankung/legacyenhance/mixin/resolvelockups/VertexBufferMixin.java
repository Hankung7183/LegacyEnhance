package me.hankung.legacyenhance.mixin.resolvelockups;

import java.nio.ByteBuffer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexBuffer;

@Mixin(VertexBuffer.class)
public class VertexBufferMixin {
    
    @Shadow private int id;

    @Inject(method = "data", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexBuffer;bind()V"), cancellable = true)
    private void legacy$ignoreRemovedBuffers(ByteBuffer byteBuffer, CallbackInfo ci) {
        if (this.id == -1) {
            ci.cancel();
        }
    }

}
