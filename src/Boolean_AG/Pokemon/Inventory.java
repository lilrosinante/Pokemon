package Boolean_AG.Pokemon;

/**
 * Class with attributes, constructor, getters and setters for {@link Inventory}-POJO.
 */
public class Inventory {

    //attributes
    private int pokeballAmount;
    private int berriesAmount;
    private int foodAmount;

    /**
     * Shows all items which the trainer possesses and their amount.
     */
    public void viewInventory() {
        System.out.println("\nYour Inventory\n" +
                "-----------------------------\n" +
                "Pokéballs: " + getPokeballAmount() + "\n" +
                "Beeries: " + getBerriesAmount() + "\n" +
                "Food: " + getFoodAmount());
    }

    //getters & setters
    public void setPokeballAmount(int pokeballAmount) { this.pokeballAmount = pokeballAmount; }

    public void setBerriesAmount(int berriesAmount) {
        this.berriesAmount = berriesAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public int getPokeballAmount() {
        return pokeballAmount;
    }

    public int getBerriesAmount() {
        return berriesAmount;
    }

    public int getFoodAmount() {
        return foodAmount;
    }
}
