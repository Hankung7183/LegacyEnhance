package me.hankung.legacyenhance.mixin.entityculling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.LegacyEnhance;
import me.hankung.legacyenhance.utils.culling.interfaces.ICullable;
import me.hankung.legacyenhance.utils.culling.interfaces.IEntityRendererInter;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Shadow
    public abstract <T extends Entity> EntityRenderer<T> getRenderer(Entity p_getEntityRenderObject_1_);

    @Inject(at = @At("HEAD"), method = "method_6913", cancellable = true)
    public void doRenderEntity(Entity entity, double p_doRenderEntity_2_, double d1, double d2,
            float tickDelta, float p_doRenderEntity_9_, boolean p_doRenderEntity_10_, CallbackInfoReturnable<Boolean> ci) {
        ICullable cullable = (ICullable) entity;
        if (!cullable.isForcedVisible() && cullable.isCulled()) {
            IEntityRendererInter<Entity> entityRenderer = (IEntityRendererInter) getRenderer(entity);
            if (LegacyEnhance.CONFIG.performanceEntityCullingRNTW.get() && entityRenderer.shadowShouldShowName(entity)) {
                entityRenderer.shadowRenderNameTag(entity, p_doRenderEntity_2_, d1, d2);
            }
            LegacyEnhance.entityCulling.skippedEntities++;
            ci.cancel();
            return;
        }
        LegacyEnhance.entityCulling.renderedEntities++;
        cullable.setOutOfCamera(false);
    }

}
