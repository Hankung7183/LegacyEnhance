package me.hankung.legacyenhance.mixin.particleculling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.utils.culling.ParticleCulling;
import me.hankung.legacyenhance.utils.culling.interfaces.IParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.entity.Entity;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Redirect(
        method = "renderParticles",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/particle/Particle;draw(Lnet/minecraft/client/render/BufferBuilder;Lnet/minecraft/entity/Entity;FFFFFF)V"
        )
    )
    private void legacy$cullParticles(
        Particle instance, BufferBuilder worldRendererIn, Entity entityIn, float partialTicks,
        float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ
    ) {
        if (ParticleCulling.shouldRender(instance)) {
            instance.draw(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        }
    }

    @ModifyVariable(
        method = "updateLayer(Ljava/util/List;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;tickParticle(Lnet/minecraft/client/particle/Particle;)V", shift = At.Shift.AFTER)
    )
    private Particle legacy$checkIfCulled(Particle particle) {
        if (ParticleCulling.camera != null) {
            ((IParticle) particle).setCullState(ParticleCulling.camera.isBoxInFrustum(
                particle.getBoundingBox()
            ) ? 1.0F : -1.0F);
        }

        return particle;
    }
}
