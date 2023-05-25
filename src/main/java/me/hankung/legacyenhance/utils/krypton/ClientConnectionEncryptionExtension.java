package me.hankung.legacyenhance.utils.krypton;

import java.security.GeneralSecurityException;

import javax.crypto.SecretKey;

public interface ClientConnectionEncryptionExtension {
    void setupEncryption(SecretKey key) throws GeneralSecurityException;
}
