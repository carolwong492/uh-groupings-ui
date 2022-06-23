package edu.hawaii.its.groupings.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;
import edu.hawaii.its.groupings.service.CheckForPwdPattern;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class CheckForPwdPatternTest {

    CheckForPwdPattern checkForPwdPattern;
    String patternResult = "";
    String pattern = "^.*password.*\\=(?!\\s*$).+";
    String dirname = "src/main/resources";

    public void setUp() {
        checkForPwdPattern = new CheckForPwdPattern();
    }

    @Test
    public void construction() {
        List<String> fileLocations = checkForPwdPattern.fileLocations(".properties", dirname, pattern);
    }

    @Test
    public void noParam() {
        try {
            checkForPwdPattern.fileLocations("", "", "");
            fail("Should throw exception if all paramters are empty");
        } catch (Exception e) {

        }
    }

}
