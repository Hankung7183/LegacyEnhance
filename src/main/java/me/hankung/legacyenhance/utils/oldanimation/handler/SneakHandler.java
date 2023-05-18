package me.hankung.legacyenhance.utils.oldanimation.handler;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.ClientPlayerEntity;

public class SneakHandler {

    // private static final float START_HEIGHT = 1.62F;
    // private static final float END_HEIGHT = 1.54F;
    private float eyeHeight;
    private float lastEyeHeight;

    public float getEyeHeight(float partialTicks) {
        return !LegacyEnhance.CONFIG.oldanimateSmoothSneaking.get() ? this.eyeHeight : this.lastEyeHeight + (this.eyeHeight - this.lastEyeHeight) * partialTicks;
    }

    public void onTick() {
        this.lastEyeHeight = this.eyeHeight;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            this.eyeHeight = 1.62F;
            return;
        }

        if (player.isSneaking()) {
            this.eyeHeight = 1.54F;
        } else if (!LegacyEnhance.CONFIG.oldanimateLongSneaking.get()) {
            this.eyeHeight = 1.62F;
        } else if (this.eyeHeight < 1.62F) {
            float delta = 1.62F - this.eyeHeight;
            delta = (float) ((double) delta * 0.4);
            this.eyeHeight = 1.62F - delta;
        }
    }
    
}
