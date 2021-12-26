package com.game.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name="player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", birthday=" + birthday +
                ", isBanned=" + isBanned +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                '}';
    }

    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "banned")
    private boolean isBanned;

    @Column(name = "experience")
    private int experience;

    @Column(name = "level")
    private int level;

    @Column(name = "untilNextLevel")
    private int untilNextLevel;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(int untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Player() {
    }

    public Player(long id, String name, String title, Race race, Profession profession, Date birthday, boolean isBanned, int experience, int level, int untilNextLevel) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.isBanned = isBanned;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
    }
}
