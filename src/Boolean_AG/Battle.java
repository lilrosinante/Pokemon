package Boolean_AG;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battle {

        ArrayList<Pokemon> allPokemon;
        Scanner scan = new Scanner(System.in);

        public void battle(Trainer player) {
            boolean battleWon = false; // checks if battle won
            allPokemon = player.getDex().getPokemonList();
            Pokemon enemyPokemon = generateEnemyPokemon();

            // Change attribute seen to true, when new Pokemon is encountered
            player.getDex().getPokemonList().get(enemyPokemon.getId() + 1).setSeen(true);

            System.out.println("A wild " + enemyPokemon.getName() + " appeared!");
            System.out.println("Quick, choose a Pokemon to battle with!");
            System.out.println("-------------------------------------------");

            listTeamPokemon(player);

            // Choosing own pokemon
            int choice = scan.nextInt();
            Pokemon myPokemon = player.getTeam().getTeamList().get(choice - 1);
            System.out.println(myPokemon.getName() + " has been chosen!");
            boolean battleContinue = true;

            // Getting maxHealth information
            int myPokemonMaxHP = myPokemon.getHp();

            // Battling
            do {
                battleContinue = playTurn(myPokemon, enemyPokemon, battleContinue, player, myPokemonMaxHP);
            } while (battleContinue);

            System.out.print((myPokemon.getHp() == 0) ? "Your " + myPokemon.getName() : enemyPokemon.getName());
            System.out.print(" has been defeated!");

            myPokemon.setHp(myPokemonMaxHP);
            enemyPokemon = null;

            player.setStamina(player.getStamina() - 1);
        }

        private boolean playTurn(Pokemon myPokemon, Pokemon enemyPokemon, boolean battleContinue, Trainer player, int myPokemonMaxHP) {

            Random r = new Random();
            int usedAttack = r.nextInt(1) + 1;
            printBattleUI();
            int choice = scan.nextInt();
            int dmgDoneToEnemy;
            int dmgDoneToAlly;
            do {
                switch (choice) {

                    case 1:
                        dmgDoneToEnemy = attack(myPokemon, enemyPokemon);
                        enemyPokemon.setHp(enemyPokemon.getHp() - dmgDoneToEnemy);
                        dmgDoneToAlly = calculateDmg(myPokemon, enemyPokemon, usedAttack, true);
                        myPokemon.setHp(myPokemon.getHp() - dmgDoneToAlly);
                        System.out.println("\n" + enemyPokemon.getName() + " dealt " + dmgDoneToAlly + " damage.");
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
            } while (choice < 1 || choice > 4);

            if(myPokemon.getHp() == 0 || enemyPokemon.getHp() == 0) {
                battleContinue = false;
            }

            return battleContinue;
        }

        private int attack(Pokemon myPokemon, Pokemon enemyPokemon) {

            System.out.println("\nWhich Attack are you going to use?");
            System.out.println("----------------------------------------");

            // Print out attack details
            System.out.println("Attack 1: " + myPokemon.getAttack1().getName() + " | Strength: "
                    + myPokemon.getAttack1().getStrength() + " | Type: " + myPokemon.getAttack1().getType().toString());
            System.out.println("----------------------------------------");
            System.out.println("Attack 2: " + myPokemon.getAttack2().getName() + " | Strength: "
                    + myPokemon.getAttack2().getStrength() + " | Type: " + myPokemon.getAttack2().getType().toString() + "\n");

            // get damage depending on whih attack is used
            int choice = scan.nextInt();
            int dmg = 0;
            if (choice == 1) {
                System.out.println(myPokemon.getName() + ", use " + myPokemon.getAttack1().getName());
                dmg = calculateDmg(myPokemon, enemyPokemon, 1, false);
            } else if (choice == 2) {
                System.out.println(myPokemon.getName() + ", use " + myPokemon.getAttack2().getName());
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

            if (isEnemyDmg) {
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

            dmg = (int) Math.floor(((dmgMultiplier * atkDefDevision * baseDmg) + 2) * modifier);

            return dmg;
        }

        private void catchPokemon(Trainer player, Pokemon enemyPokemon) {
            player.getTeam().getTeamList().add(enemyPokemon);
            enemyPokemon.setHp(0);
            System.out.println("\nCongrats! " + enemyPokemon.getName() + " has been captured and added to your team!\n");
        }

        private void viewStats(Pokemon myPokemon, Pokemon enemyPokemon) {
            System.out.println("________________________________________________________________");
            System.out.println("| Ally Pokemon stats:");
            System.out.println("| Pokemon: " + myPokemon.getName());
            System.out.println("| Type: " + myPokemon.getType().toString());
            System.out.println("| Hp: " + myPokemon.getHp());
            System.out.println("| Atk: " + myPokemon.getAtk());
            System.out.println("| Sp. Atk: " + myPokemon.getSpAtk());
            System.out.println("| Def: " + myPokemon.getDef());
            System.out.println("| Sp. Def: " + myPokemon.getSpDef());
            System.out.println("| Primary Attack: " + myPokemon.getAttack1().getName());
            System.out.println("| Primary Attack strength: " + myPokemon.getAttack1().getStrength());
            System.out.println("| Primary Attack type: " + myPokemon.getAttack1().getType().toString());
            System.out.println("| Secondary Attack: " + myPokemon.getAttack2().getName());
            System.out.println("| Secondary Attack strength: " + myPokemon.getAttack2().getStrength());
            System.out.println("| Secondary Attack type: " + myPokemon.getAttack2().getType().toString());
            System.out.println("|_______________________________________________________________");
            System.out.println("| Enemy Pokemon stats: ");
            System.out.println("| Pokemon: " + myPokemon.getName());
            System.out.println("| Type: " + myPokemon.getType().toString());
            System.out.println("| Hp: " + myPokemon.getHp());
            System.out.println("| Atk: " + myPokemon.getAtk());
            System.out.println("| Sp. Atk: " + myPokemon.getSpAtk());
            System.out.println("| Def: " + myPokemon.getDef());
            System.out.println("| Sp. Def: " + myPokemon.getSpDef());
            System.out.println("| Primary Attack: " + myPokemon.getAttack1().getName());
            System.out.println("| Primary Attack strength: " + myPokemon.getAttack1().getStrength());
            System.out.println("| Primary Attack type: " + myPokemon.getAttack1().getType().toString());
            System.out.println("| Secondary Attack: " + myPokemon.getAttack2().getName());
            System.out.println("| Secondary Attack strength: " + myPokemon.getAttack2().getStrength());
            System.out.println("| Secondary Attack type: " + myPokemon.getAttack2().getType().toString());
        }

        private void healPokemon(Pokemon myPokemon, Trainer player, int myPokemonMaxHealth) {

            if(player.getInv().getBerriesAmount() > 0) {
                myPokemon.setHp(myPokemonMaxHealth);
                player.getInv().setBerriesAmount(player.getInv().getBerriesAmount() - 1);
            }

        }

        private void printBattleUI() {

            System.out.println("What are you going to do?");
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

        }

        // Listing all Pokemon in the team
        private void listTeamPokemon(Trainer player) {
            for (int i = 0; i < player.getTeam().getTeamList().size(); i++) {
                System.out.println((i + 1) + ". " + player.getTeam().getTeamList().get(i).getName() + "\n\n");
            }
        }

        private Pokemon generateEnemyPokemon() {
            Random r = new Random();
            int pokemonNumber = r.nextInt(allPokemon.size());
            Pokemon enemyPokemon = new Pokemon(allPokemon.get(pokemonNumber).getId(), allPokemon.get(pokemonNumber).getName(),
                    allPokemon.get(pokemonNumber).getType(), allPokemon.get(pokemonNumber).getHp(),
                    allPokemon.get(pokemonNumber).getAtk(), allPokemon.get(pokemonNumber).getDef(),
                    allPokemon.get(pokemonNumber).getSpAtk(), allPokemon.get(pokemonNumber).getSpDef(),
                    allPokemon.get(pokemonNumber).getAttack1(), allPokemon.get(pokemonNumber).getAttack2(),
                    allPokemon.get(pokemonNumber).isSeen(), allPokemon.get(pokemonNumber).isCatched());
            return enemyPokemon;
        }
}
