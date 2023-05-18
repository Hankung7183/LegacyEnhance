package me.hankung.legacyenhance.mixin.resolvenpe;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import me.hankung.legacyenhance.LegacyEnhance;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;

@Mixin(ServerList.class)
public abstract class ServerListMixin {

    @Shadow
    @Final
    private List<ServerInfo> servers;

    @Shadow
    public abstract void saveFile();

    /**
     * @author LlamaLad7
     * @reason resolve NPE
     */
    @Overwrite
    public ServerInfo get(int index) {
        try {
            return this.servers.get(index);
        } catch (Exception e) {
            LegacyEnhance.LOGGER.error("Failed to get server data.", e);
            return null;
        }
    }

    /**
     * @author LlamaLad7
     * @reason resolve NPE
     */
    @Overwrite
    public void remove(int index) {
        try {
            this.servers.remove(index);
        } catch (Exception e) {
            LegacyEnhance.LOGGER.error("Failed to remove server data.", e);
        }
    }

    /**
     * @author LlamaLad7
     * @reason resolve NPE
     */
    @Overwrite
    public void add(ServerInfo server) {
        try {
            this.servers.add(server);
        } catch (Exception e) {
            LegacyEnhance.LOGGER.error("Failed to add server data.", e);
        }
    }

    /**
     * @author LlamaLad7
     * @reason resolve NPE
     */
    @Overwrite
    public void swapEntries(int p_78857_1_, int p_78857_2_) {
        try {
            ServerInfo serverdata = this.get(p_78857_1_);
            this.servers.set(p_78857_1_, this.get(p_78857_2_));
            this.servers.set(p_78857_2_, serverdata);
            this.saveFile();
        } catch (Exception e) {
            LegacyEnhance.LOGGER.error("Failed to swap servers.", e);
        }
    }

    /**
     * @author LlamaLad7
     * @reason resolve NPE
     */
    @Overwrite
    public void set(int index, ServerInfo server) {
        try {
            this.servers.set(index, server);
        } catch (Exception e) {
            LegacyEnhance.LOGGER.error("Failed to set server data.", e);
        }
    }

}
