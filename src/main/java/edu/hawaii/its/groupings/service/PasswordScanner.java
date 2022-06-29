package edu.hawaii.its.groupings.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import edu.hawaii.its.groupings.exceptions.PasswordFoundException;

@Service
public class PasswordScanner {

    private static final Log logger = LogFactory.getLog(PasswordScanner.class);

    @PostConstruct
    public void init() throws PasswordFoundException { // to edit
        logger.info("init; starting...");
        checkForPasswords();
        logger.info("init; check for passwords finished.");
        logger.info("init; started.");
    }

    private void checkForPasswords() throws PasswordFoundException {
        PatternPropertyChecker checkForPattern = new PatternPropertyChecker();

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