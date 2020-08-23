package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
@RequestMapping("/")
public class Controller {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public String interact(@RequestBody String input) throws IOException {

        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        File outputFile = Main.start(stream);

        return new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.UTF_8);
    }

}
