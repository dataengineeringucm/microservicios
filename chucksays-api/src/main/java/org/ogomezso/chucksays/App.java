package org.ogomezso.chucksays;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static spark.Spark.port;
import static spark.Spark.post;

@Slf4j
public class App {

    public static void main(String[] args) {

        try (InputStream input = new FileInputStream(args[0])) {
            Properties config = new Properties();
            config.load(input);
            int port = Integer.parseInt(config.getProperty("port"));
            final Faker faker = new Faker();
            port(8080);
            post("/chuck-says", (req, res) -> {
                log.info("Plain Json request received");
                String fact = "Chuck says: " + faker.chuckNorris().fact();
                log.info(fact);
                res.header("Content-Type", "application/json");
                return fact;
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
