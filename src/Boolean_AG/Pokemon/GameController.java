package Boolean_AG.Pokemon;

import java.util.Scanner;

public class GameController {

    //attributes
    private Trainer player = new Trainer();
    private Battle battle = new Battle();

    /**
     * Starts the whole Game.
     * Calls methods depending on user input.
     */
    public void start(){

        Scanner scan = new Scanner(System.in);
        String decision;

        do{
            printUI();
            System.out.print(player.getUsername() + " what would you like to do?: ");
            decision = scan.next();

            switch(decision){

                case "T":
                    player.viewTrainerStats();
                    break;

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
                    if(player.getStamina() > 0) {
                        battle.battle(player);
                    } else {
                        System.out.println("You need more stamina to fight another battle.\nEat some food to gain stamina.");
                    }
                    break;

                case "E":
                    player.eatFood();
                    break;

                case "C":
                    System.out.println("Old username: " + player.getUsername());
                    System.out.print("New username: ");
                    String newUsername = scan.next();
                    player.changeUsername(newUsername);
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

    /**
     * Prints interface for User with all possible options.
     */
    public void printUI(){
        System.out.println("\n----------------------\n" +
                           "| T | Trainer Stats  |\n" +
                           "| V | View Pokémons  |\n" +
                           "| P | Open Pokédex   |\n" +
                           "| I | Open Inventory |\n" +
                           "| B | Battle         |\n" +
                           "| E | Eat Food       |\n" +
                           "| C | Change Username|\n" +
                           "| Q | Quit           |\n" +
                           "----------------------");
    }

}
