package at.gv.brz.wad2023.sample7_rollback_actions;

import at.gv.brz.wad2023.sample7_rollback_actions.rollback.RollbackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PlatformTransactionManager txManager;

    private final NotificationApi notificationApi;

    private final RollbackManager rollbackManager;

    @Autowired
    public UserService(UserRepository userRepository, PlatformTransactionManager txManager, NotificationApi notificationApi, RollbackManager rollbackManager) {
        this.userRepository = userRepository;
        this.txManager = txManager;
        this.notificationApi = notificationApi;
        this.rollbackManager = rollbackManager;
    }

    @Transactional
    public void insertUser(User user) {
        this.userRepository.save(user);
    }

    @Transactional
    public void insertUsers(List<User> user) {
        this.userRepository.saveAll(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(UUID userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isPresent()) {
            this.userRepository.fetchDevices(List.of(userId));
        }
        return user;
    }

    public void addDevice(UUID userId, String pushToken) {
        TransactionTemplate txTemplate = new TransactionTemplate(this.txManager);
        AtomicBoolean notificationApiCalled = new AtomicBoolean(false); // (1) variable to remember if api was called

        try {
            txTemplate.executeWithoutResult(tx -> { // (2) start transaction
                User user = this.userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found!"));
                user.getDevices().add(new Device(pushToken));

                this.notificationApi.addToken(userId, pushToken); // (3) call notification api
                notificationApiCalled.set(true); // (4) remember that remote api was called

                throw new RuntimeException("Proccessing failed");
            });

        } catch (RuntimeException e) {
            if (notificationApiCalled.get()) { // (5) rollback actions on notification api
                this.notificationApi.removeToken(userId, pushToken);
            }
        }
    }

    @Transactional
    public void addDeviceUseRollbackManager(UUID userId, String pushToken) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        user.getDevices().add(new Device(pushToken));

        this.notificationApi.addToken(userId, pushToken);
        this.rollbackManager.addRollbackAction(() -> this.notificationApi.removeToken(userId, pushToken));

        throw new RuntimeException("Proccessing failed");
    }

    private boolean hasNewResults(SearchQuery query) {
        return true;
    }
}
