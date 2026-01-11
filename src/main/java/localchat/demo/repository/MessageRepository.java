package localchat.demo.repository;

import localchat.demo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "SELECT * FROM messages WHERE created_at >= :since ORDER BY created_at ASC", nativeQuery = true)
    List<Message> findMessageSince(@Param("since")LocalDateTime since);
    @Query(value="SELECT * FROM messages ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Message> findLastMessages(@Param("limit") int limit);
    @Query(value = "DELETE FROM messages WHERE created_at < :olderThan RETURNING *", nativeQuery = true)
    List<Message> deleteOlderThan(@Param("olderThan")LocalDateTime olderThan);
}
