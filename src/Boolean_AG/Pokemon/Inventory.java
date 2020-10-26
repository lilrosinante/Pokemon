package Boolean_AG.Pokemon;

public class Inventory {

    //attributes
    private int pokeballAmount;
    private int berriesAmount;
    private int foodAmount;

    /**Methods**/
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
