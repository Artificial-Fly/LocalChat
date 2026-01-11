package localchat.demo.repository;

import localchat.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM users WHERE ip_address = :ipAddress", nativeQuery = true)
    Optional<User> findByIpAddress(@Param("ipAddress") String ipAddress);
    @Query(value = "SELECT * FROM users WHERE nickname = :nickname", nativeQuery = true)
    Optional<User> findByNickname(@Param("nickname") String nickname);
    @Query(value="SELECT EXISTS(SELECT 1 FROM users WHERE nickname = :nickname)", nativeQuery = true)
    boolean existsByNickname(@Param("nickname") String nickname);

}
