package Boolean_AG.Pokemon;

import Boolean_AG.API.Type;
import Boolean_AG.Exception.PlayTurnInputMismatchException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Battle {

    //attributes
    private ArrayList<Pokemon> allPokemon;
    private Scanner scan = new Scanner(System.in);
    private int attackChoice;

    /**
     * Battle class
     * Manages the whole battling system.
     *
     * @param player
     */
    public void battle(Trainer player) {

        //local variables
        allPokemon = player.getDex().getPokemonList();
        Pokemon enemyPokemon = generateEnemyPokemon();
        int choice = 0;
        boolean battleContinue = true;


        if (battleContinue) {
            //change attribute seen to true, when new Pokemon is encountered
            player.getDex().getPokemonList().get(enemyPokemon.getId() - 1).setSeen(true);


            //choosing own pokemon
            System.out.println("A wild " + enemyPokemon.getName() + " appeared!");
            System.out.println("Quick, choose a Pokemon to battle with!");
            System.out.println("-------------------------------------------");

            //list own pokemons
            listTeamPokemon(player);
            System.out.println("Enter number: ");
            choice = scan.nextInt();

            //choose pokemon
            Pokemon myPokemon = player.getTeam().getTeamList().get(choice - 1);
            System.out.println(myPokemon.getName() + " has been chosen!");

            //getting maxHealth information
            int enemyPokemonMaxHP = enemyPokemon.getHp();
            int myPokemonMaxHP = myPokemon.getHp();

            //battling
            do {

                try {
                    battleContinue = playTurn(myPokemon, enemyPokemon, true, player, myPokemonMaxHP);
                } catch (PlayTurnInputMismatchException e) {
                    System.out.println(e.getMessage());
                    scan.next();
                }

            } while (battleContinue);

            System.out.print((myPokemon.getHp() == 0) ? "Your " + myPokemon.getName() : enemyPokemon.getName());
            System.out.print(" has been defeated!");

            enemyPokemon.setHp(enemyPokemonMaxHP);
            myPokemon.setHp(myPokemonMaxHP);

            player.setStamina(player.getStamina() - 1);

        }

    }

    /**
     *
     * Plays one turn of the battle, where one can either attack, heal, view statistics or catch the enemy Pokemon.
     * The enemy Pokemon will also attack once.
     *
     * @param myPokemon
     * @param enemyPokemon
     * @param battleContinue
     * @param player
     * @param myPokemonMaxHP
     * @return {@link Boolean} true if battle continues
     * @throws PlayTurnInputMismatchException
     */
    private boolean playTurn(Pokemon myPokemon, Pokemon enemyPokemon, boolean battleContinue, Trainer player, int myPokemonMaxHP) throws PlayTurnInputMismatchException {

        //local variables
        Random r = new Random();
        int usedAttack = r.nextInt(2) + 1;
        int dmgDoneToEnemy;
        int dmgDoneToAlly;


        try {
            printBattleUI();
            int choice = scan.nextInt();

            switch (choice) {

                case 1:
                    //my pokemon attacking
                    dmgDoneToEnemy = attack(myPokemon, enemyPokemon);
                    if (dmgDoneToEnemy > myPokemon.getHp()) {
                        enemyPokemon.setHp(0);
                    } else {
                        enemyPokemon.setHp(enemyPokemon.getHp() - dmgDoneToEnemy);
                    }

                    //enemy pokemon attacking
                    dmgDoneToAlly = calculateDmg(myPokemon, enemyPokemon, usedAttack, true);
                    if (dmgDoneToAlly > myPokemon.getHp()) {
                        myPokemon.setHp(0);
                    } else {
                        myPokemon.setHp(myPokemon.getHp() - dmgDoneToAlly);
                    }

                    //print used attack and dealt damage
                    System.out.println("\n" + myPokemon.getName() + " used " + (attackChoice == 1 ? myPokemon.getAttack1().getName() : myPokemon.getAttack2().getName()) + " and dealt " + dmgDoneToEnemy + " damage.");
                    System.out.println("\n" + enemyPokemon.getName() + " used " + (usedAttack == 1 ? enemyPokemon.getAttack1().getName() : enemyPokemon.getAttack2().getName()) + " and dealt " + dmgDoneToAlly + " damage.\n");
                    break;

                case 2:
                    healPokemon(myPokemon, player, myPokemonMaxHP);
                    break;

                case 3:
                    viewStats(myPokemon, enemyPokemon);
                    break;

                case 4:
                    catchPokemon(player, enemyPokemon);
                    break;

                default:
                    System.out.println("Invalid Input, please try again");
                    break;
            }
        } catch (InputMismatchException e) {
            throw new PlayTurnInputMismatchException("Invalid input, please type a number.");
        }

        if (myPokemon.getHp() <= 0 || enemyPokemon.getHp() <= 0) {
            battleContinue = false;
        }

        return battleContinue;
    }

    /**
     * Asks user which attack they want to use. Prints data about which attack has been used and how much damage
     * has been dealt by allied and enemy Pokemo, using "calculateDmg"-method.
     *
     * @param myPokemon
     * @param enemyPokemon
     * @return {@link Integer}damage (dmg) done by the chosen attack of the Pokemon.
     */
    private int attack(Pokemon myPokemon, Pokemon enemyPokemon) {

        int dmg = 0;

        System.out.println("\nWhich Attack are you going to use?");
        System.out.println("----------------------------------------");

        // Print out attack details
        System.out.println("Attack 1: " + myPokemon.getAttack1().getName() + " | Strength: "
                + myPokemon.getAttack1().getStrength() + " | Type: " + myPokemon.getAttack1().getType().toString());
        System.out.println("----------------------------------------");
        System.out.println("Attack 2: " + myPokemon.getAttack2().getName() + " | Strength: "
                + myPokemon.getAttack2().getStrength() + " | Type: " + myPokemon.getAttack2().getType().toString() + "\n");

        // get damage depending on which attack is used
        System.out.println("\nEnter attack number: ");
        attackChoice = scan.nextInt();

        if (attackChoice == 1) {
            System.out.println(myPokemon.getName() + ", used " + myPokemon.getAttack1().getName());
            dmg = calculateDmg(myPokemon, enemyPokemon, 1, false);
        } else if (attackChoice == 2) {
            System.out.println(myPokemon.getName() + ", used " + myPokemon.getAttack2().getName());
            dmg = calculateDmg(myPokemon, enemyPokemon, 2, false);
        } else {
            System.out.println("Invalid input.");
        }

        return dmg;
    }

    /**
     *
     * calculates the amount of dmg the Pokemon deals to the enemy depending on certain factors.
     * It can be used for enemy dmg and for allied dmg.
     *
     * @param myPokemon
     * @param enemyPokemon
     * @param usedAttack
     * @param isEnemyDmg
     * @return {@link Integer}dmg variable
     */
    private int calculateDmg(Pokemon myPokemon, Pokemon enemyPokemon, int usedAttack, boolean isEnemyDmg) {

        final double dmgMultiplier = 0.44;
        double atkDefDevision;
        double modifier;
        int baseDmg;
        int dmg = 0;
        double typeInteractionDmg = calculateTypeInteractions(myPokemon, enemyPokemon, usedAttack, isEnemyDmg);

        if (!isEnemyDmg) {
            if (usedAttack == 1) {
                if (myPokemon.getAttack1().isPhysical()) {
                    atkDefDevision = (double) myPokemon.getAtk() / (double) enemyPokemon.getDef();
                } else {
                    atkDefDevision = (double) myPokemon.getSpAtk() / (double) enemyPokemon.getSpDef();
                }
                baseDmg = myPokemon.getAttack1().getStrength();

                if (myPokemon.getType() == myPokemon.getAttack1().getType()) {
                    modifier = 1.5;
                } else {
                    modifier = 1;
                }
            } else {
                if (myPokemon.getAttack2().isPhysical()) {
                    atkDefDevision = (double) myPokemon.getAtk() / (double) enemyPokemon.getDef();
                } else {
                    atkDefDevision = (double) myPokemon.getSpAtk() / (double) enemyPokemon.getSpDef();
                }
                baseDmg = myPokemon.getAttack2().getStrength();

                if (myPokemon.getType() == myPokemon.getAttack2().getType()) {
                    modifier = 1.5;
                } else {
                    modifier = 1;
                }
            }
        } else {
            if (usedAttack == 1) {
                if (enemyPokemon.getAttack1().isPhysical()) {
                    atkDefDevision = (double) enemyPokemon.getAtk() / (double) myPokemon.getDef();
                } else {
                    atkDefDevision = (double) enemyPokemon.getSpAtk() / (double) myPokemon.getSpDef();
                }
                baseDmg = enemyPokemon.getAttack1().getStrength();

                if (enemyPokemon.getType() == enemyPokemon.getAttack1().getType()) {
                    modifier = 1.5;
                } else {
                    modifier = 1;
                }
            } else {
                if (enemyPokemon.getAttack2().isPhysical()) {
                    atkDefDevision = (double) enemyPokemon.getAtk() / (double) myPokemon.getDef();
                } else {
                    atkDefDevision = (double) enemyPokemon.getSpAtk() / (double) myPokemon.getSpDef();
                }
                baseDmg = enemyPokemon.getAttack2().getStrength();

                if (enemyPokemon.getType() == enemyPokemon.getAttack2().getType()) {
                    modifier = 1.5;
                } else {
                    modifier = 1;
                }
            }
        }

        dmg = (int) Math.floor(((dmgMultiplier * atkDefDevision * baseDmg) + 2) * (modifier * typeInteractionDmg) * 0.7);

        return dmg;
    }

    /**
     * Calculates multiplier of type interactions (resistances and very effective attacks).
     * 0.5, 1.0 or 2.
     * Fire strong against Grass
     * Grass strong against Water
     * Water strong against Fire
     *
     * @param myPokemon
     * @param enemyPokemon
     * @param usedAttack
     * @param isEnemyDmg
     * @return {@link Double} value for type interaction.
     */
    private double calculateTypeInteractions(Pokemon myPokemon, Pokemon enemyPokemon, int usedAttack, Boolean isEnemyDmg) {

        double damageMultiplier = 1;
        Pokemon attacker;
        Attack usedAttackAsObj;
        Pokemon defender;

        if (!isEnemyDmg) {
            attacker = myPokemon;
        } else {
            attacker = enemyPokemon;
        }

        if (attacker == myPokemon) {
            defender = enemyPokemon;
        } else {
            defender = myPokemon;
        }

        if (usedAttack == 1) {
            usedAttackAsObj = attacker.getAttack1();
        } else {
            usedAttackAsObj = attacker.getAttack2();
        }

        if (defender.getType() == Type.NORMAL) {
            damageMultiplier = 1;
        }
        switch (usedAttackAsObj.getType()) {
            case FIRE:
                switch (defender.getType()) {
                    case FIRE:
                        damageMultiplier = 0.5;
                        break;
                    case WATER:
                        damageMultiplier = 0.5;
                        break;
                    case GRASS:
                        damageMultiplier = 2;
                        break;
                }
                break;
            case WATER:
                switch (defender.getType()) {
                    case FIRE:
                        damageMultiplier = 2;
                        break;
                    case WATER:
                        damageMultiplier = 0.5;
                        break;
                    case GRASS:
                        damageMultiplier = 0.5;
                        break;
                }
                break;
            case GRASS:
                switch (enemyPokemon.getType()) {
                    case FIRE:
                        damageMultiplier = 0.5;
                        break;
                    case WATER:
                        damageMultiplier = 2;
                        break;
                    case GRASS:
                        damageMultiplier = 0.5;
                        break;
                }
                break;
            case NORMAL:
                damageMultiplier = 1;
                break;
        }
        return damageMultiplier;
    }

    /**
     * Adds enemy Pokemon to Team list.
     * Sets "Catched" attribute in Pokedex to true.
     * "kills" enemy Pokemon in order to stop the on going Battle.
     *
     * @param player
     * @param enemyPokemon
     */
    private void catchPokemon(Trainer player, Pokemon enemyPokemon) {
        player.getTeam().getTeamList().add(enemyPokemon);
        player.getDex().getPokemonList().get(enemyPokemon.getId() - 1).setCatched(true);
        enemyPokemon.setHp(0);
        System.out.println("\nCongrats! " + enemyPokemon.getName() + " has been captured and added to your team!\n");
        player.getInv().setPokeballAmount(player.getInv().getPokeballAmount() - 1);
    }

    /**
     * Displays all the stats of the allied and the enemy Pokemon.
     * Doesn't affect the current turn.
     *
     * @param myPokemon
     * @param enemyPokemon
     */
    private void viewStats(Pokemon myPokemon, Pokemon enemyPokemon) {
        System.out.println("________________________________________________________________" +
                "\n| Ally Pokemon stats:" +
                "\n| Pokemon: " + myPokemon.getName() +
                "\n| Type: " + myPokemon.getType().toString() +
                "\n| Hp: " + myPokemon.getHp() +
                "\n| Atk: " + myPokemon.getAtk() +
                "\n| Sp. Atk: " + myPokemon.getSpAtk() +
                "\n| Def: " + myPokemon.getDef() +
                "\n| Sp. Def: " + myPokemon.getSpDef() +
                "\n| Primary Attack: " + myPokemon.getAttack1().getName() +
                "\n| Primary Attack strength: " + myPokemon.getAttack1().getStrength() +
                "\n| Primary Attack type: " + myPokemon.getAttack1().getType().toString() +
                "\n| Secondary Attack: " + myPokemon.getAttack2().getName() +
                "\n| Secondary Attack strength: " + myPokemon.getAttack2().getStrength() +
                "\n| Secondary Attack type: " + myPokemon.getAttack2().getType().toString() +
                "\n|_______________________________________________________________" +
                "\n| Enemy Pokemon stats: " +
                "\n| Pokemon: " + enemyPokemon.getName() +
                "\n| Type: " + enemyPokemon.getType().toString() +
                "\n| Hp: " + enemyPokemon.getHp() +
                "\n| Atk: " + enemyPokemon.getAtk() +
                "\n| Sp. Atk: " + enemyPokemon.getSpAtk() +
                "\n| Def: " + enemyPokemon.getDef() +
                "\n| Sp. Def: " + enemyPokemon.getSpDef() +
                "\n| Primary Attack: " + enemyPokemon.getAttack1().getName() +
                "\n| Primary Attack strength: " + enemyPokemon.getAttack1().getStrength() +
                "\n| Primary Attack type: " + enemyPokemon.getAttack1().getType().toString() +
                "\n| Secondary Attack: " + enemyPokemon.getAttack2().getName() +
                "\n| Secondary Attack strength: " + enemyPokemon.getAttack2().getStrength() +
                "\n| Secondary Attack type: " + enemyPokemon.getAttack2().getType().toString() +
                "\n________________________________________________________________");
    }

    /**
     * Heals allied Pokemon to its Maximum HP (Health Points).
     * Subtracts 1 berry from Trainer's inventory.
     *
     * @param myPokemon
     * @param player
     * @param myPokemonMaxHealth
     */
    private void healPokemon(Pokemon myPokemon, Trainer player, int myPokemonMaxHealth) {

        if (player.getInv().getBerriesAmount() > 0) {
            myPokemon.setHp(myPokemonMaxHealth);
            player.getInv().setBerriesAmount(player.getInv().getBerriesAmount() - 1);
        }

        System.out.println("\nYour " + myPokemon.getName() + " has been fully healed.\n");

    }

    /**
     * Prints User Interface for battle, displaying all options.
     */
    private void printBattleUI() {

        System.out.println("________________________________");
        System.out.println("|                              |");
        System.out.println("| Attack          [1]          |");
        System.out.println("|                              |");
        System.out.println("| Heal Pokemon    [2]          |");
        System.out.println("|                              |");
        System.out.println("| View Stats      [3]          |");
        System.out.println("|                              |");
        System.out.println("| Capture Pokemon [4]          |");
        System.out.println("|______________________________|");
        System.out.println("What would you like to do?: ");

    }

    // Listing all Pokemon in the team

    /**
     * Shows the trainer all available Pokemon to battle with.
     * @param player
     */
    private void listTeamPokemon(Trainer player) {
        for (int i = 0; i < player.getTeam().getTeamList().size(); i++) {
            if (player.getTeam().getTeamList().get(i).getHp() > 0) {
                System.out.println((i + 1) + ". " + player.getTeam().getTeamList().get(i).getName() + "\n");
            }
        }
    }

    /**
     * Generates a random enemy Pokemon.
     *
     * @return {@link Pokemon}
     */
    private Pokemon generateEnemyPokemon() {
        Random r = new Random();
        int pokemonNumber = r.nextInt(allPokemon.size());
        Pokemon enemyPokemon = new Pokemon(allPokemon.get(pokemonNumber).getId(),
                allPokemon.get(pokemonNumber).getName(),
                allPokemon.get(pokemonNumber).getType(),
                allPokemon.get(pokemonNumber).getHp(),
                allPokemon.get(pokemonNumber).getAtk(),
                allPokemon.get(pokemonNumber).getSpAtk(),
                allPokemon.get(pokemonNumber).getDef(),
                allPokemon.get(pokemonNumber).getSpDef(),
                allPokemon.get(pokemonNumber).getAttack1(),
                allPokemon.get(pokemonNumber).getAttack2(),
                allPokemon.get(pokemonNumber).isSeen(),
                allPokemon.get(pokemonNumber).isCatched());
        return enemyPokemon;
    }
}
