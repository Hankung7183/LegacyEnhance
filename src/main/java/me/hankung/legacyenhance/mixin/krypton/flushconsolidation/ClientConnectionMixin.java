package me.hankung.legacyenhance.mixin.krypton.flushconsolidation;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.channel.Channel;
import me.hankung.legacyenhance.utils.krypton.ConfigurableAutoFlush;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.util.Tickable;

/**
 * Optimizes ClientConnection by adding the ability to skip auto-flushing and
 * using void promises where possible.
 */
@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin implements ConfigurableAutoFlush {

    @Shadow
    public abstract void sendQueuedPackets();

    @Shadow
    private PacketListener packetListener;

    @Shadow
    private Channel channel;
    private AtomicBoolean autoFlush;

    @Shadow
    public abstract void setState(NetworkState state);

    @Shadow
    @Final
    private static Logger LOGGER;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void legacy$initAddedFields(CallbackInfo ci) {
        this.autoFlush = new AtomicBoolean(true);
    }

    /**
     * @author Hankung
     * @reason Disable Forced Flush Every Tick
     */
    @Overwrite
    public void tick() {
        this.sendQueuedPackets();

        if (this.packetListener instanceof Tickable) {
            ((Tickable) this.packetListener).tick();
        }

        // Disable the channel.flush() call
    }

    @Override
    public void setShouldAutoFlush(boolean shouldAutoFlush) {
        boolean prev = this.autoFlush.getAndSet(shouldAutoFlush);
        if (!prev && shouldAutoFlush) {
            this.channel.flush();
        }
    }

}
