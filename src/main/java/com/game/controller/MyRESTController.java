package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class MyRESTController {

    private final PlayerService playerService;

    @Autowired
    public MyRESTController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("")
    public ResponseEntity<List<Player>> showAllPlayers(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "title", required = false) String title,
                                                       @RequestParam(value = "race", required = false) Race race,
                                                       @RequestParam(value = "profession", required = false) Profession profession,
                                                       @RequestParam(value = "after", required = false) Long after,
                                                       @RequestParam(value = "before", required = false) Long before,
                                                       @RequestParam(value = "banned", required = false) Boolean banned,
                                                       @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                       @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                       @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                       @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                       @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                                       @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        List<Player> allPlayers =  playerService.getPlayersList(name, title, race, profession, after, before,
                banned, minExperience, maxExperience, minLevel, maxLevel,
                order, pageNumber, pageSize);
        return new ResponseEntity<>(allPlayers, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getPlayersCount(@RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "title", required = false) String title,
                                                   @RequestParam(value = "race", required = false) Race race,
                                                   @RequestParam(value = "profession", required = false) Profession profession,
                                                   @RequestParam(value = "after", required = false) Long after,
                                                   @RequestParam(value = "before", required = false) Long before,
                                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {
        Integer playersCount = playerService.getPlayersList(name, title, race, profession, after, before,
                banned, minExperience, maxExperience, minLevel, maxLevel,
                PlayerOrder.ID, 0, Integer.MAX_VALUE).size();
        return new ResponseEntity<>(playersCount, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {

//        if (!(id instanceof Long)) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST, "please, check id"
//            );
//        }
        Player player = playerService.getPlayer(id);
        if (player == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "such player not found"
            );
        }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping("")
    public Player createPlayer(@RequestBody Player player) {
        playerService.createPlayer(player);
        return player;
    }

    @DeleteMapping("{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
}
