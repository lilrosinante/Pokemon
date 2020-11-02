package Boolean_AG.Pokemon;

import java.util.Scanner;

/**
 * Class with attributes, methods, constructor, getters and setters for {@link Inventory}-POJO.
 */
public class Trainer {

    //attributes
    private Scanner scan = new Scanner(System.in);
    private String username;
    private int level = 1;
    private int stamina = 100;
    private Team team;
    private Inventory inv;
    private Pokedex dex;

    //constructor
    public Trainer() {

        inv = new Inventory();
        dex = new Pokedex();
        team = new Team();

        inv.setPokeballAmount(100);
        inv.setFoodAmount(10);
        inv.setBerriesAmount(20);

        System.out.println("Hello trainer. Welcome to the Pokemon Game from Boolean AG");
        System.out.println("----------------------------------------------------------");
        System.out.print("\nEnter your username: ");
        username = scan.next();

        System.out.println("Hey " + username + ", what a great username!");
        chooseStarter(dex, team);

    }

    /**
     * Lets the Trainer choose his first Pokemon.
     * The player can't play the game without at least 1 Pokemon.
     *
     * @param dex
     * @param team
     */
    private void chooseStarter(Pokedex dex, Team team) {

        int pokemonNumber = 0;

        System.out.println("Choose your starter pokemon:\n" +
                           "1. Bisasam\n" +
                           "2. Glumanda\n" +
                           "3. Shiggy\n" +
                           "Enter number of pokemon you'd like to have as a starter");
        int choice = scan.nextInt();

        if (choice > 0 && choice < 4) {
            if (choice == 1) {
                //7 is index of the pokemon Bisasam in the pokemonlist of Pokedex
                pokemonNumber = 7;
            } else if (choice == 2) {
                //1 is index of Glumanda in the pokemonlist of Pokedex
                pokemonNumber = 1;
            } else if (choice == 3) {
                //4 is index of Shiggy in the pokemonlist of Pokedex
                pokemonNumber = 4;
            }

            pokemonNumber--;

            team.addPokemon(new Pokemon(
                    dex.getPokemonList().get(pokemonNumber).getId(), dex.getPokemonList().get(pokemonNumber).getName(),
                    dex.getPokemonList().get(pokemonNumber).getType(), dex.getPokemonList().get(pokemonNumber).getHp(),
                    dex.getPokemonList().get(pokemonNumber).getAtk(), dex.getPokemonList().get(pokemonNumber).getSpAtk(),
                    dex.getPokemonList().get(pokemonNumber).getDef(), dex.getPokemonList().get(pokemonNumber).getSpDef(),
                    dex.getPokemonList().get(pokemonNumber).getAttack1(), dex.getPokemonList().get(pokemonNumber).getAttack2(),
                    true, true));

            dex.getPokemonList().get(pokemonNumber).setSeen(true);
            dex.getPokemonList().get(pokemonNumber).setCatched(true);
        } else {
            System.out.println("Something went wrong with the input.\nPlease choose a Number between 1 and 3");
        }
    }

    /**
     * Displays all necessary information (Attributes) about the trainer.
     */
    public void viewTrainerStats(){
        System.out.println("\nTrainer Informations\n" +
                           "-------------------------\n" +
                           "Username: " + username +
                           "\nLevel: " + level +
                           "\nStamina: " + stamina +
                           "\nTeam size: " + team.getMyPokemon().size());
    }

    /**
     * Lets the user change his username.
     *
     * @param newUsername
     */
    public void changeUsername(String newUsername) {

        if (!newUsername.equalsIgnoreCase(getUsername())) {
            setUsername(newUsername);
        } else {
            System.out.println("Nothing has been changed. New username is equal to the old one.");
        }
    }

    /**
     * Fills up the Trainers stamina.
     * The Trainer needs stamina to be able to battle.
     */
    public void eatFood() {

        if(stamina == 100){

            System.out.println("Your stamina is full.");

        } else if (inv.getFoodAmount() > 0) {

            System.out.println("Your stamina has been refilled.");
            inv.setFoodAmount(inv.getFoodAmount() - 1);
            stamina = 100;

        } else {
            System.out.println("You're out of food.");
        }

    }

    //getters & setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public Inventory getInv() {
        return inv;
    }

    public Pokedex getDex() {
        return dex;
    }

    public Team getTeam() {
        return team;
    }

}
