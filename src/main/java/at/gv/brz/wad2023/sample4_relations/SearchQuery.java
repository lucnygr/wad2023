package at.gv.brz.wad2023.sample4_relations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SEARCH_QUERY_SAMPLE4")
public class SearchQuery {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id = UUID.randomUUID();

    @Column(name = "search_term", nullable = false)
    private String searchTerm;

    @Column(name = "search_date", nullable = false)
    private LocalDateTime searchDate;

    public SearchQuery(String searchTerm, LocalDateTime searchDate) {
        this.searchTerm = searchTerm;
        this.searchDate = searchDate;
    }

    // getters and setters, equals and hashCode omitted
}
