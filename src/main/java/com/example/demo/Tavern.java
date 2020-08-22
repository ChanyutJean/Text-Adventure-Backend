package com.example.demo;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Tavern {

    public static void atTavern(Hero hero, FileOutputStream output, BufferedReader reader) throws IOException {

        printTavern(hero, output);

        boolean invalidInput = true;

        while (invalidInput) {

            int option;

            try {
                option = Main.getInt(output, reader);
            } catch (EOFException e) {
                return;
            }

            invalidInput = setTavern(option, hero, output);

        }

    }

    public static void printTavern(Hero hero, FileOutputStream output) throws IOException {

        output.write(("You are now in the tavern" + "\n").getBytes());
        output.write(("Your HP: " + hero.getHp() + "/" + hero.getMaxHp() + ". Your Gold: " + hero.getGold() + "." + "\n").getBytes());
        output.write(("(1) Buy a drink and talk" + "\n").getBytes());
        output.write(("(0) Go back to the town square" + "\n").getBytes());
        output.write(("What to do? ").getBytes());

    }

    public static boolean setTavern(int option, Hero hero, FileOutputStream output) throws IOException {

        boolean invalidInput = false;

        switch(option) {

            case 1:
                if (hero.getQuest() == 2) {
                    randomTalk(true, hero, output);
                } else {
                    randomTalk(false, hero, output);
                }
                break;
            case 0:
                hero.setLocation("Town");
                break;
            default:
                invalidInput = true;
        }

        return invalidInput;

    }

    public static void randomTalk(boolean questRelated, Hero hero, FileOutputStream output) throws IOException {

        if (!(hero.getGold() == 0)) {
            hero.changeGold(-1, output);
            if (questRelated) {
                output.write(("Know this rich family manor near here? The guard's strict, but just say Hello to them and they'll let you in." + "\n\n").getBytes());
            } else {
                switch (hero.getTalk()) {
                    case 0:
                        output.write(("Hi. I'm running a student's immensely complex game program. I'd give this guy a lot of bonus points, He did good." + "\n").getBytes());
                        hero.setTalk();
                        break;
                    case 1:
                        output.write(("Struggling with fighting the guard? First, just defend, then attack, then repeat, then you're done!" + "\n").getBytes());
                        hero.setTalk();
                        break;
                    case 2:
                        output.write(("Do you know that your Str helps you attack and your Dex reduces damage taken?" + "\n").getBytes());
                        hero.setTalk();
                        break;
                    case 3:
                        output.write(("You know, your " + hero.getJob() + " job doesn't do anything. That's kinda sad." + "\n").getBytes());
                        hero.setTalk();
                        break;
                    case 4:
                        output.write(("Have you ever been in a fight? Normally, your opponent will act on a set algorithm. You can always find that out and counter it!" + "\n").getBytes());
                        hero.setTalk();
                        break;
                    case 5:
                        output.write(("Did you know, Con stat helps increase your money obtained! From where? Hm... somewhere here." + "\n").getBytes());
                        hero.setTalk();
                        break;
                    case 6:
                        output.write(("\n").getBytes());
                        hero.setTalk();
                        break;
                    case 7:
                        output.write(("\n").getBytes());
                        hero.setTalk();
                        break;
                    case 8:
                        output.write(("\n").getBytes());
                        hero.setTalk();
                        break;
                    case 9:
                        output.write(("You really liked talking to me huh? Well, here's you prize." + "\n").getBytes());
                        hero.changeGold(5 + hero.getCon(), output);
                        hero.setTalk();
                        break;
                    case 10:
                        output.write(("I'm really out of things to talk. Don't talk to me again, else I'll just repeat everything." + "\n").getBytes());
                        hero.setTalk();
                        break;
                    default:
                        output.write(("Dude. You're not supposed to see this text in game. What on earth did you do?" + "\n").getBytes());
                        hero.setTalk();
                        break;

                }

                output.write(("\n").getBytes()); //ends talk
            }
        } else {
            output.write(("Not enough money!" + "\n").getBytes());
        }

    }

}
