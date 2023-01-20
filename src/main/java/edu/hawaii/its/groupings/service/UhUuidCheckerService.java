package edu.hawaii.its.groupings.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.hawaii.its.groupings.access.Role;

@Service
public class UhUuidCheckerService {

    @Value("${groupings.access.uhuuid_pattern}")
    private String UHUUIDPATTERN;

    private Pattern pattern;

    private static final Log logger = LogFactory.getLog(UhUuidCheckerService.class);

    public boolean isValidUhUuid(String uhUuid, String uid) {
        logger.info("isValidUhUuid; check for passwords starting...");
        pattern = Pattern.compile(UHUUIDPATTERN, Pattern.CASE_INSENSITIVE);

        final Matcher matcher = pattern.matcher(uhUuid);
        return matcher.matches() || uhUuid == uid;
    }
}