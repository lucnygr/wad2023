package at.gv.brz.wad2023.sample7_rollback_actions;

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
            // small objects, so increase fetch size
            @QueryHint(name = org.hibernate.annotations.QueryHints.FETCH_SIZE, value = "5000")
    })
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.searchQueries LEFT JOIN FETCH u.devices")
    Stream<User> streamAllFetchRelations();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.devices WHERE u.id in (:userIds)")
    List<User> fetchDevices(List<UUID> userIds);
}
