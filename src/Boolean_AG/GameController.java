package Boolean_AG;

public class GameController {

    public void start(){

        Trainer player = new Trainer();
        String decision = null;

        while(!decision.equalsIgnoreCase("Q")){
            printUI();

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
        }

    }

    public void printUI(){
        System.out.println("----------------------" +
                           "| V | View Pokémons  |" +
                           "| P | Open Pokédex   |" +
                           "| I | Open Inventar  |" +
                           "| B | Battle         |" +
                           "| E | Eat Food       |" +
                           "| C | Change Username|" +
                           "| Q | Quit           |" +
                           "----------------------");
    }

}
