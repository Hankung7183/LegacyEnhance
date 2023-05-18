package me.hankung.legacyenhance.mixin.resolvecrash;

import java.net.IDN;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.network.ServerAddress;

@Mixin(ServerAddress.class)
public class ServerAddressMixin {

    @Shadow
    @Final
    private String address;

    /**
     * @author LlamaLad7
     * @reason Fix crash - MC-89698
     */
    @Overwrite
    public String getAddress() {
        try {
            return IDN.toASCII(this.address);
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

}
