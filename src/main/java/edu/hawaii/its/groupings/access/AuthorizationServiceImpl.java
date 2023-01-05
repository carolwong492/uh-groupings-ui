package edu.hawaii.its.groupings.access;

import static edu.hawaii.its.groupings.controller.HomeController.testModal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.authentication.SimplePrincipal;

import edu.hawaii.its.api.controller.GroupingsRestController;
import edu.hawaii.its.groupings.controller.HomeController;
import edu.hawaii.its.groupings.exceptions.InvalidUhUuidException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@PropertySource("classpath:custom.properties")
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    GroupingsRestController groupingsRestController;

    @Value("${groupings.access.uhuuid_pattern}")
    private String UHUUIDPATTERN;

    private static final Log logger = LogFactory.getLog(AuthorizationServiceImpl.class);

    private Pattern pattern;

    /**
     * Assign roles to user
     */
    @Override
    public RoleHolder fetchRoles(String uhUuid, String uid) {
        pattern = Pattern.compile(UHUUIDPATTERN, Pattern.CASE_INSENSITIVE);

        RoleHolder roleHolder = new RoleHolder();
        roleHolder.add(Role.ANONYMOUS);

        if (uhUuid != null) {
            final Matcher matcher = pattern.matcher(uhUuid);

            if (matcher.matches()) {
//                roleHolder.add(Role.UH);
            }

            Principal principal = new SimplePrincipal(uhUuid);

            //Determine if user is an owner.
            if (checkResult(groupingsRestController.hasOwnerPrivs(principal))) {
//                roleHolder.add(Role.OWNER);
            }

            //Determine if a user is an admin.
            if (checkResult(groupingsRestController.hasAdminPrivs(principal))) {
//                roleHolder.add(Role.ADMIN);
            }

        }

        logger.info("fetchRoles: username: " + uid + " " + roleHolder.getAuthorities() + ";");
        return roleHolder;
    }

    /**
     * Return a boolean based on parsed response.
     */
    private boolean checkResult(ResponseEntity response) {
        if (response == null || response.getBody() == null) {
            return false;
        }

        return Boolean.parseBoolean((String) response.getBody());
    }
}
