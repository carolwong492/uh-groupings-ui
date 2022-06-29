package edu.hawaii.its.groupings.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.hawaii.its.groupings.exceptions.PasswordFoundException;

@Service
public class PasswordScanner {

    private static final Log logger = LogFactory.getLog(PasswordScanner.class);

    @Value("${pattern.property.checker.pattern:^.*password.*\\\\=(?!\\\\s*$).+}")
    private String pattern;

    @PostConstruct
    public void init() throws PasswordFoundException {
        logger.info("init; starting...");
        logger.info("init; pattern: " + pattern);
        checkForPasswords();
        logger.info("init; check for passwords finished.");
        logger.info("init; started.");
    }

    private void checkForPasswords() throws PasswordFoundException {
        PatternPropertyChecker checkForPattern = new PatternPropertyChecker();
        checkForPattern.setPattern(pattern);

        String patternResult = "";
        String dirname = "src/main/resources";
        List<String> fileLocations = checkForPattern.fileLocations(".properties", dirname);
        if (fileLocations != null && !fileLocations.isEmpty()) {
            for (String list : fileLocations) {
                patternResult += "\n" + list;
            }
        }

        if (patternResult.length() > 0) {
            throw new PasswordFoundException(patternResult);
        }
    }
}