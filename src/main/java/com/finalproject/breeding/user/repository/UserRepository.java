package com.finalproject.breeding.user.repository;

import com.finalproject.breeding.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);
}
