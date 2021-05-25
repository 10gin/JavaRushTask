package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.*;

@RestController
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/rest/players")
    List<Player> getPlayersList(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "race", required = false) String race,
                                @RequestParam(value = "profession", required = false) String profession,
                                @RequestParam(value = "after", required = false) Long after,
                                @RequestParam(value = "before", required = false) Long before,
                                @RequestParam(value = "banned", required = false) Boolean banned,
                                @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                @RequestParam(value = "order", required = false, defaultValue="ID") PlayerOrder order,
                                @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                @RequestParam(value = "pageSize", required = false) Integer pageSize){
//        String chdName = (name == null) ? "" : name;
//        String chdTitle = (title == null) ? "" : title;
        Integer chdPageNumber = (pageNumber == null) ? 0 : pageNumber;
        Integer chdPageSize = (pageSize == null) ? 3 : pageSize;

        Pageable paging = PageRequest.of(chdPageNumber, chdPageSize, Sort.by(order.getFieldName()));

        Race chdRace = (race == null) ? null : Race.valueOf(race);
        Profession chdProfession = (profession == null) ? null : Profession.valueOf(profession);


        Date chdAfter = new Date((after == null) ? 0 : (after)) ;
        Date chdBefore = new Date((before == null) ? 35659332669000l : (before));
        Boolean chdBanned = (banned == null) ? null : banned;
        Integer chdMinExperience = (minExperience == null) ? 0 : (minExperience);
        Integer chdMaxExperience = (maxExperience == null) ? Integer.MAX_VALUE : (maxExperience);
        Integer chdMinLevel = (minLevel == null) ? 0 : minLevel;
        Integer chdMaxLevel = (maxLevel == null) ? Integer.MAX_VALUE : maxLevel;


        return playerRepository.findByParameters(name, title, chdRace, chdProfession, chdAfter, chdBefore,
                                                chdBanned, chdMinExperience, chdMaxExperience, chdMinLevel, chdMaxLevel, paging);

    }

    @GetMapping("/rest/players/count")
    Integer getPlayersCount(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "race", required = false) String race,
                                @RequestParam(value = "profession", required = false) String profession,
                                @RequestParam(value = "after", required = false) Long after,
                                @RequestParam(value = "before", required = false) Long before,
                                @RequestParam(value = "banned", required = false) Boolean banned,
                                @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                @RequestParam(value = "maxLevel", required = false) Integer maxLevel){
//        String chdName = (name == null) ? "" : name;
//        String chdTitle = (title == null) ? "" : title;
//        Integer chdPageNumber = (pageNumber == null) ? 0 : pageNumber;
//        Integer chdPageSize = (pageSize == null) ? 3 : pageSize;
        Race chdRace = (race == null) ? null : Race.valueOf(race);
        Profession chdProfession = (profession == null) ? null : Profession.valueOf(profession);

        Date chdAfter = new Date((after == null) ? 0 : (after)) ;
        Date chdBefore = new Date((before == null) ? 35659332669000l : (before));
        Boolean chdBanned = (banned == null) ? null : banned;
        Integer chdMinExperience = (minExperience == null) ? 0 : (minExperience);
        Integer chdMaxExperience = (maxExperience == null) ? Integer.MAX_VALUE : (maxExperience);
        Integer chdMinLevel = (minLevel == null) ? 0 : minLevel;
        Integer chdMaxLevel = (maxLevel == null) ? Integer.MAX_VALUE : maxLevel;


        return playerRepository.getCountByParameters(name, title, chdRace, chdProfession, chdAfter, chdBefore,
                chdBanned, chdMinExperience, chdMaxExperience, chdMinLevel, chdMaxLevel);

    }

    @PostMapping("/rest/players")
    Player create(@RequestBody Player player){

        if (player.writeCheckAndPrepare())
            return playerRepository.save(player);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
    }

    @GetMapping("/rest/players/{id}")
    Player get(@PathVariable Long id){
        if (id <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");

        Optional<Player> result = playerRepository.findById(id);
        if (result.isPresent())
            return result.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
    }

    @PostMapping("/rest/players/{id}")
    Player update(@PathVariable Long id,
                  @RequestBody Player player){
        if (id <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        Optional<Player> result = playerRepository.findById(id);
        if (result.isPresent()) {
            Player forWritePlayer = result.get();
            if (player.getName() != null)
                forWritePlayer.setName(player.getName());
            if (player.getTitle() != null)
                forWritePlayer.setTitle(player.getTitle());
            if (player.getRace() != null)
                forWritePlayer.setRace(player.getRace());
            if (player.getProfession() != null)
                forWritePlayer.setProfession(player.getProfession());
            if (player.getBirthday() != null)
                forWritePlayer.setBirthday(player.getBirthday());
            if (player.getExperience() != null)
                forWritePlayer.setExperience(player.getExperience());
            if (player.getBanned() != null)
                forWritePlayer.setBanned(player.getBanned());

            if (forWritePlayer.writeCheckAndPrepare()) {
//                player.setId(id);
                return playerRepository.save(forWritePlayer);
            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
    }

    @DeleteMapping("/rest/players/{id}")
    void delete(@PathVariable Long id){
        if (id <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        Optional<Player> result = playerRepository.findById(id);
        if (result.isPresent())
            playerRepository.delete(result.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
    }
}
