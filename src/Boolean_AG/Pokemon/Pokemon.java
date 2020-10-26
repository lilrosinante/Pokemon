package Boolean_AG.Pokemon;

import Boolean_AG.API.Type;

public class Pokemon {

    //attributes
    private int id;
    private String name;
    private Type type;
    private int hp;
    private int atk;
    private int spAtk;
    private int def;
    private int spDef;
    private Attack attack1;
    private Attack attack2;
    private boolean seen;
    private boolean catched;

    //constructor
    public Pokemon(int id, String name, Type type, int hp, int atk, int spAtk, int def, int spDef, Attack attack1, Attack attack2, boolean seen, boolean catched) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.atk = atk;
        this.spAtk = spAtk;
        this.def = def;
        this.spDef = spDef;
        this.attack1 = attack1;
        this.attack2 = attack2;
        this.seen = seen;
        this.catched = catched;
    }

    //getters & setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getHp() { return hp; }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public int getDef() {
        return def;
    }

    public int getSpDef() {
        return spDef;
    }

    public Attack getAttack1() {
        return attack1;
    }

    public Attack getAttack2() {
        return attack2;
    }

    public boolean isSeen() { return seen; }

    public void setSeen(boolean seen) { this.seen = seen; }

    public boolean isCatched() { return catched; }

    public void setCatched(boolean catched) { this.catched = catched; }

}

