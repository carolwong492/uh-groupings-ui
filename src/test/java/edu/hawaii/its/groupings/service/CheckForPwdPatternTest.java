package edu.hawaii.its.groupings.service;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

 @RunWith(SpringRunner.class)
 @SpringBootTest(classes = { SpringBootWebApplication.class })
public class CheckForPwdPatternTest {

    CheckForPwdPattern checkForPwdPattern;
    String pattern;
    String dirname;

    @Before
    public void setUp() {
        checkForPwdPattern = new CheckForPwdPattern();
        pattern = "^.*password.*\\=(?!\\s*$).+";
        dirname= "src/test/java/edu/hawaii/its/groupings/service";
    }

//    @Test
//    public void testNoParam() {
//        if ()
//        try {
//            checkForPwdPattern.fileLocations(".properties", null, pattern);
//            fail("Should throw exception if all parameters are empty");
//        } catch (Exception e) {
//            assertTrue(true);
//        }
//    }

    // test that it can find two instances of pattern in the file
    @Test
    public void testOnePattern() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname, pattern);
            assertTrue(fileLocations.size() == 2);
        } catch (Exception e) {
            fail("Should have found password");
        }
    }

    // test that it can find two instances of pattern in the same file
    @Test
    public void testTwoPatternSameFile() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname, pattern);
            assertTrue(fileLocations.size() == 2);
        } catch (Exception e) {
            fail("Should have found password");
        }
    }

    // test that it can find two instances of pattern in the same file
     //TODO
    @Test
    public void testTwoPatternSeparateFile() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname, pattern);
            assertTrue(fileLocations.size() == 2);
        } catch (Exception e) {
            fail("Should have found password");
        }
    }

    // test tests empty files
    @Test
    public void testEmptyFile() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".txt", dirname, pattern);
            assertTrue(fileLocations.size() == 0);
        } catch (Exception e) {
            fail("Should not throw exception and .");
        }
    }

    // test for when pattern is not in any file
    @Test
    public void testNoPattern() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname, "potato");
            assertTrue(fileLocations.size() == 0);
        } catch (Exception e) {
            fail("Should not throw an exception");
        }
    }

    // empty
    // no pattern
    // one pattern
    // two pattern (separate files)
    // two pattern (same file)

}
