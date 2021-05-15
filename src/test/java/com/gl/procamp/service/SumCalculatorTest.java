package com.gl.procamp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SumCalculatorTest {
    private SumCalculator sumCalculator;
    private final String fakeFileName = "fakeFileName";

    @BeforeClass
    public void setUp(){
        this.sumCalculator = new SumCalculator(fakeFileName);
    }

    @Test
    public void testSum_whenCorrectDoubles_thenGetCorrectSum() {
        List<String> correctListOfStringsWithDoubles = Arrays.asList("2.2", "3.3", "4.4");
        Double expectedResult = 9.9d;

        Double actualResult = sumCalculator.sum(correctListOfStringsWithDoubles);

        assertThat("Sum check failed", actualResult, is(expectedResult));
    }
}
