package com.example.demo;

import java.io.FileOutputStream;
import java.io.IOException;

public class Monster {

    private final int maxHp;
    private int hp;
    private final String name;
    private final boolean[] algorithm;

    public Monster(int value1, String value2, boolean[] value3) {
        maxHp = value1;
        hp = value1;
        name = value2;
        algorithm = value3;
    }

    public void changeHp(int value, FileOutputStream output) throws IOException {
        hp = hp + value;
        output.write(("Your opponent's HP changed by " + value + "." + "\n").getBytes());
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public boolean[] getAlgorithm() {
        return algorithm;
    }

    public void resetHp() {
        hp = maxHp;
    }

    public void printAlgorithm(FileOutputStream output) throws IOException {

        output.write(("You defeated " + name + "!" + "\n").getBytes());
        output.write(("Monster Action Algorithm: ").getBytes());

        for (boolean i : algorithm) {
            if (i) {
                output.write(("Attack ").getBytes());
            } else {
                output.write(("Defend ").getBytes());
            }
        }

        output.write(("\n\n").getBytes());

    }


}
