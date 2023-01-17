package at.gv.brz.wad2023.sample6_improvements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
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
        return this.userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public void notifyUsers() {
        try (Stream<User> users = this.userRepository.streamAllFetchRelations()) { // streams users and fetches relations in same select
            users.forEach(user -> {
                for (SearchQuery query : user.getSearchQueries()) { // no additional query needed
                    boolean newResults = hasNewResults(query);

                    if (newResults) {
                        for (Device device : user.getDevices()) { // no additional query needed
                            // notify device
                            //System.out.println("notifying device " + device.getPushToken() + " from user " + user.getUsername() + " for search query " + query.getSearchTerm());
                        }
                    }
                }
                this.entityManager.detach(user); // detach entity to allow garbage collection
            });
        }
    }

    private boolean hasNewResults(SearchQuery query) {
        return true;
    }
}
