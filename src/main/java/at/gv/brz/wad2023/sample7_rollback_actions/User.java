package at.gv.brz.wad2023.sample7_rollback_actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_SAMPLE7")
public class User {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id = UUID.randomUUID();

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "registered_at", nullable = false)
    private LocalDate registeredAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_SEARCH_QUERY_SAMPLE5"))
    private Set<SearchQuery> searchQueries = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_DEVICE_SAMPLE5"))
    private Set<Device> devices = new HashSet<>();

    public User(String username, String email, LocalDate registeredAt) {
        this.username = username;
        this.email = email;
        this.registeredAt = registeredAt;
    }

    // getters and setters, equals and hashCode omitted

}
