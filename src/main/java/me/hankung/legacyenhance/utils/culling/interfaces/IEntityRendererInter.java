package me.hankung.legacyenhance.utils.culling.interfaces;

import net.minecraft.entity.Entity;

public interface IEntityRendererInter<T extends Entity> {

	boolean shadowShouldShowName(T entity);

	void shadowRenderNameTag(T entity, double p_renderName_2_, double offsetX, double offsetY);

}
