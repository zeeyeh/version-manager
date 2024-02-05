package com.zeeyeh.versionmanager.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

import java.nio.charset.StandardCharsets;

public class MemberUtils {

    /**
     * 校验两个加密的密码是否一致
     * @param savePassword 存储的加密后密码
     * @param password 明文密码
     * @param publicKey 加密公钥
     * @return 是否相等
     */
    public static boolean isPasswordsEquals(String savePassword, String password, String publicKey) {
        String savePasswordDecode = decode(savePassword, publicKey);
        return savePasswordDecode.equals(password);
    }

    /**
     * 内容加密方法
     * @param content 原始内容
     * @param privateKey 加密私钥
     * @return 加密后内容
     */
    public static String encode(String content, String privateKey) {
        byte[] bytes = content.getBytes();
        String hexStr = HexUtil.encodeHexStr(bytes);
        byte[] aesKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        String encode = HexUtil.encodeHexStr(aesKey);
        String aesEncrypt = SecureUtil.aes(aesKey).encryptHex(hexStr);
        aesEncrypt = "$" + encode.length() + "$" + encode + aesEncrypt;
        RSA rsa = new RSA(privateKey, null);
        return rsa.encryptHex(aesEncrypt, KeyType.PrivateKey);
    }

    /**
     * 内容解密方法
     * @param content 加密的内容
     * @param publicKey 加密私钥
     * @return 解密后内容
     */
    public static String decode(String content, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        String rsaDecrypt = rsa.decryptStr(content, KeyType.PublicKey);
        rsaDecrypt = rsaDecrypt.substring(1);
        String[] strings = rsaDecrypt.split("\\$");
        int aesKeyLength = Integer.parseInt(strings[0]);
        String aesKeyHex = strings[1].substring(0, aesKeyLength);
        byte[] aesKey = HexUtil.decodeHex(aesKeyHex);
        String aesContent = strings[1].substring(aesKeyLength);
        byte[] decrypt = SecureUtil.aes(aesKey).decrypt(aesContent);
        String hexStr = new String(decrypt, StandardCharsets.UTF_8);
        return HexUtil.decodeHexStr(hexStr);
    }

