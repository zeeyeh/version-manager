package com.zeeyeh.versionmanager.repository;

import com.zeeyeh.versionmanager.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface MenuRepository extends Serializable, JpaRepository<Menu, Long> {

    @Query("select u from menus u where u.mid = ?1")
    Menu findByMid(Long mid);

    @Query("select u from menus u where u.mid = ?1")
    List<Menu> findAllByMid(Long mid);

    @Query("select u from menus u where u.name = ?1")
    Menu findByName(String name);

    @Query("select u from menus u where u.name = ?1")
    List<Menu> findAllByName(String name);
}
