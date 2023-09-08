//package org.example.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import org.example.base.domain.BaseEntity;
//import org.example.entity.users.User;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDateTime;
//
//@Entity
//
//
//@AllArgsConstructor
//@NoArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class ConfirmationToken extends BaseEntity<Long> {
//
//    String token;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    User user;
//
//    @CreationTimestamp
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    LocalDateTime createdAt;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    LocalDateTime expireAt;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    LocalDateTime confirmAt;
//
//    public String getToken() {
//        return token;
//    }
//
//    public ConfirmationToken setToken(String token) {
//        this.token = token;
//        return this;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public ConfirmationToken setUser(User user) {
//        this.user = user;
//        return this;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public ConfirmationToken setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//        return this;
//    }
//
//    public LocalDateTime getExpireAt() {
//        return expireAt;
//    }
//
//    public ConfirmationToken setExpireAt(LocalDateTime expireAt) {
//        this.expireAt = expireAt;
//        return this;
//    }
//
//    public LocalDateTime getConfirmAt() {
//        return confirmAt;
//    }
//
//    public ConfirmationToken setConfirmAt(LocalDateTime confirmAt) {
//        this.confirmAt = confirmAt;
//        return this;
//    }
//
//    public ConfirmationToken(String token, User user, LocalDateTime createdAt, LocalDateTime expireAt) {
//        this.token = token;
//        this.user = user;
//        this.createdAt = createdAt;
//        this.expireAt = expireAt;
//    }
//}
