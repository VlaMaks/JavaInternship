package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    PlayerDAO playerDAO;

    @Override
    @Transactional
    public List<Player> getAllPlayers() {
        return playerDAO.getAllPlayers();
    }

    @Override
    public Player getPlayer(long id) {
        return playerDAO.getPlayer(id);
    }

    @Override
    public Player createNewPlayer() {
        return null;
    }

    @Override
    public Player updatePlayer() {
        return null;
    }

    @Override
    public void deletePlayer() {

    }
}
