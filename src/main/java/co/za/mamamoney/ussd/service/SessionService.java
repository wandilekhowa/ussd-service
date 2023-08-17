package co.za.mamamoney.ussd.service;

import co.za.mamamoney.ussd.model.Session;
import co.za.mamamoney.ussd.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getSession(String sessionId) {
        return sessionRepository.findSessionBySessionId(sessionId);
    }

    public void saveSession(Session session) {
        sessionRepository.save(session);
    }
}
