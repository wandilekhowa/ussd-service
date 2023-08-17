package co.za.mamamoney.ussd.repository;

import co.za.mamamoney.ussd.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findSessionBySessionId(String sessionId);
}
