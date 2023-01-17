package at.gv.brz.wad2023.sample5_joinfetch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Transactional
    public void insertUsers(List<User> user) {
        this.userRepository.saveAll(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(UUID userId) {
        return this.userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public void notifyUsersFetchAfterwards() {
        // Sample 5 - 2
        List<User> users = this.userRepository.findAll();
        List<UUID> ids = users.stream().map(User::getId).collect(Collectors.toList());
        this.userRepository.fetchSearchQueries(ids);
        this.userRepository.fetchDevices(ids);

        for (User user : users) {
            for (SearchQuery query : user.getSearchQueries()) {
                boolean newResults = hasNewResults(query);

                if (newResults) {
                    for (Device device : user.getDevices()) {
                        // notify device
                        System.out.println(
                                "notifying device " + device.getPushToken() + " from user " + user.getUsername() + " for search query " + query.getSearchTerm());
                    }
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public void notifyUsersFetchImmediately() {
        // Sample 5 - 1
        List<User> users = this.userRepository.findAllFetchRelations();
        for (User user : users) {
            for (SearchQuery query : user.getSearchQueries()) {
                boolean newResults = hasNewResults(query);

                if (newResults) {
                    for (Device device : user.getDevices()) {
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
