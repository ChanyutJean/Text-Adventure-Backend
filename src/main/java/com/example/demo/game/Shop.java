package com.example.demo.game;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class Shop {

    public static void atShop(Hero hero, FileOutputStream output, BufferedReader reader) throws IOException {

        boolean getMaster = false;

        while (!getMaster) {

            Shop.printShop(true, true, hero.isInBag("Master"), hero, output); //already have sword and shield, but maybe not master sword

            int itemToBuy;

            itemToBuy = Main.getInt(output, reader);

            boolean[] shopLoopArray = Shop.buyItem(true, true, false, itemToBuy, hero, output);

            getMaster = !shopLoopArray[0]; //true when user exits or buys master sword and method immediately returns

            output.write(("\n").getBytes());

        }

    }

    public static void printShop(boolean swordSold, boolean shieldSold, boolean masterSold, Hero hero, FileOutputStream output) throws IOException {

        output.write(("Welcome to our shop! Before you begin your adventure, consider buying items!" + "\n").getBytes());

        output.write(("(1) Sword        ($  50)").getBytes());
        if (swordSold) {
            output.write((" (SOLD)").getBytes());
        }
        output.write(("\n").getBytes());

        output.write(("(2) Shield       ($  75)").getBytes());
        if (shieldSold) {
            output.write((" (SOLD)").getBytes());
        }
        output.write(("\n").getBytes());

        output.write(("(3) Master Sword ($ 500)").getBytes());
        if (masterSold) {
            output.write((" (SOLD)").getBytes());
        }
        output.write(("\n").getBytes());

        output.write(("(0) Leave the shop" + "\n").getBytes());
        output.write(("You have $" + hero.getGold() + ". What would you like to buy? (1, 2, 3, 0): ").getBytes());

    }

    public static boolean[] buyItem(boolean swordSold, boolean shieldSold, boolean masterSold, int itemToBuy, Hero hero, FileOutputStream output) throws IOException {

        boolean shopLoop = true;

        switch (itemToBuy) {

            case 1:
                if (hero.getGold() >= 50) {

                    if (!swordSold) {
                        hero.addItemToBag("Sword");
                        hero.changeGold(-50, output);
                        swordSold = true;
                    } else {
                        output.write(("Already sold!" + "\n").getBytes());
                    }

                } else {
                    output.write(("Not enough money!" + "\n").getBytes());
                }
                break;

            case 2:
                if (hero.getGold() >= 75) {

                    if (!shieldSold) {
                        hero.addItemToBag("Shield");
                        hero.changeGold(-75, output);
                        shieldSold = true;
                    } else {
                        output.write(("Already sold!" + "\n").getBytes());
                    }

                } else {
                    output.write(("Not enough money!" + "\n").getBytes());
                }
                break;

            case 3:
                if (hero.getGold() >= 500) {

                    if (!masterSold) {

                        output.write(("Thank you for your purchase!" + "\n\n").getBytes());
                        hero.addItemToBag("Master");
                        hero.changeGold(-500, output);
                        return new boolean[]{false, true, true, true}; //exits secondShopLoop instantly

                    } else {
                        output.write(("Already sold!" + "\n").getBytes());
                    }

                } else {
                    output.write(("Not enough money!" + "\n").getBytes());
                }
                break;

            case 0:
                if (hero.isInBag("Sword") && hero.isInBag("Shield")) {
                    if (!(hero.getQuest() == 3)) {
                        output.write(("Your adventure begins here!" + "\n").getBytes()); //only at first time
                    }
                    shopLoop = false;
                } else {
                    output.write(("Buy items before you go!" + "\n").getBytes());
                }
                break;

            default:
                output.write(("Enter a valid number!" + "\n").getBytes());
        }

        return new boolean[]{shopLoop, swordSold, shieldSold, masterSold};
    }


}
