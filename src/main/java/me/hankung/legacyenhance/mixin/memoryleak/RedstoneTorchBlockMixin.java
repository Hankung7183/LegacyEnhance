package me.hankung.legacyenhance.mixin.memoryleak;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.world.World;

@Mixin(RedstoneTorchBlock.class)
public class RedstoneTorchBlockMixin {
    @Shadow
    private static Map<World, List<RedstoneTorchBlock.TurnOffEntry>> turnOffEntries = new WeakHashMap<>();
}
