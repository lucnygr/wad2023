package at.gv.brz.wad2023.sample3_uuid;

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
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
