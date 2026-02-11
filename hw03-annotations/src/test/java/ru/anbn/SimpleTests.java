package ru.anbn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.anbn.framework.annotations.After;
import ru.anbn.framework.annotations.Before;
import ru.anbn.framework.annotations.Test;

@SuppressWarnings("squid:S2187")
public class SimpleTests {
    private static final Logger log = LoggerFactory.getLogger(SimpleTests.class);

    @Before
    public void setUp() {
        log.info("Before test setup");
    }

    @Test
    public void failedTest() {
        log.info("Test will fail now");
        throw new RuntimeException("Intentional failure");
    }

    @Test
    public void successTest() {
        log.info("Test OK");
    }

    @After
    public void After() {
        log.info("After test cleanup");
    }
}
