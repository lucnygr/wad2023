package at.gv.brz.wad2023.sample6_improvements;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @QueryHints(value = {
            // optimization, like no dirty checks
            @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true"),
            // distinction is done by hibernate and not at db-level
            @QueryHint(name = org.hibernate.annotations.QueryHints.PASS_DISTINCT_THROUGH, value = "false"),
            // small objects, so increase fetch size
            @QueryHint(name = org.hibernate.annotations.QueryHints.FETCH_SIZE, value = "5000")
    })
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.searchQueries LEFT JOIN FETCH u.devices")
    List<User> findAllFetchRelations();

    @QueryHints(value = {
            // optimization, like no dirty checks
            @QueryHint(name = org.hibernate.annotations.QueryHints.READ_ONLY, value = "true"),
            // distinction is done by hibernate and not at db-level
            @QueryHint(name = org.hibernate.annotations.QueryHints.PASS_DISTINCT_THROUGH, value = "false"),
            // small objects, so increase fetch size
            @QueryHint(name = org.hibernate.annotations.QueryHints.FETCH_SIZE, value = "5000")
    })
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.searchQueries LEFT JOIN FETCH u.devices")
    Stream<User> streamAllFetchRelations();
}
