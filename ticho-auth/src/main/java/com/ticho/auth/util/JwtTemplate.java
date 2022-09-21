package com.ticho.auth.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.extra.spring.SpringUtil;
import com.ticho.boot.web.util.JsonUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-21 15:55
 */
@Slf4j
@Component
public class JwtTemplate implements InitializingBean {
    private String verifierKey = null;

    @Getter
    private Signer signer = null;

    private String signingKey = verifierKey;

    @Getter
    private SignatureVerifier verifier;

    @Override
    public void afterPropertiesSet() {
        setKeyPair("rsa_first.jks", "com.ticho", "123456");
        log.info("加载证书成功");
    }

    /**
     * 通过证书加密
     */
    public void setKeyPair(String path, String alias, String password) {
        ClassPathResource resource = new ClassPathResource(path);
        //设置密钥对（私钥） 此处传入的是创建jks文件时的别名-alias 和 秘钥库访问密码
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, password.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias);
        PrivateKey privateKey = keyPair.getPrivate();
        Assert.state(privateKey instanceof RSAPrivateKey, "KeyPair must be an RSA ");
        signer = new RsaSigner((RSAPrivateKey) privateKey);
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        verifier = new RsaVerifier(publicKey);
        verifierKey =
                "-----BEGIN PUBLIC KEY-----\n" + Base64.encode(publicKey.getEncoded()) + "\n-----END PUBLIC KEY-----";
    }

    /**
     * 通过公钥加密
     */
    public void setSigningKey(String key) {
        key = key.trim();
        this.signingKey = key;
        if (isPublic(key)) {
            signer = new RsaSigner(key);
            log.info("Configured with RSA signing key");
        } else {
            // Assume it's a MAC key
            this.verifierKey = key;
            signer = new MacSigner(key);
        }
    }

    public boolean isPublic(String key) {
        return key.startsWith("-----BEGIN");
    }

    public static String encode(Object data) {
        JwtTemplate jwtTemplate = SpringUtil.getBean(JwtTemplate.class);
        Signer signer = jwtTemplate.getSigner();
        return JwtHelper.encode(JsonUtil.toJsonString(data), signer).getEncoded();
    }

    public static String decode(String token) {
        JwtHelper.decode(token);
        return JwtHelper.decode(token).getClaims();
    }

    public static String decodeAndVerify(String token) {
        JwtTemplate jwtTemplate = SpringUtil.getBean(JwtTemplate.class);
        SignatureVerifier verifier = jwtTemplate.getVerifier();
        Assert.notNull(verifier, "verifier is null ");
        return JwtHelper.decodeAndVerify(token, verifier).getClaims();
    }

}
