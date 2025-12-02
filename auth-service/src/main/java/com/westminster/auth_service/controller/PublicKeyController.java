package com.westminster.auth_service.controller;

import com.westminster.auth_service.service.KeyPairService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class PublicKeyController {

    private final KeyPairService keyPairService;

    public PublicKeyController(KeyPairService keyPairService) {
        this.keyPairService = keyPairService;
    }

    @GetMapping("/auth/public-key")
    public String getPublicKey() {
        String encodedKey = Base64.getEncoder().encodeToString(keyPairService.getPublicKey().getEncoded());
        return "-----BEGIN PUBLIC KEY-----\n" +
                encodedKey +
                "\n-----END PUBLIC KEY-----";
    }
}
