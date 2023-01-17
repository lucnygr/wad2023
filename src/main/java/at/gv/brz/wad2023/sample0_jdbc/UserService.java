package at.gv.brz.wad2023.sample0_jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Service
public class UserService {

    private final DataSource dataSource;

    @Autowired
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection()
            throws SQLException {
        return this.dataSource.getConnection();
    }

    // Sample 0
    public void insertUser(User user) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO USER_SAMPLE0(id, username, email, registered_at) VALUES (?, ?, ?, ?)")
        ) {
            pstmt.setLong(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setDate(4, Date.valueOf(user.getRegisteredAt()));

            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // exception handling
        }
    }

    public Optional<User> findUserById(Long userId) {
        User fetchedUser = null;
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT username, email, registered_at FROM USER_SAMPLE0 WHERE id = ?")
        ) {
            pstmt.setString(1, userId.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    fetchedUser = new User(
                            userId,
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getDate("registered_at").toLocalDate());
                }
            }
        } catch (SQLException e) {
            // exception handling
        }
        return Optional.ofNullable(fetchedUser);
    }
}
