package at.gv.brz.wad2023.sample0_jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String email;

    private LocalDate registeredAt;

    // getters and setters, equals and hashCode omitted

}
