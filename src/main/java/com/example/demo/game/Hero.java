package com.example.demo.game;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Hero {

    public static final int BAG_LENGTH = 10;
    public static final int INITIAL_GOLD = 300;
    public static final int MIN_HP = 200;
    public static final int MAX_HP = 300;

    private int maxHp;
    private int hp;
    private String job;
    private String name;
    private int str;
    private int dex;
    private int con;
    private int gold;
    private String location;
    private int quest;
    private int talk = 0;
    private int arena = 0;
    private boolean fountain = false;
    private boolean restart = false;
    private boolean gameIsRunning = true;
    private final boolean[] lostTo = new boolean[]{false, false, false}; //guard, priest, tycoon
    private final String[] bag = new String[BAG_LENGTH];

    public void setTalk() {
        if (talk < 10) {
            talk++;
        } else {
            talk = 1;
        }
    }

    public void setArena() {
        arena++;
    }

    public void setFountain() {
        fountain = true;
    }

    public void setRestart() {
        restart = true;
    }

    public void setGameIsRunning() {
        gameIsRunning = false;
    }

    public void setLostTo(int i) {
        lostTo[i] = true;
    }

    public void changeHp(int value, FileOutputStream output) throws IOException {
        hp = hp + value;
        output.write(("Your HP changed by " + value + "." + "\n").getBytes());
    }

    public void changeGold(int value, FileOutputStream output) throws IOException {
        gold += value;
        output.write(("Your gold changed by " + value + "." + "\n").getBytes());
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int value) {
        maxHp = value;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int value) {
        hp = value;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int value) {
        str = value;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int value) {
        dex = value;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int value) {
        con = value;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int value) {
        gold = value;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String value) {
        location = value;
    }

    public int getQuest() {
        return quest;
    }

    public void setQuest(int value) {
        quest = value;
    }

    public int getTalk() {
        return talk;
    }

    public int getArena() {
        return arena;
    }

    public boolean getFountain() {
        return fountain;
    }

    public boolean getRestart() {
        return restart;
    }

    public boolean getGameIsRunning() {
        return gameIsRunning;
    }

    public boolean getLostTo(int i) {
        return lostTo[i];
    }

    public void addItemToBag(String value) {
        for (int i = 0; i < BAG_LENGTH; i++) {
            if (bag[i] == null) {
                bag[i] = value;
                break;
            }
        }
    }

    public boolean isInBag(String value) {
        for (int i = 0; i < BAG_LENGTH; i++) {
            if (value.equals(bag[i])) {
                return true;
            }
        }
        return false;
    }

    public void printBag(FileOutputStream output) throws IOException {
        if (bag[0] == null) {
            output.write(("None").getBytes());
        }
        for (String i : bag) {
            if (!(i == null)) {
                output.write((i + " ").getBytes());
            }
        }
    }

    public void setHeroStat(FileOutputStream output, BufferedReader reader) throws IOException {

        output.write(("Choose your hero name: ").getBytes());

        String line;
        if ((line = reader.readLine()) != null) {
            name = line;
            output.write((name + "\n").getBytes());
        } else {
            throw new EOFException();
        }

        String heroJob = "";

        while (!(heroJob.equals("Warrior") || heroJob.equals("Mage") || heroJob.equals("Ranger"))) {

            output.write(("Choose your hero job (Warrior, Mage, Ranger): ").getBytes());

            if ((line = reader.readLine()) != null) {
                heroJob = line;
                output.write((heroJob + "\n").getBytes());
            } else {
                throw new EOFException();
            }

        }

        job = heroJob;
    }

    public void assignStat() {

        str = (Main.hash(5) + 10);
        dex = (Main.hash(6) + 9);
        con = (Main.hash(7) + 8);
        hp = (Main.hash(Hero.MAX_HP - Hero.MIN_HP + 1) + Hero.MIN_HP);
        maxHp = hp;

    }

    public void showStat(FileOutputStream output) throws IOException {

        output.write(("Your hero's stat is:" + "\n" +
                "Name: " + name + "\n" +
                "Job: " + job + "\n" +
                "HP: " + hp + "/" + maxHp + "\n" +
                "Strength: " + str + "\n" +
                "Dexterity: " + dex + "\n" +
                "Constitution: " + con + "\n" +
                "Gold: " + INITIAL_GOLD + "\n").getBytes());

    }

    public void showNewStat(FileOutputStream output) throws IOException {

        showStat(output);

        output.write(("Quest: " + "\n").getBytes());

        switch (quest) {

            case 0:
                output.write(("None at the moment." + "\n").getBytes());
                break;
            case 1:
                output.write(("Catch the Robbers!" + "\n").getBytes());
                break;
            case 2:
                output.write(("Apprehend the Tycoon!" + "\n").getBytes());
                break;
            case 3:
                output.write(("Conquer the Arena!" + "\n").getBytes());
                break;

        }

        output.write(("Inventory: ").getBytes());
        printBag(output);

    }

}
