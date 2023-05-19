package me.hankung.legacyenhance.mixin.fullbright;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.utils.FullbrightTicker;
import net.minecraft.world.chunk.Chunk;

@Mixin(Chunk.class)
public class ChunkMixin {
    @Inject(method = { "getLightAtPos", "getLightLevel" }, at = @At("HEAD"), cancellable = true)
    private void legacy$fullbright(CallbackInfoReturnable<Integer> cir) {
        if (FullbrightTicker.isFullbright()) {
            cir.setReturnValue(15);
        }
    }
}
