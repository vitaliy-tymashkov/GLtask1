package com.gl.procamp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    private static final String BASE_URL_KEY = "base_url";
    private static final String AUTH_BEARER_TOKEN = "authorization_Bearer_Token";
    private static final String GET_POSTS_KEY = "get_posts_path";
    private static final String USER_KEY = "user_path";

    public static void readConfigurationFromFile(String propertiesFileName) {
        FileInputStream fileInputStream;
        Properties properties = new Properties();

        try {
            fileInputStream = new FileInputStream(propertiesFileName);
            properties.load(fileInputStream);
            Config.setBaseUrl(properties.getProperty(BASE_URL_KEY));
            Config.setAuthBearerToken(properties.getProperty(AUTH_BEARER_TOKEN));
            Config.setGetPostsPath(properties.getProperty(GET_POSTS_KEY));
            Config.setUserPath(properties.getProperty(USER_KEY));

        } catch (IOException e) {
            System.out.println(String.format("IOException while reading %s", e.getMessage()));
        }
        logger.info("Configs updated.");
    }
}
