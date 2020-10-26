package Boolean_AG;

public class Attack {

    private String name;
    private Type type;
    private boolean isPhysical;
    private int strength;

    public Attack(String name, Type type, boolean isPhysical, int strength) {
        this.name = name;
        this.type = type;
        this.isPhysical = isPhysical;
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public void setPhysical(boolean physical) {
        isPhysical = physical;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
