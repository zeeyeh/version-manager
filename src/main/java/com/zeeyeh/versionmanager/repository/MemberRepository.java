package com.zeeyeh.versionmanager.repository;

import com.zeeyeh.versionmanager.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@EnableJpaRepositories
public interface MemberRepository extends Serializable, JpaRepository<Member, Long> {

    @Query("select u from members u where u.uid = ?1")
    Member findByUid(Long uid);

    @Query("select u from members u where u.username = ?1")
    Member findByUsername(String username);

    @Query("select u from members u where u.nickname = ?1")
    Member findByNickname(String nickname);

    @Query("select u from members u where u.email = ?1")
    Member findByEmail(String email);
}
