package Boolean_AG;

import java.util.Scanner;

public class Trainer {

    //attributes
    Scanner scan = new Scanner(System.in);
    private String username;
    private int level = 1;
    private int stamina = 100;
    private Team team;
    private Inventory inv;
    private Pokedex dex;

    public void Trainer(){

        inv = new Inventory();
        dex = new Pokedex();

        inv.setPokeballAmount(100);
        inv.setFoodAmount(10);
        inv.setBerriesAmount(20);

        System.out.println("Hello trainer. Welcome to the Pokémon Game from Boolean AG");
        System.out.println("----------------------------------------------------------");
        System.out.print("\nEnter your username: ");
        username = scan.next();

        System.out.println("Hey " + username + ", what a great username!");
        System.out.println("Choose your starter pokémon: " +
                           "1. Bisasam");

    }

    public void changeUsername(){
        System.out.println("Old username: " + username);
        System.out.print("New username: ");
        String newUsername = scan.next();
        if(!newUsername.equalsIgnoreCase(getUsername())){
            setUsername(newUsername);
        } else{
            System.out.println("Nothing has been changed. New username is equal to the old one.");
        }
    }

    public void eatFood(){

        if(inv.getFoodAmount() > 0){

            System.out.println("Your stamina has been refilled.");
            inv.setFoodAmount(inv.getFoodAmount()-1);
            stamina = 100;

        } else{
            System.out.println("You're out of food.");
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public Pokedex getDex() { return dex; }

    public void setDex(Pokedex dex) { this.dex = dex; }

    public Team getTeam() { return team; }

    public void setTeam(Team team) { this.team = team; }
}
