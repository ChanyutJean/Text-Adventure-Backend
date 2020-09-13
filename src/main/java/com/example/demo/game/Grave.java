package com.example.demo.game;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class Grave {

    public static void atGrave(Hero hero, FileOutputStream output, BufferedReader reader) throws IOException {

        output.write(("\n" + "You entered into the graveyard... surrounded by mysterious fog." + "\n").getBytes());

        while (true) {

            boolean graveResult;

            graveResult = graveTurn(output, reader);

            if (graveResult) {
                output.write(("You got a torn cloth. Torn cloth was put into inventory, show it to the guards!" + "\n").getBytes());
                hero.addItemToBag("Cloth");
                break;
            } else {
                output.write(("You wound up at the entrance of the graveyard! You lost some health by the mysterious fog!" + "\n").getBytes());

                if (hero.getHp() >= 10) {
                    hero.changeHp(-10, output);

                    boolean retry;

                    retry = Main.getBoolean("Retry?", output, reader);

                    if (!retry) {
                        break;
                    }
                } else {
                    hero.setHp(1);
                    output.write(("You are out of HP!" + "\n").getBytes());
                    break;
                }

                output.write(("\n").getBytes());

            }

        }

        output.write(("You walked back to the temple." + "\n\n").getBytes()); //event ends

    }

    public static boolean graveTurn(FileOutputStream output, BufferedReader reader) throws IOException {

        boolean turn;

        turn = Main.getBoolean("You came across a crossroad. Do you turn to your right?", output, reader);
        if (turn) {
            return false;
        }
        turn = Main.getBoolean("Next, do you turn to your left?", output, reader);
        if (turn) {
            return false;
        }
        turn = Main.getBoolean("Next, do you go straight?", output, reader);
        if (!turn) {
            return false;
        }
        turn = Main.getBoolean("You came across some stairs, going up and down. Do you climb up the stairs?", output, reader);
        if (turn) {
            return false;
        }
        turn = Main.getBoolean("You become fatigued. Forge on?", output, reader);
        if (!turn) {
            return false;
        }
        turn = Main.getBoolean("You've come far. Do you turn around and detour?", output, reader);
        if (turn) {
            return false;
        }
        turn = Main.getBoolean("After a long walk, you heard some mechanical noise behind you. Turn back and detour?", output, reader);
        if (!turn) {
            return false;
        }
        turn = Main.getBoolean("You found a tombstone, do you inspect it?", output, reader);
        if (!turn) {
            return false;
        }
        turn = Main.getBoolean("Inside lies an empty coffin, do you continue your inspection?", output, reader);
        if (!turn) {
            return false;
        }
        turn = Main.getBoolean("You found a torn piece of cloth, do you take it?", output, reader);

        return turn;

    }

}
