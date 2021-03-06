package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {

    List<Player> getPlayersList(String name, String title, Race race, Profession profession,
                                Long after, Long before, Boolean banned, Integer minExperience,
                                Integer maxExperience, Integer minLevel, Integer maxLevel,
                                PlayerOrder order, Integer pageNumber, Integer pageSize);



    Player getPlayer(Long id);

    Player createPlayer(Player player);

    Player updatePlayer(Long id, Player player);

    void deletePlayer(Long id);
}
