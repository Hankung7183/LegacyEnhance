package me.hankung.legacyenhance.mixin.banneranimationfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.world.World;

@Mixin(BannerBlockEntityRenderer.class)
public class BannerBlockEntityRendererMixin {
    @Redirect(method = "render(Lnet/minecraft/block/entity/BannerBlockEntity;DDDFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getLastUpdateTime()J"))
    private long legacy$resolveOverflow(World world) {
        return world.getLastUpdateTime() % 100L;
    }
}
