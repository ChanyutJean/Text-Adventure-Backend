package com.example.demo.game;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Combat {

    private boolean playerRuns = false;
    private boolean playerLoseTurn = false;
    private boolean monsterLoseTurn = false;
    private byte consecutiveShield = 0;

    public static void printInitCombat(Monster mon, FileOutputStream output) throws IOException {

        output.write(("You encountered a " + mon.getName() + "!" + "\n").getBytes());
        output.write(("(1) Attack with Sword" + "\n").getBytes());
        output.write(("(2) Block with Shield" + "\n").getBytes());
        output.write(("(0) Run away" + "\n\n").getBytes());

    }

    public static void printCombat(Hero hero, Monster mon, FileOutputStream output) throws IOException {

        output.write(("You have " + hero.getHp() + "HP and " + mon.getName() + " has " + mon.getHp() + "HP." + "\n").getBytes());
        output.write(("What do you do? (1, 2, 0): ").getBytes());

    }

    public static boolean playerAttack(boolean monsterLoseTurn, byte i, Hero hero, Monster mon, FileOutputStream output) throws IOException, NoSuchAlgorithmException {

        boolean playerLoseTurn = false;

        if (!monsterLoseTurn) {

            boolean monsterStance = mon.getAlgorithm()[i];

            if (mon.getName().equals("Demon") && mon.getHp() < 200) {

                monsterStance = !monsterStance;

            }

            if (monsterStance) {
                output.write(("You attack! Your opponent attacks!" + "\n").getBytes());
                hero.changeHp(-Main.hash(11) + hero.getDex() - 20, output); //Decrease 5-20 HP
                mon.changeHp(-Main.hash(11) - hero.getStr() + 5, output); //Decrease 5-20 HP
            } else {
                output.write(("You attack! Your opponent shields! You flinched!" + "\n").getBytes());
                playerLoseTurn = true;
            }

        } else {
            output.write(("You attack while your opponent is flinched." + "\n").getBytes());
            mon.changeHp(-Main.hash(11) - hero.getStr() - 5, output); //Decrease 15-30 HP
        }

        return playerLoseTurn;
    }

    public static boolean playerDefend(boolean monsterLoseTurn, byte i, Monster mon, FileOutputStream output) throws IOException {

        if (!monsterLoseTurn) {

            boolean monsterStance = mon.getAlgorithm()[i];

            if (mon.getName().equals("Demon") && mon.getHp() < 200) {

                monsterStance = !monsterStance;

            }

            if (monsterStance) {
                output.write(("You shield! Your opponent attacks! Your opponent flinched!" + "\n").getBytes());
                monsterLoseTurn = true;
            } else {
                output.write(("You shield! Your opponent shields! Nothing happened!" + "\n").getBytes());
            }

        } else {
            output.write(("You shield while your opponent is flinched. Nothing happened." + "\n").getBytes());
            monsterLoseTurn = false;
        }

        return monsterLoseTurn;

    }

    public static boolean determineDefend(byte consecutiveShield, Hero hero, FileOutputStream output) throws IOException, NoSuchAlgorithmException {

        if (consecutiveShield < 12) {

            if (!(consecutiveShield > 5 && Main.hash(100) < 110 - (10 * consecutiveShield))) {

                return true; //1st to 5th time 100% succeed, 6th time 50% succeed, 7th time 40% succeed, 8th time 30% succeed and so on.

            } else {
                output.write(("You shielded too much consecutively, your opponent figures your shield out and pierces!" + "\n").getBytes());
                hero.changeHp(-Main.hash(11) + hero.getDex() - 30, output); //Decrease 15-30 HP
            }

        } else {
            output.write(("Your opponent pierces your shield for a fatal attack!" + "\n").getBytes());
            hero.setHp(1); //prevents continuous shielding even after consecutive warning
        }

        return false;

    }

    public static boolean playerRun(Hero hero, Monster mon, byte i, FileOutputStream output) throws IOException, NoSuchAlgorithmException {

        boolean playerRuns = false;

        if (Main.hash(100) > 49) {
            output.write(("You ran away." + "\n").getBytes());
            playerRuns = true;
        } else {
            output.write(("Run attempt failed!" + "\n").getBytes());
            if (mon.getAlgorithm()[i]) {
                output.write(("Your opponent attacks!" + "\n").getBytes());
                hero.changeHp(-Main.hash(11) + hero.getDex() - 20, output); //Decrease 5-20 HP
            } else {
                output.write(("Your opponent shields!" + "\n").getBytes());
            }
        }

        return playerRuns;

    }

    public static void playerIdle(int i, Monster mon, Hero hero, FileOutputStream output) throws IOException, NoSuchAlgorithmException {

        if (mon.getAlgorithm()[i]) {
            output.write(("You lost a turn! Your opponent attacks!" + "\n").getBytes());
            hero.changeHp(-Main.hash(11) + hero.getDex() - 20, output); //Decrease 5-20 HP

        } else {
            output.write(("You lost a turn! Your opponent shields!" + "\n").getBytes());
        }

    }

    public static void playerLost(Hero hero, FileOutputStream output) throws IOException {

        hero.setHp(hero.getMaxHp());
        hero.setGold(0);
        hero.setLocation("Temple");
        output.write(("You lost to your opponent! With your last health, you ran back to heal at the temple." + "\n").getBytes());
        output.write(("Along the way back, you encountered a robber and lost all your gold along the way." + "\n").getBytes());

    }

    public void fight(Hero hero, Monster mon, FileOutputStream output, BufferedReader reader) throws IOException, NoSuchAlgorithmException {

        playerRuns = false;
        playerLoseTurn = false;
        monsterLoseTurn = false;
        consecutiveShield = 0;

        printInitCombat(mon, output);

        while (hero.getHp() > 0 && mon.getHp() > 0 && !playerRuns) {

            for (byte i = 0; i < mon.getAlgorithm().length && hero.getHp() > 0 && mon.getHp() > 0 && !playerRuns; i++) {

                if (!playerLoseTurn) {

                    printCombat(hero, mon, output);

                    int option = Main.getInt(output, reader);

                    playerAct(option, i, hero, mon, output);

                } else {

                    playerIdle(i, mon, hero, output);

                    playerLoseTurn = false;
                }

                output.write(("\n").getBytes());
            }
        }

        if (mon.getHp() <= 0) {

            mon.printAlgorithm(output);

        } else {

            mon.resetHp();

            if (!playerRuns && !mon.getName().equals("Madman")) {
                playerLost(hero, output); //don't go to temple if opponent is madman
            }
            hero.setRestart(); //no more arena fight
        }

    }

    public void playerAct(int option, byte i, Hero hero, Monster mon, FileOutputStream output) throws IOException, NoSuchAlgorithmException {

        switch (option) {

            case 1:

                consecutiveShield = 0;

                playerLoseTurn = playerAttack(monsterLoseTurn, i, hero, mon, output);

                monsterLoseTurn = false;

                break;

            case 2:

                if (mon.getName().equals("Fairy")) {

                    output.write(("Fairy heals itself while you defend!" + "\n").getBytes());
                    mon.changeHp(3, output);

                }

                consecutiveShield++;

                boolean playerDefends = determineDefend(consecutiveShield, hero, output);

                if (playerDefends) {

                    playerLoseTurn = false;
                    monsterLoseTurn = playerDefend(monsterLoseTurn, i, mon, output);

                }

                break;

            case 0:

                playerRuns = playerRun(hero, mon, i, output);

                break;

            default:

                output.write(("You are at a loss of what to do!" + "\n").getBytes());
                hero.changeHp(-5, output);
        }

    } //items

}
