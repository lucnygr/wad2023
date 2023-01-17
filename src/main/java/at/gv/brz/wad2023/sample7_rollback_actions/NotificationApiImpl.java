package at.gv.brz.wad2023.sample7_rollback_actions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class NotificationApiImpl implements NotificationApi {

    @Override public void addToken(UUID userId, String pushToken) {
        log.info("user {}: add token {}", userId, pushToken);
        // do nothing
    }

    @Override public void removeToken(UUID userId, String pushToken) {
        log.info("user {}: remove token {}", userId, pushToken);
        // do nothing
    }
}
