package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
@RequestMapping("/")
public class Controller {

    //Todo: eliminate all warning (folder -> analyze -> inspect code)
    // React
    // Heroku

    @GetMapping("/")
    public String interact(@RequestBody String input) throws IOException {

        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        File outputFile = Main.start(stream);

        return new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.UTF_8);
    }

}
