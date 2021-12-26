package com.game.service;

import com.game.entity.Player;

import java.util.List;

public interface PlayerService {

    List<Player> getAllPlayers();

    Player getPlayer(long id);


    Player createNewPlayer();

    Player updatePlayer();

    void deletePlayer();
}
