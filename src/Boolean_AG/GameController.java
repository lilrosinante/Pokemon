package Boolean_AG;

import java.util.Scanner;

public class GameController {

    public void start(){

        Trainer player = new Trainer();
        Battle battle = new Battle();
        Scanner scan = new Scanner(System.in);
        String decision = null;

        do{
            printUI();
            System.out.print("What would you like to do?: ");
            decision = scan.next();

            switch(decision){
                case "V":
                    player.getTeam().viewTeam();
                    break;

                case "P":
                    player.getDex().openPokedex();
                    break;

                case "I":
                    player.getInv().viewInventory();
                    break;

                case "B":
                    if(player.getInv().getFoodAmount() > 0) {
                        battle.battle(player);
                    } else {
                        System.out.println("You need more stamina to fight another battle.\nEat some food to gain stamina.");
                    }
                    break;

                case "E":
                    player.eatFood();
                    break;

                case "C":
                    player.changeUsername();
                    break;

                case "Q":

                    System.out.println("Thanks for playing. :)");

                    break;

                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }
        } while(!decision.equalsIgnoreCase("Q"));

    }

    public void printUI(){
        System.out.println("----------------------\n" +
                           "| V | View Pokémons  |\n" +
                           "| P | Open Pokédex   |\n" +
                           "| I | Open Inventar  |\n" +
                           "| B | Battle         |\n" +
                           "| E | Eat Food       |\n" +
                           "| C | Change Username|\n" +
                           "| Q | Quit           |\n" +
                           "----------------------");
    }

}
