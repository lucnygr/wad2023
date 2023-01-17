package at.gv.brz.wad2023.sample1_jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// Sample 1
@Entity
@Table(name = "USER_SAMPLE1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "registered_at", nullable = false)
    private LocalDate registeredAt;

    // getters and setters, equals and hashCode omitted
}
