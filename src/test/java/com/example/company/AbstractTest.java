package com.example.company;

import java.nio.charset.StandardCharsets;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public abstract class AbstractTest {

    private final EasyRandomParameters parameters = new EasyRandomParameters()
            .seed(123L)
            .objectPoolSize(100)
            .randomizationDepth(3)
            .charset(StandardCharsets.UTF_8)
            .stringLengthRange(5, 50)
            .collectionSizeRange(1, 10)
            .scanClasspathForConcreteTypes(true)
            .overrideDefaultInitialization(false)
            .ignoreRandomizationErrors(true);

    protected final EasyRandom random = new EasyRandom(parameters);
}
