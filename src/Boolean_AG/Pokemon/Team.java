package Boolean_AG.Pokemon;

import java.util.ArrayList;

public class Team {

    //attributes
    private ArrayList<Pokemon> myPokemon = new ArrayList<Pokemon>();

    /**Methods**/
    public void viewTeam() {
        System.out.println("______________________________");
        System.out.println("Your Pokemon: ");
        myPokemon.stream().forEach(currentPokemon -> {
            System.out.println("Name: " + currentPokemon.getName() + " - " +" Type: " + currentPokemon.getType());
        });
    }

    //getters & setters
    public ArrayList<Pokemon> getTeamList() {
        return myPokemon;
    }

    public void addPokemon(Pokemon pokemon) {
        myPokemon.add(pokemon);
    }

    public ArrayList<Pokemon> getMyPokemon() { return myPokemon; }
}
