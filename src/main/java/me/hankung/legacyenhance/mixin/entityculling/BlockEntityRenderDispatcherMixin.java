package me.hankung.legacyenhance.mixin.entityculling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.culling.interfaces.ICullable;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    @Inject(method = "renderEntity(Lnet/minecraft/block/entity/BlockEntity;DDDFI)V", at = @At("HEAD"), cancellable = true)
    public void onRenderEntity(BlockEntity blockEntity, double x, double y, double z, float tickDelta, int destroyProgress, CallbackInfo ci) {
        if (!((ICullable) blockEntity).isForcedVisible() && ((ICullable) blockEntity).isCulled()) {
            LegacyEnhance.entityCulling.skippedBlockEntities++;
            ci.cancel();
            return;
        }
        LegacyEnhance.entityCulling.renderedBlockEntities++;
    }
}
