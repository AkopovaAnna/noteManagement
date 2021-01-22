package com.project.repository;

import com.project.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Integer> {
    Optional<UserDto> findByEmail(String email);
}
