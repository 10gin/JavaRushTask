package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

    @Query(value = "SELECT p " +
            "FROM Player p " +
            "WHERE " +
            "(:name is null or p.name LIKE %:name%) and" +
            "(:title is null or p.title LIKE %:title%) and" +
            "(:race is null or p.race = :race) and" +
            "(:profession is null or p.profession = :profession) and" +
            "(p.birthday >= :after) and" +
            "(p.birthday <= :before) and" +
            "(:banned is null or p.banned = :banned) and" +
            "(p.experience >= :minExperience) and" +
            "(p.experience <= :maxExperience) and" +
            "(p.level >= :minLevel) and" +
            "(p.level <= :maxLevel)")
//    Get players list
    List<Player> findByParameters(@Param("name") String name,
                                  @Param("title") String title,
                                  @Param("race") Race race,
                                  @Param("profession") Profession profession,
                                  @Param("after") Date after,
                                  @Param("before") Date before,
                                  @Param("banned") Boolean banned,
                                  @Param("minExperience") Integer minExperience,
                                  @Param("maxExperience") Integer maxExperience,
                                  @Param("minLevel") Integer minLevel,
                                  @Param("maxLevel") Integer maxLevel,
                                  Pageable paging
                              );


    @Query(value = "SELECT COUNT(p) " +
            "FROM Player p " +
            "WHERE " +
            "(:name is null or p.name LIKE %:name%) and" +
            "(:title is null or p.title LIKE %:title%) and" +
            "(:race is null or p.race = :race) and" +
            "(:profession is null or p.profession = :profession) and" +
            "(p.birthday >= :after) and" +
            "(p.birthday <= :before) and" +
            "(:banned is null or p.banned = :banned) and" +
            "(p.experience >= :minExperience) and" +
            "(p.experience <= :maxExperience) and" +
            "(p.level >= :minLevel) and" +
            "(p.level <= :maxLevel)")
//    Get players count
    Integer getCountByParameters(@Param("name") String name,
                                  @Param("title") String title,
                                  @Param("race") Race race,
                                  @Param("profession") Profession profession,
                                  @Param("after") Date after,
                                  @Param("before") Date before,
                                  @Param("banned") Boolean banned,
                                  @Param("minExperience") Integer minExperience,
                                  @Param("maxExperience") Integer maxExperience,
                                  @Param("minLevel") Integer minLevel,
                                  @Param("maxLevel") Integer maxLevel
    );
//    Pageable paging


}
