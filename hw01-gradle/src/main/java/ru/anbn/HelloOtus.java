package ru.anbn;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloOtus {
    private static final Logger logger = LoggerFactory.getLogger(HelloOtus.class);

    public static void main(String[] args) {
        String text = "Hello, OTUS!";
        String repeated = Strings.repeat(text + " ", 3);
        logger.info(repeated);
    }
}
