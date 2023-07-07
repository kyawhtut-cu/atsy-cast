package com.kyawhut.atsycast.msubpc.utils;

import android.util.Base64;

import androidx.annotation.Keep;

import com.google.gson.Gson;
import com.kyawhut.atsycast.msubpc.BuildConfig;

import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryptDecrypt {
    public static String decrypt(byte[] keyValue, String ivValue, String encryptedData, String macValue) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyValue, "AES");
        int i = 0;
        byte[] decode = Base64.decode(ivValue.getBytes("UTF-8"), 0);
        byte[] decode2 = Base64.decode(encryptedData.getBytes("UTF-8"), 0);
        SecretKeySpec secretKeySpec2 = new SecretKeySpec(keyValue, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec2);
        mac.update(ivValue.getBytes("UTF-8"));
        if (!Arrays.equals(mac.doFinal(encryptedData.getBytes("UTF-8")), Hex.decodeHex(macValue.toCharArray()))) {
            return "MAC mismatch";
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(decode));
        byte[] doFinal = cipher.doFinal(decode2);
        while (doFinal[i] != 34) {
            i++;
        }
        return new String(Arrays.copyOfRange(doFinal, i + 1, doFinal.length - 2));
    }

    public static String encrypt(byte[] paramArrayOfbyte, String paramString) throws Exception {
        SecretKeySpec secretKeySpec2 = new SecretKeySpec(paramArrayOfbyte, "AES");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("s:");
        stringBuilder.append((paramString.getBytes()).length);
        stringBuilder.append(":\"");
        stringBuilder.append(paramString);
        stringBuilder.append("\";");
        byte[] arrayOfByte2 = stringBuilder.toString().getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec2);
        byte[] arrayOfByte1 = cipher.getIV();
        String str2 = Base64.encodeToString(cipher.doFinal(arrayOfByte2), 2);
        SecretKeySpec secretKeySpec1 = new SecretKeySpec(paramArrayOfbyte, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec1);
        mac.update(Base64.encode(arrayOfByte1, 2));
        String str1 = new String(Hex.encodeHex(mac.doFinal(str2.getBytes("UTF-8"))));
        AesEncryptionData aesEncryptionData = new AesEncryptionData(Base64.encodeToString(arrayOfByte1, 2), str2, str1);
        return Base64.encodeToString((new Gson()).toJson(aesEncryptionData).getBytes("UTF-8"), 0);
    }

    public static String getDecryptedString(String encryptedString) {
        AesEncryptionData aesEncryptionData = (AesEncryptionData) new Gson().fromJson(
                new String(Base64.decode(encryptedString.getBytes(), 0)),
                AesEncryptionData.class
        );
        try {
            return decrypt(BuildConfig.ENCRYPT_KEY.getBytes("UTF-8"), aesEncryptionData.iv, aesEncryptionData.value, aesEncryptionData.mac);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public static String getDecryptedStringForAnother(String password, String encryptedString) {
        AesEncryptionData aesEncryptionData = (AesEncryptionData) new Gson().fromJson(
                new String(Base64.decode(encryptedString.getBytes(), 0)),
                AesEncryptionData.class
        );
        try {
            return decrypt(password.getBytes("UTF-8"), aesEncryptionData.iv, aesEncryptionData.value, aesEncryptionData.mac);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    @Keep
    public static class AesEncryptionData {
        public String iv;
        public String mac;
        public String value;

        public AesEncryptionData(String iv, String mac, String value) {
            this.iv = iv;
            this.mac = mac;
            this.value = value;
        }
    }
}
