package com.example.andras.myapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * source:
 * https://medium.com/overmorrow/authentication-sucks-bad-security-too-345ed20463d4#.wnhoz4toh
 * https://github.com/flschweiger/SafeApp
 */

public class KeystoreActivity extends AppCompatActivity {

    private static final String TAG = "KeystoreActivity";
    //this should also work but it doesn't: KeyStore.getDefaultType();
    private static final String KEY_STORE_TYPE = "AndroidKeyStore";
    public static final String KEY_ALIAS = "myPocKeyAlias";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            KeyStore keyStore = loadKeyStore();
            if (!isKeyStoreInitialized(keyStore)) {
                createKeyStore();
                Log.d(TAG, "kkkk new keystore created");
            }

            // Get the SecretKey from the KeyStore and instantiate a Cipher
            SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_ALIAS, null);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            // Init the Cipher and encrypt the plaintext
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal("This is a super secret message".getBytes());

            //TODO in real world here you should enccode the bytes and the  initialization vector (IV). See the source app
            // for details!
            Log.d(TAG, "kkkk encryption successful: " + new String(encryptedBytes));

            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(cipher.getIV()));
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            Log.d(TAG, "kkkk decryption successful: " + new String(decryptedBytes));


        } catch (NoSuchProviderException
                | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | CertificateException
                | KeyStoreException
                | IOException
                | InvalidKeyException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | UnrecoverableKeyException e) {
            Log.e(TAG, "kkkk " +  e.getMessage(), e);
        }
    }

    private KeyStore loadKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        // Get the AndroidKeyStore instance
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        // Relict of the JCA API - you have to call load even
        // if you do not have an input stream you want to load or it'll crash
        keyStore.load(null);
        Log.d(TAG, "kkkk keystore loaded");
        return keyStore;
    }

    private boolean isKeyStoreInitialized(KeyStore keyStore) throws KeyStoreException {
        return keyStore.containsAlias(KEY_ALIAS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void createKeyStore() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        // Get the KeyGenerator instance for AES
        KeyGenerator keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, KEY_STORE_TYPE);

        // Create a KeyGenParameterSpec builder and
        // set the alias and different purposes of the key
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);

        // The KeyGenParameterSpec is how parameters for your key are passed to the
        // generator. Choose wisely!
        builder
            .setKeySize(256)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);

        // Generate and store the key
        keyGenerator.init(builder.build());
        keyGenerator.generateKey();
    }
}
