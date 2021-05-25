package com.game.entity;

import com.game.entity.Profession;
import com.game.entity.Race;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "player")

public class Player implements Serializable {
    final static int maxNameLength = 12;
    final static int maxTitleLength = 30;
    final static Date Year2000 = new Date(946674000000l);
    final static Date Year3000 = new Date(32503665599000l);

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "title")
    private String title;
    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;
    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "level")
    private Integer level;
    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "banned")
    private Boolean banned;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", birthday=" + birthday +
                ", banned=" + banned +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public boolean writeCheckAndPrepare(){


        boolean noName = this.getName() == null;
        boolean noTitle = this.getTitle() == null;
        boolean noRace = this.getRace() == null;
        boolean noProfession = this.getProfession() == null;
        boolean noBirthDay = this.getBirthday() == null;
        boolean noExpirience = this.getExperience() == null;
        boolean tooLongName = noName || this.name.length() > Player.maxNameLength;
        boolean tooLongTitle = noTitle || this.title.length() > Player.maxTitleLength;
        boolean emptyName = noName || this.name.isEmpty();
        boolean wrongExperience = noExpirience || this.experience < 0 || this.experience > 10000000;
        boolean wrongBirthday = noBirthDay || this.birthday.before(Player.Year2000) || this.birthday.after(Player.Year3000);

        boolean mayBeWritten = !(noName || noTitle || noRace || noProfession || noBirthDay || noExpirience || tooLongName || tooLongTitle || emptyName || wrongExperience || wrongBirthday);

        if (mayBeWritten) {
            if (this.getBanned() == null)
                this.setBanned(false);
            this.level =(int) Math.floor((Math.sqrt(2500 + 200 * this.experience) - 50) / 100);
            this.untilNextLevel = 50 * (this.level + 1) * (this.level + 2) - this.experience;
        }
        return mayBeWritten;
    }
}
