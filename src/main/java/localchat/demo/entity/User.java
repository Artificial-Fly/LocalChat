package localchat.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;
    @Column(name = "ip_adress")
    private String ipAdress;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User() {
    }

    public User(Long id, String nickname, String ipAdress, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.ipAdress = ipAdress;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
