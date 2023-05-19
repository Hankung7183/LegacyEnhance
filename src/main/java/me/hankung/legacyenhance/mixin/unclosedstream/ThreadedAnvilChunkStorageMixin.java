package me.hankung.legacyenhance.mixin.unclosedstream;

import java.io.DataInputStream;
import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.chunk.ThreadedAnvilChunkStorage;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorageMixin {
    @Redirect(method = "loadChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtIo;read(Ljava/io/DataInputStream;)Lnet/minecraft/nbt/NbtCompound;"))
    private NbtCompound legacy$closeStream(DataInputStream stream) throws IOException {
        NbtCompound result = NbtIo.read(stream);
        stream.close();
        return result;
    }
}
