package co.za.mamamoney.ussd.util;

import co.za.mamamoney.ussd.model.Session;
import java.time.LocalDateTime;

public class SessionChecker {

    private static final Long SESSION_EXPIRY_MINUTES = 45L;

    public static Boolean isSessionExpired(Session session) {
        return LocalDateTime.now()
                .isAfter(session.getCreateDate().plusMinutes(SESSION_EXPIRY_MINUTES));
    }
}
