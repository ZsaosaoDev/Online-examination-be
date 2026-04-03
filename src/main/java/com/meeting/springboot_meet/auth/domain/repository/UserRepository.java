package com.meeting.springboot_meet.auth.domain.repository;

import com.meeting.springboot_meet.auth.domain.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
}