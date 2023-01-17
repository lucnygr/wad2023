package at.gv.brz.wad2023.sample7_rollback_actions;

import java.util.UUID;

public interface NotificationApi {

    void addToken(UUID userId, String pushToken);

    void removeToken(UUID userId, String pushToken);
}
