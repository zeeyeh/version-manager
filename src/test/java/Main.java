import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        String data = Files.readString(new File(System.getProperty("user.dir"), "data.txt").toPath());
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        PrivateKey aPrivate = pair.getPrivate();
        byte[] privateEncoded = aPrivate.getEncoded();
        String privateKey = Base64.getEncoder().encodeToString(privateEncoded);
        PublicKey aPublic = pair.getPublic();
        byte[] publicEncoded = aPublic.getEncoded();
        String publicKey = Base64.getEncoder().encodeToString(publicEncoded);
        System.out.println(privateKey);
        System.out.println(publicKey);
//        byte[] aesKey = SecureUtil.generateKey("AES").getEncoded();
//        String encrypt = encrypt(data, aesKey, publicEncoded);
//        System.out.println(encrypt);
//        String decrypt = decrypt(encrypt, privateEncoded);
//        System.out.println(decrypt);
    }

    public static String encrypt(String content, byte[] aesKey, byte[] publicKey) {
        byte[] bytes = content.getBytes();
        String hexStr = HexUtil.encodeHexStr(bytes).toUpperCase();
        String encode = HexUtil.encodeHexStr(aesKey).toUpperCase();
        String aesEncrypt = SecureUtil.aes(aesKey).encryptHex(hexStr).toUpperCase();
        aesEncrypt = "$" + encode.length() + "$" + encode + aesEncrypt;
        RSA rsa = SecureUtil.rsa(null, publicKey);
        return rsa.encryptHex(aesEncrypt, KeyType.PublicKey).toUpperCase();
    }

    public static String decrypt(String content, byte[] privateKey) {
        RSA rsa = SecureUtil.rsa(privateKey, null);
        String rsaDecrypt = rsa.decryptStr(content, KeyType.PrivateKey);
        rsaDecrypt = rsaDecrypt.substring(1);
        String[] strings = rsaDecrypt.split("\\$");
        int aesKeyLength = Integer.parseInt(strings[0]);
        String aesKeyHex = strings[1].substring(0, aesKeyLength);
        byte[] aesKey = HexUtil.decodeHex(aesKeyHex);
        String aesContent = strings[1].substring(aesKeyLength);
        byte[] decrypt = SecureUtil.aes(aesKey).decrypt(aesContent);
        return HexUtil.decodeHexStr(new String(decrypt, StandardCharsets.UTF_8));
    }
}