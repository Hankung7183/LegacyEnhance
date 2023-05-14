package me.hankung.legacyenhance.mixin.staticparticlecolor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.particle.Particle;

@Mixin(Particle.class)
public class ParticleMixin {
    @Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;getLightmapCoordinates(F)I"))
    private int staticParticleColor(Particle particle, float partialTicks) {
        return LegacyEnhance.CONFIG.performanceStaticParticleColor.get() ? 15728880 : particle.getLightmapCoordinates(partialTicks);
    }
}
