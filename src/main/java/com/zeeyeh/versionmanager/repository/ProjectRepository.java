package com.zeeyeh.versionmanager.repository;

import com.zeeyeh.versionmanager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@EnableJpaRepositories
//public interface ProjectRepository extends Serializable, JpaRepository<Project, Long>, CrudRepository<Project, Long> {
public interface ProjectRepository extends Serializable, JpaRepository<Project, Long> {
    @Query("select u from projects u where u.pid = ?1")
    Project findByPid(long pid);

    @Query("select u from projects u where u.name = ?1")
    Project findByName(String name);
}
