package com.zeeyeh.versionmanager.repository;

import com.zeeyeh.versionmanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@EnableJpaRepositories
public interface RoleRepository extends Serializable, JpaRepository<Role, Long> {

    @Query("select u from roles u where u.rid = ?1")
    Role findByRid(Long rid);

    @Query("select u from roles u where u.name = ?1")
    Role findByName(String name);
}
