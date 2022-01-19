package com.game.validation;

import com.game.entity.Player;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PlayerValidation {
    private Player player;

    public PlayerValidation(Player player) {
        this.player = player;
    }

    public PlayerValidation() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isTitleNull() {
        return player.getTitle() == null;
    }

    public boolean isNameNull() {
        return player.getName() == null;
    }

    public boolean isRaceNull() {
        return player.getRace() == null;
    }

    public boolean isProfessionNull() {
        return player.getProfession() == null;
    }

    public boolean isBannedNull() {
        return player.isBanned() == null;
    }

    public boolean isBirthDayNull() {
        return player.getBirthday() == null;
    }

    public boolean isExperienceNull() {
        return player.getExperience() == null;
    }

    public boolean isNameNotValid() {
        return player.getName().length() > 12;
    }

    public boolean isTitleNotValid() {
        return player.getTitle().length() > 30;
    }

    public boolean isNameEmpty() {
        return player.getName().isEmpty();
    }

    public boolean isTitleEmpty() {
        return player.getTitle().isEmpty();
    }

    public boolean isExperienceNotCorrect() {
        return !(player.getExperience() >= 0 && player.getExperience() <= 10000000);
    }

    public boolean isDateOfBirthdayNegative() {
        return player.getBirthday().getTime() < 0;
    }

    /*boolean isDateOfBirthDayNotCorrect() {
        return player.getBirthday().getTime()
    }*/

    public boolean isCorrectParams() {
        StringBuilder message = new StringBuilder();
        boolean isException = false;
        if (isNameEmpty()) {
            isException = true;
            message.append("Имя игрока не должно быть пустой строкой; ");
        }

        if (isNameNotValid()) {
            isException = true;
            message.append("Длина имени игрока не должна превышать 12 символов; ");
        }

        if (isTitleNotValid()) {
            isException = true;
            message.append("Длина титула игрока не должна превышать 30 символов; ");
        }

        if (isExperienceNull()) {
            isException = true;
            message.append("Не задан опыт игрока; ");
        }
        else if (isExperienceNotCorrect()) {
            isException = true;
            message.append("Опыт игрока должен находиться в диапазоне от 0 до 10000000; ");
        }

        if (isBirthDayNull()) {
            isException = true;
            message.append("Не задана дата регистрации игрока; ");
        } else if (isDateOfBirthdayNegative()) {
            isException = true;
            message.append("Дата регистрации не может быть отрицательным числом; ");
        }

        if (isException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
        }
        return false;
    }

}
