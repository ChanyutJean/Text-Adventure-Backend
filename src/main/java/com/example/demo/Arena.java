package com.example.demo;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Arena {

    public static void atArena(Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        if(hero.isInBag("Master")) {

            output.write(("You stepped into the arena..." + "\n").getBytes());

            boolean breakLoop;

            switch (hero.getArena()) {

                case 0:

                    breakLoop = fightMonster(hero, r, c, 500, "Goblin", new boolean[]{true, true, true, false}, output, reader);
                    if (breakLoop) {
                        break;
                    }

                case 1:

                    breakLoop = fightMonster(hero, r, c, 200, "Fairy", new boolean[]{false, false, true, true}, output, reader);
                    if (breakLoop) {
                        break;
                    }//this guy heals 3HP when he makes you defend

                case 2:

                    breakLoop = fightMonster(hero, r, c, 800, "Demon", new boolean[]{false, false, true, true, false, false, true, false, true, false}, output, reader);
                    if (breakLoop) {
                        break;
                    }//if hp<200 then flip algorithm


                case 3:

                    output.write(("...Congratulations! You've defeated the boss of the arena!" + "\n").getBytes());
                    output.write(("-----END-----"  + "\n").getBytes());
                    hero.setGameIsRunning();

            }

        } else {

            output.write(("You need a master sword. You turned back. ").getBytes());

        }

    }

    public static boolean fightMonster(Hero hero, Random r, Combat c, int hp, String name, boolean[] algorithm, FileOutputStream output, BufferedReader reader) throws IOException {

        Monster mon = new Monster(hp, name, algorithm);

        c.fight(hero, mon, r, output, reader);

        boolean defeated = (mon.getHp() <= 0);
        boolean breakLoop = false;

        if (defeated) {
            hero.setArena();
        } else {
            breakLoop = true;
        }

        return breakLoop;

    }

}
