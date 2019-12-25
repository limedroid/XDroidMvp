package com.lennon.cn.utill.utill.rsa;

import android.os.Build;
import android.util.Base64;

import java.security.*;
import java.util.Arrays;

import androidx.annotation.RequiresApi;

public class RSAKeyUtil {

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static RSAKey createKeyPairsStr() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(512, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        PublicKey pubKey = pair.getPublic();
        PrivateKey privKey = pair.getPrivate();
        byte[] publicKeyByte = pubKey.getEncoded();
        byte[] privateKeyByte = privKey.getEncoded();
        String publicKeyStr = Base64.encodeToString(publicKeyByte, Base64.DEFAULT);
        String privateKeyStr = Base64.encodeToString(privateKeyByte, Base64.DEFAULT);

        System.out.println("公钥:" + Arrays.toString(publicKeyByte));
        System.out.println("私钥:" + Arrays.toString(privateKeyByte));
        System.out.println("公钥Base64编码:" + publicKeyStr);
        System.out.println("私钥Base64编码:" + privateKeyStr);

        return RSAKey.builder().addPublicKeyStr(publicKeyStr).addPrivateKeyStr(privateKeyStr).build();
    }

    public static class RSAKey {
        private String publicKeyStr;
        private String privateKeyStr;

        private RSAKey(RSAKeyBuilder rSAKeyBuilder) {
            this.publicKeyStr = rSAKeyBuilder.publicKeyStr;
            this.privateKeyStr = rSAKeyBuilder.privateKeyStr;
        }

        public String getPublicKeyStr() {
            return publicKeyStr;
        }

        public void setPublicKeyStr(String publicKeyStr) {
            this.publicKeyStr = publicKeyStr;
        }

        public String getPrivateKeyStr() {
            return privateKeyStr;
        }

        public void setPrivateKeyStr(String privateKeyStr) {
            this.privateKeyStr = privateKeyStr;
        }

        /**
         * Creates builder to build {@link RSAKey}.
         *
         * @return created builder
         */
        public static RSAKeyBuilder builder() {
            return new RSAKeyBuilder();
        }

        /**
         * Builder to build {@link RSAKey}.
         */
        public static final class RSAKeyBuilder {
            private String publicKeyStr;
            private String privateKeyStr;

            private RSAKeyBuilder() {
            }

            public RSAKeyBuilder addPublicKeyStr(String publicKeyStr) {
                this.publicKeyStr = publicKeyStr;
                return this;
            }

            public RSAKeyBuilder addPrivateKeyStr(String privateKeyStr) {
                this.privateKeyStr = privateKeyStr;
                return this;
            }

            public RSAKey build() {
                return new RSAKey(this);
            }
        }
    }
}
