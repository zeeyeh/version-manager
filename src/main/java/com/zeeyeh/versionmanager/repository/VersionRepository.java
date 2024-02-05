package com.zeeyeh.versionmanager.repository;

import com.zeeyeh.versionmanager.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface VersionRepository extends JpaRepository<Version, Long> {
    @Query("select u from versions u where u.vid = ?1")
    Version findByVid(long vid);

    @Query("select u from versions u where u.name = ?1")
    List<Version> findByName(String name);

    @Query("select u from versions u where u.pid = ?1")
    List<Version> findByPid(long pid);
}
