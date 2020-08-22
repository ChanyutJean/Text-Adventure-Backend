package com.example.demo;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Castle {

    public static void atCastle(Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        printCastle(hero, output);

        boolean invalidInput = true;

        while(invalidInput) {

            int option;

            try {
                option = Main.getInt(output, reader);
            } catch (EOFException e) {
                return;
            }

            invalidInput = setCastle(option, hero, r, c, output, reader);

        }

    }

    public static void printCastle(Hero hero, FileOutputStream output) throws IOException {

        output.write(("You are now in the castle!" + "\n").getBytes());
        output.write(("Your HP: " + hero.getHp() + "/" + hero.getMaxHp() + ". Your Gold: " + hero.getGold() + "." + "\n").getBytes());
        output.write(("(1) Work for the king" + "\n").getBytes());
        if (hero.getQuest() == 0) {output.write(("(2) Talk to the guards" + "\n").getBytes());}
        if (hero.getQuest() == 1 && hero.isInBag("Cloth")) {output.write(("(2) Tell the guards about the cloth" + "\n").getBytes());}
        if (hero.getQuest() == 2 && hero.isInBag("Letter")) {output.write(("(2) Give letter to the guards" + "\n").getBytes());}
        output.write(("(0) Go back to the town square" + "\n").getBytes());

        output.write(("What to do? ").getBytes());
    }

    public static boolean setCastle(int option, Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        output.write(("\n").getBytes());

        boolean invalidInput = false;

        switch (option) {

            case 1:
                castleWork(hero, output);
                break;
            case 2:
                if (hero.getQuest() == 0) {
                    guardTalk(hero, r, c, output, reader);
                } else if (hero.getQuest() == 1 && hero.isInBag("Cloth")) {
                    guardCloth(hero, output);
                } else if (hero.getQuest() == 2 && hero.isInBag("Letter")) {
                    guardLetter(hero, output);
                } else {
                    output.write(("Enter a valid number! ").getBytes());
                    invalidInput = true;
                }
                break;
            case 0:
                hero.setLocation("Town");
                break;
            default:
                output.write(("Enter a valid number! ").getBytes());
                invalidInput = true;

        }

        return invalidInput;

    }

    public static void castleWork(Hero hero, FileOutputStream output) throws IOException {

        hero.changeGold(1, output);
        output.write(("\n").getBytes());

    }

    public static void guardTalk(Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        if (!hero.getLostTo(0)) {

            output.write(("Hello there, " + hero.getName() + ". There has been a lot of grave robbing incidents lately." + "\n").getBytes());
            output.write(("I don't really have time for this, so can you find and catch the grave robbers?" + "\n").getBytes());

            boolean accept;

            for (int i = 0; i < 3; i++) {

                try {
                    accept = Main.getBoolean("Accept?", output, reader);
                } catch (EOFException e) {
                    return;
                }

                if (accept) {

                    output.write(("Nice! Now to make sure that you are capable of self-defense..." + "\n" + "\n").getBytes());
                    guardFight(hero, r, c, output, reader);
                    break;

                } else {

                    switch (i) {
                        case 0:
                            output.write(("You will get big rewards from the king! We need somebody!" + "\n").getBytes());
                            break;
                        case 1:
                            output.write(("Please!!!" + "\n").getBytes());
                            break;
                        case 2:
                            output.write(("This is an order." + "\n").getBytes());

                            while (!accept) {
                                try {
                                    accept = Main.getBoolean("Accept?", output, reader);
                                } catch (EOFException e) {
                                    return;
                                }
                            }

                            output.write(("Nice! Now to make sure that you are capable of self-defense..." + "\n" + "\n").getBytes());
                            guardFight(hero, r, c, output, reader);
                            break;
                    }

                }

            }

        } else {

            output.write(("You've come again. Let's try this again!" + "\n").getBytes());
            guardFight(hero, r, c, output, reader);

        }
    }

    public static void guardFight(Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        Monster Guard = new Monster(200, "Guard", new boolean[] {true, false});

        c.fight(hero, Guard, r, output, reader);

        boolean defeated = (Guard.getHp() <= 0);

        if (defeated) {
            output.write(("Thank you! The graveyard should be around the temple." + "\n").getBytes());
            output.write(("Accepted Quest: Catch the Robbers!" + "\n" + "\n").getBytes());
            hero.setQuest(1);
        } else {
            hero.setLostTo(0);
        }

    }

    public static void guardCloth(Hero hero, FileOutputStream output) throws IOException {

        output.write(("The guards tell you that this cloth belongs to the 'drunk' tycoon who lives in the manor!" + "\n").getBytes());
        output.write(("Accepted Quest: Apprehend the Tycoon!" + "\n" + "\n").getBytes());
        hero.setQuest(2); //event ends

    }

    public static void guardLetter(Hero hero, FileOutputStream output) throws IOException {

        output.write(("Thank you! Now we don't have to worry about grave robberies again! Here's your reward from the king!" + "\n").getBytes());
        hero.changeGold(500, output);
        if (!hero.getRestart()) {
            output.write(("With this much power defeating the tycoon, you might want to try fighting in the arena!" + "\n").getBytes());
            output.write(("Accepted Quest: Conquer the Arena!" + "\n").getBytes());
            hero.setQuest(3);
        } else {
            output.write(("\n" + "Congratulations, you have finished the game!" + "\n").getBytes());
            output.write(("Next time, try beating the game without losing to or running from the opponent in a fight for a surprise!" + "\n").getBytes());
            hero.setGameIsRunning();
        }
        output.write(("\n").getBytes());

    }

}
