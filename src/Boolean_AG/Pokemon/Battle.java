package Boolean_AG.Pokemon;

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
     * Methods
     **/
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

            player.setStamina(player.getStamina() - 1);

        }

    }

    private boolean playTurn(Pokemon myPokemon, Pokemon enemyPokemon, boolean battleContinue, Trainer player, int myPokemonMaxHP) throws PlayTurnInputMismatchException {

        //local variables
        Random r = new Random();
        int usedAttack = r.nextInt(1) + 1;
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
                    System.out.println("\n" + myPokemon.getName() + " used " + (attackChoice == 1 ? myPokemon.getAttack1().getName() : myPokemon.getAttack2().getName()) + " and dealt " + dmgDoneToAlly + " damage.");
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

        if (myPokemon.getHp() == 0 || enemyPokemon.getHp() == 0) {
            battleContinue = false;
        }

        return battleContinue;
    }

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

        // get damage depending on whih attack is used
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

    private int calculateDmg(Pokemon myPokemon, Pokemon enemyPokemon, int usedAttack, boolean isEnemyDmg) {

        final double dmgMultiplier = 0.44;
        double atkDefDevision;
        double modifier;
        int baseDmg;
        int dmg = 0;

        if (!isEnemyDmg) {
            if (usedAttack == 1) {
                if (myPokemon.getAttack1().isPhysical()) {
                    atkDefDevision = myPokemon.getAtk() / enemyPokemon.getDef();
                } else {
                    atkDefDevision = myPokemon.getSpAtk() / enemyPokemon.getSpDef();
                }
                baseDmg = myPokemon.getAttack1().getStrength();

                if (myPokemon.getType() == myPokemon.getAttack1().getType()) {
                    modifier = 1.5;
                } else {
                    modifier = 1;
                }
            } else {
                if (myPokemon.getAttack2().isPhysical()) {
                    atkDefDevision = myPokemon.getAtk() / enemyPokemon.getDef();
                } else {
                    atkDefDevision = myPokemon.getSpAtk() / enemyPokemon.getSpDef();
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
                    atkDefDevision = enemyPokemon.getAtk() / myPokemon.getDef();
                } else {
                    atkDefDevision = enemyPokemon.getSpAtk() / myPokemon.getSpDef();
                }
                baseDmg = enemyPokemon.getAttack1().getStrength();

                if (enemyPokemon.getType() == enemyPokemon.getAttack1().getType()) {
                    modifier = 1.5;
                } else {
                    modifier = 1;
                }
            } else {
                if (enemyPokemon.getAttack2().isPhysical()) {
                    atkDefDevision = enemyPokemon.getAtk() / myPokemon.getDef();
                } else {
                    atkDefDevision = enemyPokemon.getSpAtk() / myPokemon.getSpDef();
                }
                baseDmg = enemyPokemon.getAttack2().getStrength();

                if (enemyPokemon.getType() == enemyPokemon.getAttack2().getType()) {
                    modifier = 1.5;
                } else {
                    modifier = 1;
                }
            }
        }

        dmg = (int) Math.floor(((dmgMultiplier * atkDefDevision * baseDmg) + 2) * modifier * 0.7);

        return dmg;
    }

    private void catchPokemon(Trainer player, Pokemon enemyPokemon) {
        player.getTeam().getTeamList().add(enemyPokemon);
        enemyPokemon.setHp(0);
        System.out.println("\nCongrats! " + enemyPokemon.getName() + " has been captured and added to your team!\n");
        player.getInv().setPokeballAmount(player.getInv().getPokeballAmount() - 1);
    }

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

    private void healPokemon(Pokemon myPokemon, Trainer player, int myPokemonMaxHealth) {

        if (player.getInv().getBerriesAmount() > 0) {
            myPokemon.setHp(myPokemonMaxHealth);
            player.getInv().setBerriesAmount(player.getInv().getBerriesAmount() - 1);
        }

    }

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
    private void listTeamPokemon(Trainer player) {
        for (int i = 0; i < player.getTeam().getTeamList().size(); i++) {
            if (player.getTeam().getTeamList().get(i).getHp() > 0) {
                System.out.println((i + 1) + ". " + player.getTeam().getTeamList().get(i).getName() + "\n");
            }
        }
    }

    private Pokemon generateEnemyPokemon() {
        Random r = new Random();
        int pokemonNumber = r.nextInt(allPokemon.size());
        Pokemon enemyPokemon = new Pokemon(allPokemon.get(pokemonNumber).getId(), allPokemon.get(pokemonNumber).getName(), allPokemon.get(pokemonNumber).getType(), allPokemon.get(pokemonNumber).getHp(), allPokemon.get(pokemonNumber).getAtk(), allPokemon.get(pokemonNumber).getSpAtk(), allPokemon.get(pokemonNumber).getDef(), allPokemon.get(pokemonNumber).getSpDef(), allPokemon.get(pokemonNumber).getAttack1(), allPokemon.get(pokemonNumber).getAttack2(), allPokemon.get(pokemonNumber).isSeen(), allPokemon.get(pokemonNumber).isCatched());
        return enemyPokemon;
    }
}
