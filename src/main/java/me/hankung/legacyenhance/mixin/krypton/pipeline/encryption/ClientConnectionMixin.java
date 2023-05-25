package me.hankung.legacyenhance.mixin.krypton.pipeline.encryption;

import java.security.GeneralSecurityException;

import javax.crypto.SecretKey;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.velocitypowered.natives.encryption.VelocityCipher;
import com.velocitypowered.natives.util.Natives;

import io.netty.channel.Channel;
import me.hankung.legacyenhance.utils.krypton.ClientConnectionEncryptionExtension;
import me.hankung.legacyenhance.utils.krypton.pipeline.MinecraftCipherDecoder;
import me.hankung.legacyenhance.utils.krypton.pipeline.MinecraftCipherEncoder;
import net.minecraft.network.ClientConnection;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin implements ClientConnectionEncryptionExtension {

    @Shadow
    private boolean encrypted;
    @Shadow
    private Channel channel;

    @Override
    public void setupEncryption(SecretKey key) throws GeneralSecurityException {
        if (!this.encrypted) {
            VelocityCipher decryption = Natives.cipher.get().forDecryption(key);
            VelocityCipher encryption = Natives.cipher.get().forEncryption(key);

            this.encrypted = true;
            this.channel.pipeline().addBefore("splitter", "decrypt", new MinecraftCipherDecoder(decryption));
            this.channel.pipeline().addBefore("prepender", "encrypt", new MinecraftCipherEncoder(encryption));
        }
    }

}
