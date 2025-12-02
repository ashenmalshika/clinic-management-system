package com.westminster.doctor_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class PublicKeyConfig {

    @Bean
    public PublicKey authPublicKey() throws Exception {
        // Read the public key PEM file from resources folder
        ClassPathResource resource = new ClassPathResource("public_key.pem");
        String key = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Remove PEM headers and whitespace
        String cleanedKey = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        // Decode Base64 to bytes
        byte[] decoded = Base64.getDecoder().decode(cleanedKey);

        // Create PublicKey object from bytes
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(keySpec);
    }
}