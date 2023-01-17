package at.gv.brz.wad2023.sample5_joinfetch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Sample 5
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.searchQueries LEFT JOIN FETCH u.devices")
    List<User> findAllFetchRelations();

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.searchQueries WHERE u.id in (:userIds)")
    List<User> fetchSearchQueries(List<UUID> userIds);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.devices WHERE u.id in (:userIds)")
    List<User> fetchDevices(List<UUID> userIds);

}
