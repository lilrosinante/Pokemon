package Boolean_AG.Pokemon;

import Boolean_AG.API.Type;

import java.util.ArrayList;

/**
 * Class with attributes, constructor, getters and setters for {@link Pokedex}-POJO.
 */
public class Pokedex {

    //attributes
    private ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
    private ArrayList<Attack> attackList = new ArrayList<Attack>();

    /**
     * Creates all available Attacks and Pokemon and adds them to their corresponding ArrayList.
     */
    public Pokedex() {

        //add all attacks to the game
        attackList.add(new Attack("Leaf Storm", Type.GRASS, false, 140));
        attackList.add(new Attack("Fire Storm", Type.FIRE, false, 120));
        attackList.add(new Attack("Hydro Pump", Type.WATER, false, 110));
        attackList.add(new Attack("Hyper Beam", Type.NORMAL, false, 150));
        attackList.add(new Attack("Leaf Sword", Type.GRASS, true, 80));
        attackList.add(new Attack("Scald", Type.WATER, false, 80));
        attackList.add(new Attack("Flame Thrower", Type.FIRE, false, 80));
        attackList.add(new Attack("Waterfall", Type.WATER, true, 80));
        attackList.add(new Attack("Fire Punch", Type.FIRE, true, 80));
        attackList.add(new Attack("Hyper Tooth", Type.NORMAL, true, 90));
        attackList.add(new Attack("Bodycheck", Type.NORMAL, true, 95));
        attackList.add(new Attack("Tackle", Type.NORMAL, true, 50));
        attackList.add(new Attack("Ember", Type.FIRE, false, 40));
        attackList.add(new Attack("Water Gun", Type.WATER, false, 40));
        attackList.add(new Attack("Vine Whip", Type.GRASS, true, 40));
        attackList.add(new Attack("Giga Drain", Type.GRASS, false, 90));
        attackList.add(new Attack("Interstellar Energybeam", Type.NORMAL, false, 250));

        //add all pokemons to the game
        pokemonList.add(new Pokemon(1, "Glumanda", Type.FIRE, 35, 20, 16, 19, 13, attackList.get(12), attackList.get(11), false, false));
        pokemonList.add(new Pokemon(2, "Glutexo", Type.FIRE, 80, 40, 20, 20, 15, attackList.get(6), attackList.get(10), false, false));
        pokemonList.add(new Pokemon(3, "Glurak", Type.FIRE, 140, 60, 30, 40, 40, attackList.get(1), attackList.get(8), false, false));
        pokemonList.add(new Pokemon(4, "Schiggi", Type.WATER, 35, 15, 15, 25, 15, attackList.get(13), attackList.get(11), false, false));
        pokemonList.add(new Pokemon(5, "Schillock", Type.WATER, 80, 30, 16, 30, 30, attackList.get(7), attackList.get(10), false, false));
        pokemonList.add(new Pokemon(6, "Turktok", Type.WATER, 140, 45, 30, 55, 60, attackList.get(2), attackList.get(5), false, false));
        pokemonList.add(new Pokemon(7, "Bisasam", Type.GRASS, 35, 20, 16, 19, 13, attackList.get(14), attackList.get(11), false, false));
        pokemonList.add(new Pokemon(8, "Bisaknosp", Type.GRASS, 80, 30, 18, 20, 25, attackList.get(4), attackList.get(10), false, false));
        pokemonList.add(new Pokemon(9, "Bisaflor", Type.GRASS, 140, 40, 45, 40, 50, attackList.get(0), attackList.get(15), false, false));
        pokemonList.add(new Pokemon(10, "Ponita", Type.FIRE, 45, 20, 20, 15, 15, attackList.get(12), attackList.get(11), false, false));
        pokemonList.add(new Pokemon(11, "Gallopa", Type.FIRE, 100, 50, 25, 40, 30, attackList.get(10), attackList.get(8), false, false));
        pokemonList.add(new Pokemon(12, "Owei", Type.GRASS, 40, 15, 10, 20, 15, attackList.get(14), attackList.get(11), false, false));
        pokemonList.add(new Pokemon(13, "Kokowei", Type.GRASS, 120, 60, 30, 50, 30, attackList.get(4), attackList.get(15), false, false));
        pokemonList.add(new Pokemon(14, "Lavados", Type.FIRE, 200, 50, 100, 50, 45, attackList.get(1), attackList.get(3), false, false));
        pokemonList.add(new Pokemon(15, "Arktos", Type.WATER, 200, 40, 80, 60, 75, attackList.get(2), attackList.get(3), false, false));
        pokemonList.add(new Pokemon(16, "Taubsi", Type.NORMAL, 15, 10, 10, 10, 10, attackList.get(11), attackList.get(12), false, false));
        pokemonList.add(new Pokemon(17, "Tauboga", Type.NORMAL, 50, 40, 20, 30, 40, attackList.get(10), attackList.get(6), false, false));
        pokemonList.add(new Pokemon(18, "Tauboss", Type.NORMAL, 150, 60, 25, 50, 60, attackList.get(9), attackList.get(3), false, false));
        pokemonList.add(new Pokemon(19, "Rattfratz", Type.NORMAL, 30, 10, 20, 10, 10, attackList.get(11), attackList.get(13), false, false));
        pokemonList.add(new Pokemon(20, "Rattikarl", Type.NORMAL, 100, 60, 30, 40, 50, attackList.get(9), attackList.get(5), false, false));
        pokemonList.add(new Pokemon(21, "Arceus", Type.NORMAL, 250, 80, 145, 110, 125, attackList.get(16), attackList.get(2), false, false));
    }

    /**
     * Displays all Pokemon which the trainer has encountered so far.
     */
    public void openPokedex() {
        System.out.println("\nYour Pokedex\n" +
                "-----------------------------\n" +
                "Name, Type, Seen, Catched");
        for (int i = 0; i < pokemonList.size(); i++) {

            if (pokemonList.get(i).isSeen()) {
                System.out.println(pokemonList.get(i).getId() + ". " + pokemonList.get(i).getName() + " - " + pokemonList.get(i).getType() + " - " + pokemonList.get(i).isSeen() + " - " + pokemonList.get(i).isCatched());
            }
        }
    }

    /**
     *
     * @return {@link ArrayList} of type {@link Pokemon}
     */
    public ArrayList<Pokemon> getPokemonList() {
        return pokemonList;
    }

}
