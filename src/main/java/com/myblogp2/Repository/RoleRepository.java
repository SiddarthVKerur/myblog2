package com.myblogp2.Repository;

import com.myblogp2.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);     // name of the role like admin and user
}
