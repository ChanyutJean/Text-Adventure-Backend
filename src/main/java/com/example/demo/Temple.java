package com.example.demo;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Temple {

    public static void atTemple(Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        printTemple(hero, output);

        boolean invalidInput = true;

        while(invalidInput) {

            int option;

            try {
                option = Main.getInt(output, reader);
            } catch (EOFException e) {
                return;
            }

            invalidInput = setTemple(option, hero, r, c, output, reader);

        }

    }

    public static void printTemple(Hero hero, FileOutputStream output) throws IOException {

        output.write(("You are now in the temple!" + "\n").getBytes());
        output.write(("Your HP: " + hero.getHp() + "/" + hero.getMaxHp() + ". Your Gold: " + hero.getGold() + "." + "\n").getBytes());
        output.write(("(1) Heal for 10 gold" + "\n").getBytes());
        if (hero.getQuest() == 1 && !hero.isInBag("Cloth")) {output.write(("(2) Inspect graveyard" + "\n").getBytes());}
        output.write(("(0) Go back to the town square" + "\n").getBytes());

        output.write(("What to do? ").getBytes());

    }

    public static boolean setTemple(int option, Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        boolean invalidInput = false;

        switch (option) {

            case 1:
                if (hero.getGold() >= 10) {
                    hero.changeGold(-10, output);
                    output.write(("HP replenished." + "\n\n").getBytes());
                    hero.setHp(hero.getMaxHp());
                } else {
                    output.write(("Not enough money!" + "\n\n").getBytes());
                }
                break;
            case 2:
                if (hero.getQuest() == 1 && !hero.isInBag("Cloth")) {
                    if (!hero.isInBag("Repel")) {

                        boolean defeated = priestFight(hero, r, c, output, reader);

                        if(defeated) {
                            output.write(("Hmm! I guess you can go in the grave with this power of yours. Here, take this ghost repellent." + "\n").getBytes());
                            output.write(("Repellent was added to the inventory!" + "\n\n").getBytes());
                            hero.addItemToBag("Repel");
                        } else {
                            hero.setLostTo(1);
                            break;
                        }

                    }
                    hero.setLocation("Grave");
                } else {
                    output.write(("Enter a valid number! ").getBytes());
                    invalidInput = true;
                }
                break;
            case 0:
                hero.setLocation("Town");
                output.write(("\n").getBytes());
                break;
            default:
                output.write(("Enter a valid number! ").getBytes());
                invalidInput = true;
        }

        return invalidInput;

    }

    public static boolean priestFight(Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        if (!hero.getLostTo(1)) {
            output.write(("\n" + "Wait! You shouldn't go there, it's dangerous!" + "\n").getBytes());
            output.write(("...Ah. So you are the one catching the robber." + "\n").getBytes());
            output.write(("Still, it's very dangerous, and I won't let you past unless you prove yourself that you are capable!" + "\n").getBytes());
        } else {
            output.write(("\n" + "Like I said, it's very dangerous, and I won't let you past unless you prove yourself that you are capable!" + "\n").getBytes());
        }

        Monster Priest = new Monster(300, "Priest", new boolean[]{true, true, false});

        c.fight(hero, Priest, r, output, reader);

        return (Priest.getHp() <= 0);

    }

}
