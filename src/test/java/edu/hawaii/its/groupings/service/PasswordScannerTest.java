package edu.hawaii.its.groupings.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;
import edu.hawaii.its.groupings.exceptions.PasswordFoundException;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// cant run with these
// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = { SpringBootWebApplication.class })
public class PasswordScannerTest {

    PasswordScanner passwordScanner;

    @Test
    public void testPasswordScanner() {
        try {
            passwordScanner = new PasswordScanner();
            passwordScanner.init();
            fail("Fail: should have found password");
        } catch (PasswordFoundException e) {
            // test passes
        }
    }
}
