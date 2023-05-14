package me.hankung.legacyenhance.utils.culling;

import me.hankung.legacyenhance.utils.culling.interfaces.IParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.CameraView;

public class ParticleCulling {

    public static CameraView camera;

    public static boolean shouldRender(Particle particle) {
        return particle != null && (camera == null || ((IParticle) particle).getCullState() > -1);
    }
    
}
