package me.hankung.legacyenhance.mixin.krypton.pipeline.encryption;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.hankung.legacyenhance.utils.krypton.ClientConnectionEncryptionExtension;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerLoginNetworkHandler;

import java.security.Key;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin {

    @Shadow
    @Final
    public ClientConnection connection;

    @Redirect(method = "onKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkEncryptionUtils;cipherFromKey(ILjava/security/Key;)Ljavax/crypto/Cipher;"))
    private Cipher legacy$initializeVelocityCipher(int ignored1, Key secretKey) throws GeneralSecurityException {
        // Hijack this portion of the cipher initialization and set up our own
        // encryption handler.
        ((ClientConnectionEncryptionExtension) this.connection).setupEncryption((SecretKey) secretKey);

        // Turn the operation into a no-op.
        return null;
    }

    @Redirect(method = "onKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;setupEncryption(Ljavax/crypto/Cipher;Ljavax/crypto/Cipher;)V"))
    public void legacy$ignoreMinecraftEncryptionPipelineInjection(ClientConnection connection, Cipher ignored1,
            Cipher ignored2) {
        // Turn the operation into a no-op.
    }

}