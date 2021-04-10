package com.gl.procamp.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SumCalculator {
    public static final double ZERO = 0.0D;

    private final String FILE_NAME;

    public SumCalculator(String filename) {
        this.FILE_NAME = filename;
    }

    public void calculate() {
        try {
            List<String> lines = getAllLinesFromFile();
            double sum = sum(lines);

            System.out.println(sum);
        } catch (IOException e) {
            System.out.println(String.format("IOException while reading %s", e.getMessage()));
        }
    }

    private List<String> getAllLinesFromFile() throws IOException {
        return Files.readAllLines(Paths.get(FILE_NAME),
                StandardCharsets.UTF_8);
    }

    public double sum(List<String> lines) {
        return lines.stream()
                .map(String::trim)
                .filter(this::isNotComment)
                .filter(this::isNotEmptyLine)
                .mapToDouble(this::getDoubleFromLine)
                .sum();
    }

    private boolean isNotComment(String line) {
        return !(line.startsWith("#"));
    }

    private boolean isNotEmptyLine(String line) {
        return !(line.equals(""));
    }

    private double getDoubleFromLine(String line) {
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println(String.format("NumberFormatException '%s'. Replaced with '%s'.", e.getMessage(), ZERO));
            return ZERO;
        }
    }
}
