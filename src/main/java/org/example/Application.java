package org.example;

import io.github.swagger2markup.Swagger2MarkupConverter;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {


    public static void main(String[] args) throws MalformedURLException {


        SpringApplication.run(Application.class, args);

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v3/api-docs"))
                .build()
                .toFile(Paths.get("outputPath"));
    }
}
