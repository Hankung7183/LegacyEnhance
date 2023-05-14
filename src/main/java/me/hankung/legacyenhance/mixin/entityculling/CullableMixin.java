package me.hankung.legacyenhance.mixin.entityculling;

import org.spongepowered.asm.mixin.Mixin;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.culling.interfaces.ICullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;

@Mixin(value = { Entity.class, BlockEntity.class })
public class CullableMixin implements ICullable {

    private long lasttime = 0;
    private boolean culled = false;
    private boolean outOfCamera = false;

    @Override
    public void setTimeout() {
        lasttime = System.currentTimeMillis() + 1000;
    }

    @Override
    public boolean isForcedVisible() {
        return lasttime > System.currentTimeMillis();
    }

    @Override
    public void setCulled(boolean value) {
        this.culled = value;
        if (!value) {
            setTimeout();
        }
    }

    @Override
    public boolean isCulled() {
        if (!LegacyEnhance.CONFIG.performanceEntityCullingEnabled.get())
            return false;
        return culled;
    }

    @Override
    public void setOutOfCamera(boolean value) {
        this.outOfCamera = value;
    }

    @Override
    public boolean isOutOfCamera() {
        if (!LegacyEnhance.CONFIG.performanceEntityCullingEnabled.get())
            return false;
        return outOfCamera;
    }

}