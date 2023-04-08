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
    public static String decrypt(byte[] paramArrayOfbyte, String paramString1, String paramString2, String paramString3) throws Exception {
        SecretKeySpec secretKeySpec2 = new SecretKeySpec(paramArrayOfbyte, "AES");
        byte[] arrayOfByte2 = paramString1.getBytes("UTF-8");
        byte b = 0;
        arrayOfByte2 = Base64.decode(arrayOfByte2, 0);
        byte[] arrayOfByte3 = Base64.decode(paramString2.getBytes("UTF-8"), 0);
        SecretKeySpec secretKeySpec1 = new SecretKeySpec(paramArrayOfbyte, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec1);
        mac.update(paramString1.getBytes("UTF-8"));
        if (!Arrays.equals(mac.doFinal(paramString2.getBytes("UTF-8")), Hex.decodeHex(paramString3.toCharArray())))
            return "MAC mismatch";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(2, secretKeySpec2, new IvParameterSpec(arrayOfByte2));
        byte[] arrayOfByte1 = cipher.doFinal(arrayOfByte3);
        while (arrayOfByte1[b] != 34)
            b++;
        return new String(Arrays.copyOfRange(arrayOfByte1, b + 1, arrayOfByte1.length - 2));
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

    public static String getDecryptedString(String paramString) {
        paramString = new String(Base64.decode(paramString.getBytes(), 0));
        AesEncryptionData aesEncryptionData = (new Gson()).fromJson(paramString, AesEncryptionData.class);
        try {
            String str = decrypt(BuildConfig.ENCRYPT_KEY.getBytes("UTF-8"), aesEncryptionData.iv, aesEncryptionData.value, aesEncryptionData.mac);
            return str;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public static String getDecryptedStringForAnother(String password, String paramString) {
        paramString = new String(Base64.decode(paramString.getBytes(), 0));
        AesEncryptionData aesEncryptionData = (new Gson()).fromJson(paramString, AesEncryptionData.class);
        try {
            String str = decrypt(password.getBytes("UTF-8"), aesEncryptionData.iv, aesEncryptionData.value, aesEncryptionData.mac);
            return str;
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
