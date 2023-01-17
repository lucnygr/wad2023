package at.gv.brz.wad2023.sample2_spring_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public Optional<User> findUserById(Long userId) {
        return this.userRepository.findById(userId);
    }

    @Transactional
    public void updateUser(String oldUsername, String newUsername) {
        User user = userRepository.findByUsername(oldUsername).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        user.setUsername(newUsername);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserMultipleTimes(Long userId) {
        Optional<User> user = this.userRepository.findById(userId); // query database
        // do something
        user = this.userRepository.findById(userId); // no additional query
        // do something more
        user = this.userRepository.findById(userId); // no additional query
        return user;
    }
}
