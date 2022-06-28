package edu.hawaii.its.groupings.service;

import org.junit.Test;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Null;
import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        dirname = "src/test/java/edu/hawaii/its/groupings/service/checkForPwdPatternTestFiles";
    }

/*    @ExtendWith(OutputCaptureExtension.class)
    @Test
    public void testNullParam(CapturedOutput output) {
        try {
            checkForPwdPattern.fileLocations(null, null, null);
            assertTrue(output.contains("ok"));
            fail("Should throw exception if all parameters are empty");
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }*/

    // directory is null
    // pattern is null
    // file extension is null

    // test for when searching for empty string
//    @Test
//    public void testEmptyStringGiven() {
//        try {
//            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname + "/test2", "");
//            System.out.println(fileLocations);
//            assertEquals(0, fileLocations.size());
//        } catch (Exception e) {
//            fail("Should not throw an exception");
//        }
//    }

    // test for when pattern is not in any file
    @Test
    public void testNoPatternFound() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".txt", dirname + "/test2", pattern);
            assertTrue(fileLocations.size() == 0);
        } catch (Exception e) {
            fail("Should not throw an exception");
        }
    }

    // test for when there are no files found for given extension
    @Test
    public void testNoFileFound() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname, pattern);
            assertTrue(fileLocations.size() == 0);
        } catch (Exception e) {
            fail("Should not throw an exception");
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

    // test that it can find one instance of pattern in the file (looks in test1 folder)
    @Test
    public void testOnePatternFound() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname + "/test1", pattern);
            assertEquals(1, fileLocations.size());
            assertTrue(fileLocations.contains(
                    "src/test/java/edu/hawaii/its/groupings/service/checkForPwdPatternTestFiles/test1/checkForPwdPatternTestFile.properties on line: 2"));
        } catch (Exception e) {
            fail("Should have found password");
        }
    }

    // test that it can find two instances of pattern in the same file (looks in test2 folder)
    @Test
    public void testTwoPatternSameFile() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname + "/test2", pattern);
            assertEquals(2, fileLocations.size());
            assertTrue(fileLocations.contains(
                    "src/test/java/edu/hawaii/its/groupings/service/checkForPwdPatternTestFiles/test2/checkForPwdPatternTestFile2.properties on line: 2"));
            assertTrue(fileLocations.contains(
                    "src/test/java/edu/hawaii/its/groupings/service/checkForPwdPatternTestFiles/test2/checkForPwdPatternTestFile2.properties on line: 5"));
        } catch (Exception e) {
            fail("Should have found password");
        }
    }

    // test that it can find two instances of pattern in the separate file (looks in test1 folder)
    @Test
    public void testTwoPatternDiffFile() {
        try {
            List<String> fileLocations = checkForPwdPattern.fileLocations(".txt", dirname + "/test1", pattern);
            assertEquals(2, fileLocations.size());
            assertTrue(fileLocations.contains(
                    "src/test/java/edu/hawaii/its/groupings/service/checkForPwdPatternTestFiles/test1/checkForPwdPatternTwoPatterns.txt on line: 1"));
            assertTrue(fileLocations.contains(
                    "src/test/java/edu/hawaii/its/groupings/service/checkForPwdPatternTestFiles/test1/checkForPwdPatternTwoPatterns2.txt on line: 3"));
        } catch (Exception e) {
            fail("Should have found password");
        }
    }

}
