package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.stream.Collectors;

import static com.game.repository.PlayerSpec.*;


@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    PlayerDAO playerDAO;

    @Override
    public List<Player> getPlayersList(String name, String title, Race race, Profession profession,
                                       Long after, Long before, Boolean banned, Integer minExperience,
                                       Integer maxExperience, Integer minLevel, Integer maxLevel,
                                       PlayerOrder order, Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(order.getFieldName());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        return playerDAO.findAll(hasName(name)
                        .and(hasTitle(title))
                        .and(hasRace(race))
                        .and(hasProfession(profession))
                        .and(hasBirthday(after, before))
                        .and(hasBanned(banned))
                        .and(hasExperience(minExperience, maxExperience))
                        .and(hasLevel(minLevel, maxLevel)), pageRequest)
                .stream().collect(Collectors.toList());
    }


    @Override
    public Player getPlayer(Long id) {
        playerExistById(id);
        return playerDAO.findById(id).get();
    }

    @Override
    @Transactional
    public void createPlayer(Player player) {
        playerDAO.save(player);
    }

    @Override
    @Transactional
    public Player updatePlayer() {
        return null;
    }

    @Override
    @Transactional
    public void deletePlayer(Long id) {
        playerExistById(id);
        playerDAO.deleteById(id);
    }


    private void playerExistById(Long id) {
        if (!(playerDAO.existsById(id)))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Such player is not found");
    }

}
