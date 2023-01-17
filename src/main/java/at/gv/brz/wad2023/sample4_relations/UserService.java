package at.gv.brz.wad2023.sample4_relations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void insertUser(User user) {
        this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(UUID userId) {
        return this.userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public void notifyUsers() {
        List<User> users = this.userRepository.findAll();       // (1) loads all users at once
        for (User user : users) {
            for (SearchQuery query : user.getSearchQueries()) { // (2) executes additional query
                boolean newResults = hasNewResults(query);

                if (newResults) {
                    for (Device device : user.getDevices()) {   // (3) executes additional query
                        // notify device
                        System.out.println(
                                "notifying device " + device.getPushToken() + " from user " + user.getUsername() + " for search query " + query.getSearchTerm());
                    }
                }
            }
        }
    }

    private boolean hasNewResults(SearchQuery query) {
        return true;
    }
}
