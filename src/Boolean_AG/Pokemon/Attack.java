package Boolean_AG.Pokemon;

import Boolean_AG.API.Type;

public class Attack {

    //attributes
    private String name;
    private Type type;
    private boolean isPhysical;
    private int strength;

    //constructor
    public Attack(String name, Type type, boolean isPhysical, int strength) {
        this.name = name;
        this.type = type;
        this.isPhysical = isPhysical;
        this.strength = strength;
    }

    //getters & setters
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public int getStrength() {
        return strength;
    }
}
