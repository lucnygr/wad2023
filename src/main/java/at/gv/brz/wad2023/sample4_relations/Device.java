package at.gv.brz.wad2023.sample4_relations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DEVICE_SAMPLE4")
public class Device {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id = UUID.randomUUID();

    @Column(name = "push_token")
    private String pushToken;

    public Device(String pushToken) {
        this.pushToken = pushToken;
    }

    // getters and setters, equals and hashCode omitted
}
