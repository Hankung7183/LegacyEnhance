package me.hankung.legacyenhance.mixin.entityculling;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.gui.hud.DebugHud;

@Mixin(DebugHud.class)
public class DebugHudMixin {

    public DebugHudMixin() {
        LegacyEnhance.entityCulling.clientTick();
    }
    
    @Inject(method = "getLeftText", at = @At("RETURN"))
    public List<String> getLeftText(CallbackInfoReturnable<List<String>> cir) {
        List<String> list = cir.getReturnValue();
        list.add("[Culling] Last pass: " + LegacyEnhance.entityCulling.cullTask.lastTime + "ms");
        list.add("[Culling] Rendered Block Entities: " + LegacyEnhance.entityCulling.renderedBlockEntities + " Skipped: " + LegacyEnhance.entityCulling.skippedBlockEntities);
        list.add("[Culling] Rendered Entities: " + LegacyEnhance.entityCulling.renderedEntities + " Skipped: " + LegacyEnhance.entityCulling.skippedEntities);
        
        LegacyEnhance.entityCulling.renderedBlockEntities = 0;
        LegacyEnhance.entityCulling.skippedBlockEntities = 0;
        LegacyEnhance.entityCulling.renderedEntities = 0;
        LegacyEnhance.entityCulling.skippedEntities = 0;

        return list;
    }
    
}
