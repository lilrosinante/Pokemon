package Boolean_AG;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public void setSpAtk(int spAtk) {
        this.spAtk = spAtk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpDef() {
        return spDef;
    }

    public void setSpDef(int spDef) {
        this.spDef = spDef;
    }

    public Attack getAttack1() {
        return attack1;
    }

    public void setAttack1(Attack attack1) {
        this.attack1 = attack1;
    }

    public Attack getAttack2() {
        return attack2;
    }

    public void setAttack2(Attack attack2) {
        this.attack2 = attack2;
    }

    public boolean isSeen() { return seen; }

    public void setSeen(boolean seen) { this.seen = seen; }

    public boolean isCatched() { return catched; }

    public void setCatched(boolean catched) { this.catched = catched; }

}

