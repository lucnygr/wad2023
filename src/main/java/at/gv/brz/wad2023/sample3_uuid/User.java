package at.gv.brz.wad2023.sample3_uuid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_SAMPLE3")
// Sample 3
public class User {

    @Id
    @Column(columnDefinition = "UUID")
    //@GeneratedValue
    private UUID id = UUID.randomUUID();

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "registered_at", nullable = false)
    private LocalDate registeredAt;

    public User(String username, String email, LocalDate registeredAt) {
        this.username = username;
        this.email = email;
        this.registeredAt = registeredAt;
    }

    // getters and setters, equals and hashCode omitted

}
