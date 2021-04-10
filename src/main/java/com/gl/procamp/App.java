package com.gl.procamp;

import static com.gl.procamp.config.ConfigReader.readConfigurationFromFile;

public class App {
    //Filename can be passed with arguments
    private static final String PROPERTIES_FILE_NAME = "src/main/resources/config.properties";

    public static void main(String[] args) {
        readConfigurationFromFile(PROPERTIES_FILE_NAME);


//        new SumCalculator(FILE_NAME).calculate();
    }
}
