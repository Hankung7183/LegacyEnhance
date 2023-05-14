package me.hankung.legacyenhance.mixin.entityculling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;

import me.hankung.legacyenhance.utils.culling.interfaces.IEntityRendererInter;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> implements IEntityRendererInter<T>  {

    @Override
    public boolean shadowShouldShowName(T entity) {
        return hasLabel(entity);
    }

    @Override
    public void shadowRenderNameTag(T p_renderName_1_, double p_renderName_2_, double d1, double d2) {
        method_10208(p_renderName_1_, p_renderName_2_, d1, d2);
    }

    @Shadow
    protected abstract void method_10208(T p_renderName_1_, double p_renderName_2_, double d1, double d2);
    
    @Shadow
    protected abstract boolean hasLabel(T p_canRenderName_1_);
    
}
