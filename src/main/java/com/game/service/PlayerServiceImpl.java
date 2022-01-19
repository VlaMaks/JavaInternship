package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerDAO;
import com.game.validation.PlayerValidation;
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
    public Player createPlayer(Player player) {
        if (player.getName() == null && player.getTitle() == null && player.getExperience() == null & player.getBirthday() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Параметры игрока не заданы");
        }
        PlayerValidation playerValidation = new PlayerValidation(player);
        playerValidation.isCorrectParams();
        if (playerValidation.isBannedNull()) {
            player.setBanned(false);
        }
        player.setLevel(calculateLevel(player.getExperience()));
        player.setUntilNextLevel(calculateNextLevel(player.getExperience(), player.getLevel()));
        return playerDAO.save(player);
    }

    @Override
    @Transactional
    public Player updatePlayer(Long id, Player player) {
       playerExistById(id);
       Player updatePlayer = playerDAO.findById(id).get();
       PlayerValidation playerValidation = new PlayerValidation(player);
       if (playerValidation.isNameNull())
            player.setName(updatePlayer.getName());
       else {
           if (playerValidation.isNameNotValid()) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
           }
       }

       if (playerValidation.isTitleNull())
            player.setTitle(updatePlayer.getTitle());
       else {
           if (playerValidation.isTitleNotValid()) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
           }
       }

       if (playerValidation.isRaceNull())
            player.setRace(updatePlayer.getRace());

        if (playerValidation.isProfessionNull())
            player.setProfession(updatePlayer.getProfession());

        if (playerValidation.isBirthDayNull())
            player.setBirthday(updatePlayer.getBirthday());

        if (playerValidation.isBannedNull())
            player.setBanned(updatePlayer.isBanned());
        else {
            if (playerValidation.isDateOfBirthdayNegative()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        if (playerValidation.isExperienceNull()) {
            player.setExperience(updatePlayer.getExperience());
            player.setLevel(updatePlayer.getLevel());
            player.setUntilNextLevel(updatePlayer.getUntilNextLevel());
        } else {
            if (playerValidation.isExperienceNotCorrect()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            player.setLevel(calculateLevel(player.getExperience()));
            player.setUntilNextLevel(calculateNextLevel(player.getExperience(), player.getLevel()));
        }

        player.setId(updatePlayer.getId());

        return playerDAO.save(player);

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

    private int calculateLevel(int exp) {
        return ((int) Math.sqrt(2500 + 200 * exp) - 50) / 100;
    }

    private int calculateNextLevel(int exp, int level) {
        return 50 * (level + 1) * (level + 2) - exp;
    }
}
