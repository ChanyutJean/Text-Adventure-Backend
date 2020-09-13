package com.example.demo.game;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class Town {

    public static void atTown(Hero hero, FileOutputStream output, BufferedReader reader) throws IOException {

        printTown(hero, output);

        boolean invalidInput = true;

        while (invalidInput) {

            int option;

            option = Main.getInt(output, reader);

            invalidInput = setTown(option, hero, output, reader);

        }

        output.write(("\n").getBytes());

    }

    public static void printTown(Hero hero, FileOutputStream output) throws IOException {

        output.write(("You are now in the town square!" + "\n").getBytes());
        output.write(("Your HP: " + hero.getHp() + "/" + hero.getMaxHp() + ". Your Gold: " + hero.getGold() + "." + "\n").getBytes());
        output.write(("(1) Visit the temple" + "\n").getBytes());
        output.write(("(2) Go to the castle" + "\n").getBytes());
        output.write(("(3) Spend time in the tavern" + "\n").getBytes());

        if (hero.getQuest() == 2 && !hero.isInBag("Letter")) {
            output.write(("(4) Look around the manor" + "\n").getBytes());
        }
        if (hero.getQuest() == 3) {
            output.write(("(4) Fight in the arena" + "\n").getBytes());
        }
        if (hero.getQuest() == 3 && !hero.isInBag("Master")) {
            output.write(("(5) Buy something in the shop" + "\n").getBytes());
        }

        if (!hero.getFountain()) {
            output.write(("(0) Wish in the fountain" + "\n").getBytes());
        } else {
            output.write(("(0) Check status" + "\n").getBytes());
        }

        output.write(("What to do? ").getBytes());

    }

    public static boolean setTown(int option, Hero hero, FileOutputStream output, BufferedReader reader) throws IOException {

        boolean invalidInput = false;

        switch (option) {

            case 1:
                hero.setLocation("Temple");
                break;
            case 2:
                hero.setLocation("Castle");
                break;
            case 3:
                hero.setLocation("Tavern");
                break;
            case 4:
                if (hero.getQuest() == 2 && !hero.isInBag("Letter")) {
                    hero.setLocation("Manor");
                } else if (hero.getQuest() == 3) {
                    hero.setLocation("Arena");
                } else {
                    output.write(("Enter a valid number! ").getBytes());
                    invalidInput = true;
                }
                break;
            case 5:
                if (hero.getQuest() == 3 && !(hero.isInBag("Master"))) {
                    hero.setLocation("Shop");
                } else {
                    output.write(("Enter a valid number! ").getBytes());
                    invalidInput = true;
                }
                break;
            case 0:
                output.write(("\n").getBytes()); //empty enter line
                if (!hero.getFountain()) {
                    fountain(hero, output, reader);
                } else {
                    hero.showNewStat(output);
                }
                break;
            default:
                output.write(("Enter a valid number! ").getBytes());
                invalidInput = true;

        }

        return invalidInput;

    }

    public static void fountain(Hero hero, FileOutputStream output, BufferedReader reader) throws IOException {

        String wish = "";

        while (!(wish.equals("HP") || wish.equals("Gold") || wish.equals("Str") || wish.equals("Dex") || wish.equals("Con"))) {
            output.write(("What do you wish for? (HP, Gold, Str, Dex, Con): ").getBytes());

            if (reader.ready()) {
                wish = reader.readLine();
                output.write((wish + "\n").getBytes());
            } else {
                return;
            }
        }

        switch (wish) {

            case "HP":
                output.write(("Your max HP changed by 50." + "\n").getBytes());
                hero.setMaxHp(hero.getMaxHp() + 50);
                break;
            case "Gold":
                hero.changeGold(500, output);
                break;
            case "Str":
                output.write(("Your Strength changed by 3." + "\n").getBytes());
                hero.setStr(hero.getStr() + 3);
                break;
            case "Dex":
                output.write(("Your Dexterity changed by 3." + "\n").getBytes());
                hero.setDex(hero.getDex() + 3);
                break;
            case "Con":
                output.write(("Your Constitution changed by 3." + "\n").getBytes());
                hero.setCon(hero.getCon() + 3);
                break;

        }

        hero.setFountain();

    }

}
