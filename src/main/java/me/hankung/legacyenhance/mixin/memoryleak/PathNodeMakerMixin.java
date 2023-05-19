package me.hankung.legacyenhance.mixin.memoryleak;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.world.BlockView;

@Mixin(PathNodeMaker.class)
public class PathNodeMakerMixin {

    @Shadow
    protected BlockView blockView;

    @Inject(method = "clear", at = @At("HEAD"))
    private void legacy$cleanupBlockAccess(CallbackInfo ci) {
        this.blockView = null;
    }

}
