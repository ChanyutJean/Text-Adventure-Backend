package com.example.demo;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Manor {

    public static void atManor(Hero hero, Random r, Combat c, FileOutputStream output, BufferedReader reader) throws IOException {

        if (!hero.getLostTo(2)) {

            output.write(("You entered into the manor... and found the guard!" + "\n").getBytes());
            output.write(("What do you want here? This place is very secluded, only people who know the password can enter!" + "\n").getBytes());
            output.write(("What's the password? ").getBytes());

            String password;

            if (reader.ready()) {
                password = reader.readLine();
                output.write((password + "\n").getBytes());
            } else {
                return;
            }

            if (password.equals("Hello")) {

                output.write(("Welcome! What do you want here? ...What, you want me to admit that I'm a grave robber? Never!" + "\n").getBytes());

            } else {

                output.write(("Scram! Go to the tavern or something, you aren't worth coming in!" + "\n").getBytes());
                return;

            }
            output.write(("\n").getBytes());

        } else {

            output.write(("You again!" + "\n").getBytes());

        }

        Monster Tycoon = new Monster(500, "Tycoon", new boolean[]{false, false, true});

        c.fight(hero, Tycoon, r, output, reader);

        boolean defeated = (Tycoon.getHp() <= 0);

        if (defeated) {

            output.write(("The tycoon lost and admits his guilt of grave robbery! Tycoon's guilty letter is added to the bag. Give it to the guards!" + "\n\n").getBytes());
            hero.addItemToBag("Letter");

        } else {

            output.write(("It'll take you a million years until you can win against me!" + "\n\n").getBytes());

        }

    }

}