    public static void main(String[] args) {
        String data = "admin123456..";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKXk9nksIhobgwZEcFvBjnSPKprOWShIYFt4WSLQpf+ar5nofqSbo4OYjDi/wbC+jpSNIA6K8z6+b1HM7m8byhBB5baKkB6YQIo6OvrlbE+DQ0uq3nTisXHU1aCdRd00ML9M3K9pQSpWbbignBWbfrrHPxNDySe0+fZIFipud8xpAgMBAAECgYAS4+udCLApa5CT73R2k6fLu1xinF9SqEswn0JR0JMryUrNqnJ2qV0JPcmvJ0bAVYfu1ShyqifgsSwD6I4OC4q1TWOqEH9ohaXaehUPbxDceu+h/spPK9kHfLHL1rosgdwFA1HJARseNfMhmanDC1RkwY8WYoTNDoyzy9UJer8c8QJBAOMte5HLY9Cqpo0IdQ2C/jPfapQC6RbUfHUPN2LR5MTDbE1g4gPufnskduLbR0fVLQFru40fXhRIAJdLe+vfxNMCQQC68Q6CElA3vzuiazuPLTWlJYFdybttivi+0mwL0mbNQ9UCopPk+cdPGLKY6NlP+g0WmwpEtcklmPOdL9tLu5RTAkEAxL/kcE1dQiA5pJV5gt07OUO6czveEXav31XxWvV7kunJR26r8EnCYvYevLS6lDzNAJkEUuGiwh/l2yJ1zb/8HwJAePs6RWluqrVC9bjqIZ3Dgu5Dy5ubhagTlQL+06PFzf+hIgRvLBeOKh00sAq5YK3VvJR3z8HJvGBBALAQ/vEVawJBAMwqgCz2F8bG9UEm+AJZ4YEcIsFBQ+digO3F8peGS2bjFFZZARB/xd99MwelTWphox+4Mg5lQxtx5bU5C9Yc0Yo=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCl5PZ5LCIaG4MGRHBbwY50jyqazlkoSGBbeFki0KX/mq+Z6H6km6ODmIw4v8Gwvo6UjSAOivM+vm9RzO5vG8oQQeW2ipAemECKOjr65WxPg0NLqt504rFx1NWgnUXdNDC/TNyvaUEqVm24oJwVm366xz8TQ8kntPn2SBYqbnfMaQIDAQAB";
        String encode = encode(data, privateKey);
        System.out.println(encode);
        String pa = "8fd1afa198932cee2d5dea2fb92b234f61effe4a6e3b78391ea46331f0a90eae203353814a888beb123f6b01f7eab489422564f9eef156a69514bfb1758c20e637f7d3d138087226946e86daa7e4139b6fb7e654d4f75bb96ef63e2409484d1afa185d2381d82188de256152e2ddd60c815b05867b4dcb94782aa63564b31a790d9f29aef88270164465cc8e8d4fac807bf75f6235b56d1fedba0c52bd5be93486c8a5dc1549c7cb5ceb5a40e326217e05a5d35777bdf4e24836cb794e462c8af6f8f085a5dc0f7761ddb902cea3494b46f47f674f68ea1ebfac9f0cd57ddd883f9bb2e5b4b35912e452e893d4bb7c936d5f7cb37f7b1ee94c59cfdd4d22f14d7674b3d8049f1b23a8349db2906cfef0976c92c68b5e94b07d6b7f69c0bb4cb55d59aca5b2eadfb02dd7ca4c2b1013c8f1571aecce3f963cd5c7f769001806207b3551355630430856e50d9615a2ec1ccd78117abfe4586bfd8130e1412f46509c1a58565da0a1b7ebe5f6709d3b1caf0aa652e6d0eb3bc126c6565d403dc35b64b012cf9f623e1432e870efb91516cde25739963ff8dd8f85ae8eca1679630cf1b9036f83e45c995ad53677bdec93b437985bc0f263a06a668cbeb93bbe6b4246e5573a0adca094a741f5c63979a07211bff00053b80efe04f3ba25054276d7818024f73498703952d6554d911cfb495e50d5cd996e01ff36bbc0f4b54bfd2f8af8067141de23d919b429705eda9224f2c89dea16674707cdcafa9be834ea01d4907d47e6c4f6b05ba3c8b1dce575adeaabda397b8302af193e3d70aff148b9400dd72d85795e84664798bced64701c75303450519310bb7615d8bd1d89ea4e745a06d67508674fb89649166daca741e2f58e4701be96cf4c4cbf8a4872caf14c26fa75ce684045c5944133ec7a8c9b87466b46df2eeea0b2a0454967d8d160085982b62535c5503bae646ef78bb64068e4bc7c82e7e707bef74a1386f73a1a8f5a1d067349a5a45d0d4de722f3c566eed49b4b1747e164a42f1f95acfc711414a7fea76896b5e04394b61ca88ba3229a203b88dc618ba35330324bca5c88af2260cd3c12c4f23996fc8f91a0dbefd3024e20705970341a76e971a9528fa1af40115757b1fa9e2c10681fea6119d92729bae57c6df09aad011673840c0dc410603e16a32bf6ca0f0f1c5976e1ae70beafd6ac7158896c4c803fbae53b34bab648f52a67c39c64a03fb192b66fd5ecee58b589b1436e501b9fc0091a843d3fe61c851c695ae64ecb566e0521008892424fd34a75fb68bfc45d6f6deb54d834dc3f94d413a317b7a483de8b7f0362f3c3636e319267538cf0001440a83ac45d067c0800583f503615e86b65b93a0737bc5bb28306344b2cac634d576ae8dbcdcc7b4f6a2639d6c3154c401e7a1edd9e12ccb1051a055b30e45afbeeac632437833019a0cd596a118c33fca3b0e30530fb916bfd4e1c73132c02b0f34ed1945c18f3fb4f1494bb94b4c4438dbcec58e7775533092d5805267293890d50a274be06a2fc992e0527d870e489c4cb7be892411e821e27720005fe1213ea5be95287248ab5feda50e8b843dddd539be99861187d454f4e86abdb94e29cffb86c95cfb759d286bb4efdf4fbbe46340ee32be2f26934cf49240c71e00b8a4ed5e6cbde2623b9790fcff116b54efe2ede413aaac4ec39d454bbe1eb48dd525455a98b713572dc7a9051632223c064e7ffce14f063867021b7feea6fb55b1b1b58360167fb1f7da9e53351f21030305bedcf2ec84a9fe8b063354de689be1449be4af4e782";
        String decode = decode(encode, publicKey);
        String decodepa = decode(pa, publicKey);
        System.out.println(decode);
        System.out.println(decodepa);
    }
}
