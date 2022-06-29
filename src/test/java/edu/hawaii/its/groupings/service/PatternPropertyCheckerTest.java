package edu.hawaii.its.groupings.service;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class PatternPropertyCheckerTest {

    PatternPropertyChecker patternPropertyChecker;
    String pattern;
    String dirname;

    @Before
    public void setUp() {
        patternPropertyChecker = new PatternPropertyChecker();
        pattern = "^.*password.*\\=(?!\\s*$).+";
        dirname = "src/test/resources/PatternPropertyCheckerTestFiles";
    }

    @Test
    public void testNoPatternFound() {
        List<String> fileLocations = patternPropertyChecker.fileLocations(".txt", dirname + "/test2");
        assertTrue(fileLocations.size() == 0);
    }

    @Test
    public void testNoFileFound() {
        List<String> fileLocations = patternPropertyChecker.fileLocations(".properties", dirname);
        assertTrue(fileLocations.size() == 0);
    }

    @Test
    public void testEmptyFile() {
        List<String> fileLocations = patternPropertyChecker.fileLocations(".txt", dirname);
        assertTrue(fileLocations.size() == 0);
    }

    @Test
    public void testOnePatternFound() {
        List<String> fileLocations = patternPropertyChecker.fileLocations(".properties", dirname + "/test1");
        assertEquals(1, fileLocations.size());
        assertTrue(fileLocations.contains(
                "src/test/resources/PatternPropertyCheckerTestFiles/test1/PatternPropertyCheckerTestFile.properties on line: 2"));
    }

    @Test
    public void testTwoPatternSameFile() {
        List<String> fileLocations = patternPropertyChecker.fileLocations(".properties", dirname + "/test2");
        assertEquals(2, fileLocations.size());
        assertTrue(fileLocations.contains(
                "src/test/resources/PatternPropertyCheckerTestFiles/test2/PatternPropertyCheckerTestFile2.properties on line: 2"));
        assertTrue(fileLocations.contains(
                "src/test/resources/PatternPropertyCheckerTestFiles/test2/PatternPropertyCheckerTestFile2.properties on line: 5"));
    }

    @Test
    public void testTwoPatternDiffFile() {
        List<String> fileLocations = patternPropertyChecker.fileLocations(".txt", dirname + "/test1");
        assertEquals(2, fileLocations.size());
        assertTrue(fileLocations.contains(
                "src/test/resources/PatternPropertyCheckerTestFiles/test1/PatternPropertyCheckerTwoPatterns.txt on line: 1"));
        assertTrue(fileLocations.contains(
                "src/test/resources/PatternPropertyCheckerTestFiles/test1/PatternPropertyCheckerTwoPatterns2.txt on line: 3"));
    }

}
