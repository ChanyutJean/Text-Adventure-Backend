package com.example.demo;

import com.example.demo.game.Main;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin
@RequestMapping("/")
public class Controller {

    @GetMapping("/")
    public String home() {
        return "Welcome! Use POST request to communicate.";
    }

    @PostMapping("/")
    public String interact(@RequestBody String rawInput) throws IOException, NoSuchAlgorithmException {
        String input = decode(rawInput);

        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        File outputFile = Main.start(stream);

        return new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.UTF_8);
    }

    public static String decode(String value) {
        try {
            String valueWithEquals = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
            return valueWithEquals.substring(0, valueWithEquals.length() - 1);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

}
