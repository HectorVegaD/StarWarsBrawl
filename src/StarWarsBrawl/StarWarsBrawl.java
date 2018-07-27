package StarWarsBrawl;
/*
 * Hector De La Vega
 * CECS 277 
 * Assignment 1, Project 1
 * February 10, 2015
 */
import java.util.*;

/**
 * <h1>Star Wars Brawl!</h1>
 * This StarWarsBrawl program implements a game application that simulates a
 * battle between the user and simple A.I.. The game application consists of 
 * two types of modes - "Deathmatch" and "Objective Control".
 * <p>The primary game loop will run in this class. User will create their 
 * character which will lead the player's team in battle. The user will then 
 * initiate a game mode. All objects will be initialized and 
 * stored here.
 * <p>
 * <h2>Game Modes</h2>
 * <b>Team Deathmatch:</b> Objective is to be the last team standing.<p>
 * <b>Objective Control:</b> Protect the Astromech unit until all enemies are
 * defeated, allowing it to hack and shut down the Death Star's main shields.
 * @author hectordelavega
 * @since 2015-02-10
 */
public class StarWarsBrawl {
	private static String SITHNAME = "Sith Lord Vader";//name of enemy leader
	//doesn't need to be global - [fix]

	/**
	 * Main method containing the mode selection and core game/program loop.
	 * <h2>Classes Utilized:</h2> Entity superclass and its multiple 
	 * sub-classes to simulate the core game mechanics.
	 * @param args Unused
	 */
	public static void main(String[] args) {
		
		/**
		 * Stage 1 - Declare Teams & Necessary variables.
		 */
		Scanner scan = new Scanner(System.in);
		int choice, npcTarget, gameMode, enemyChoice, maxHealth, enemyCount, 
		allyCount, exitHeal;
		boolean injuredNotDeadE; //checks for injured, but not dead enemies
		boolean invalidChoice;
		
		printGameMode(); //game mode menu
		gameMode = getValidMenuChoice(scan, 1, 3); //user selection
		scan.nextLine(); //clear line
		
		/**
		 * Stage 2 - Prompt for game mode, initialize teams accordingly.
		 */
		
		if(gameMode == 1){
			enemyCount = 7;
			allyCount = 7;
			System.out.println("Enemy Team Sighted! Destroy Them!");
		}else{
			enemyCount = 8;
			allyCount = 8;
			System.out.println("Break through the Shield generators, defeat "
					+ "the enemy team, and hack the Imperial Command Computer "
					+ "before your battle-ready Astromech dies!");
		}
		Entity[] playerTeam;
		Entity[] enemyTeam; //enemy team
		
		Teams.createPlayerTeam(playerTeam); //this creates the teams
		Teams.createEnemies(enemyTeam);
		
		/**
		 * Stage 3 - Deathmatch Game Mode Loop.
		 * Step 1: Determine if game is over with win / loss conditions.
		 * Step 2: Determine if user's character is alive
		 * 		2a: If user's character is alive - Prompt user to heal or attack.
		 * 		2b: If user chooses to heal:
		 * 			- Check if medical droid is active. Invalidate choice if droid is inactive.
		 * 			- Validate that user's heal target is injured and not dead.
		 * 		2c: If user chooses to attack:
		 * 			- Write comments. [fix]
		 * 			- 
		 */
		
		while(gameMode != 3){
			if(Teams.isDefeated(enemyTeam)){
				gameMode = 3;
				System.out.println("Congratulations! The enemy "
						+ "team has been eradicated! You are "
						+ "successful and the galaxy is safe!");
				break;
			}else if(Teams.isDefeated(playerTeam)){
				gameMode = 3;
				System.out.println("Your team has been defeated! "
						+ "The galaxy is doomed!!");
				break;
			}
			printTeams(enemyTeam, playerTeam);//prints teams and their health
			
			Entity playerCharacter = playerTeam[0];
			MedicalD medicalDroid = (MedicalD)playerTeam[6];
			if(playerCharacter.getActive()){//checks if user's character is alive
				do{
					printTeamDeathMatch();//prints main user menu
					System.out.println(medicalDroid.getNumTask()+
							" Heals Remaining");
					do{
						choice = getValidMenuChoice(scan, 1, 2);
						scan.nextLine();//clear line
						invalidChoice = false;
						
						//checks whether the medical droid is still alive
						if(!medicalDroid.getActive() && choice == 2){
							System.out.println("Your Medical Droid has been "
									+ "destroyed! You can't heal!");
							invalidChoice = true;
						}	
						if(medicalDroid.getNumTask() == 0){
							System.out.println("Your Medical Droid has run out"
									+ " of Medpacks and cannot heal!");
							invalidChoice = true;
						}
					}while(invalidChoice);
					//Repeat if the user keeps pressing heal without a droid
					
					

					switch(choice){
					case 1://user attacks
						do{
							//prints out the list of targets to attack
							printEnemyTargets(enemyTeam);
							choice = getValidMenuChoice(scan, 1, enemyTeam.length);
							invalidChoice = false;
							choice--;
							if(!enemyTeam[choice].getActive()){
								System.out.println("This target has been "
										+ "defeated, please select a different"
										+ " target.");
							}
						}while(invalidChoice);
						
						//user can attack with the jedi
						playerCharacter.doTask(enemyTeam[choice]);
						System.out.println(" ");
						//lowers the counter of remaining enemies
						if(enemyTeam[choice].getHp() == 0)
							enemyCount--;
						break;
						
					case 2://implement healing
						do{
							healChoice(playerTeam);
							choice = getValidMenuChoice(scan, 1, 7);
							if(choice == 7)
								break;
							choice--;
							// Determine if user is healing a Jedi or regular trooper. Acquire their maximum health.
							if(choice == 0)
								maxHealth = 100;
							else
								maxHealth = 50;
							
							//checks if the target is dead:
							if(!playerTeam[choice].getActive()) 
								System.out.println("Your target is defeated. Please re-select target."); 
							else if(playerTeam[choice].getHp() == maxHealth)
								System.out.println("Your target is at full health. Please re-select target.");
						
						}while(!playerTeam[choice].getActive() || 
								playerTeam[choice].getHp() == maxHealth);
					}
				}while(choice == 7);	
			}else{//if the user's jedi has been slain, 
				//this will allow the user to see the outcome of the fight
				System.out.println("Your Jedi has been defeated! But your "
						+ "squad fights on in hopes of avenging you!");
				System.out.println("Enter any key to continue viewing the "
						+ "fierce battle!");
				scan.nextLine();
			}
			
			/**
			 * Rebel Attack Process:
			 * 1. Iterate and select current rebel and verify they are active.
			 * 2. Randomly select enemy target and verify enemy is active.
			 * 3. Call doTask(), executing the attack. 
			 * 4. If enemy is defeated, update enemy team count. 
			 */
			for(int i = 1; i <= 5; i++){
				if(enemyCount == 0)
					break; //exit if no enemies remain
				Entity rebelAlly = playerTeam[i]; 
				if(!rebelAlly.getActive())
					continue;
				do{
					npcTarget = (int)(Math.random()*enemyTeam.length);
				}while(!enemyTeam[npcTarget].getActive());
				rebelAlly.doTask(enemyTeam[npcTarget]); //attack / doTask()
				System.out.println(" ");
				if(!enemyTeam[npcTarget].getActive())//lowers the counter of remaining enemies
					enemyCount--;		
			}
			
			
			/**
			 * Enemy's turn: 
			 */
			injuredNotDeadE = Teams.injuredEnemies(enemyTeam);//checks for injured but not dead teammates, if found it returns true
			enemyChoice = (int)(Math.random()*10+1);//random number
			if(!enemyTeam.get(0).getActive())//if the sith is dead, the enemy team's only choice will be to heal
				enemyChoice = 4;
			
			/**
			 * 20% chance for pc to heal ally. To heal, must verify the following:
			 * - Chosen ally is injured & not dead
			 * - Medical Droid is active / not dead
			 * - Medical Droid has heals remaining (heal counter != 0)
			 */
			if(enemyChoice % 4 == 0 && injuredNotDeadE && enemyTeam.get(6).getActive() && ((MedicalD)enemyTeam.get(6)).getNumTask() != 0){
				boolean targetAllive, targetHealthy;
				do{
					//pc / enemy selects random ally to heal.
					enemyChoice = (int)(Math.random()*6);
					
					// Determine if user is healing a Jedi or regular trooper. Acquire their maximum health.
					if(enemyChoice == 0)
						maxHealth = 100;
					else
						maxHealth = 50;
					
					// determine if pc choice can be healed.
					targetAllive = enemyTeam.get(enemyChoice).getActive();
					targetHealthy = enemyTeam.get(enemyChoice).getHp() == maxHealth;
				}while(!targetAllive || targetHealthy);//repeat if an invalid target is chosen.
				
				enemyTeam.get(6).doTask(enemyTeam.get(enemyChoice));
			}else{//if the choice to heal didnt activate, the they just attack
				if(enemyTeam.get(0).getActive()){//[fix] modularize this and make this a return condition.
					do{
						npcTarget = (int)(Math.random()*playerTeam.size());
					}while(!playerTeam.get(npcTarget).getActive());//it will select a valid target
					enemyTeam.get(0).doTask(playerTeam.get(npcTarget));
					System.out.println(" ");
					if(!playerTeam.get(npcTarget).getActive())//lowers the counter of remaining enemies
						allyCount--;
					if(allyCount == 0){
						System.out.println("The Sith Lord and the Imperial Delta Squad are Victorious!");
					}
				}
			}
			
			/**
			 * Next stage will be to enemy team members attack.
			 * Same process as the rebel's attack, but for enemies.
			 */
			
			for(int i = 1; i <= 5; i++){//enemy team attack
				if(enemyTeam.get(i).getActive() && allyCount != 0){
					do{
						npcTarget = (int)(Math.random()*playerTeam.size());
					}while(!playerTeam.get(npcTarget).getActive());
					enemyTeam.get(i).doTask(playerTeam.get(npcTarget));
					System.out.println(" ");
					if(!playerTeam.get(npcTarget).getActive())//lowers the counter of remaining enemies
						allyCount--;
					if(allyCount == 0){
						System.out.println("The Sith Lord and the Imperial Delta Squad are Victorious!");
						break;
					}
				}
			}	
		}
	}
	
	/**
	 * Prints the main gameMode Menu
	 */
	public static void printGameMode(){
		System.out.println("Welcome to Star Wars Brawl!");
		System.out.println("Please select Game Mode:");
		System.out.println("1. Team Deathmatch");
		System.out.println("2. Capture the Imperial Base");
		System.out.println("3. Exit");
	}
	
	/**
	 * This method will print out the main user combat menu
	 */
	public static void printTeamDeathMatch(){
		System.out.println("What would you like to do?");
		System.out.println("1. Attack");
		System.out.println("2. Heal"); //implement this
	}
	
	/**
	 * This method will print out both of the teams, labeling the Good Guys and Bad guys,
	 * as well as displaying the individual names and health points
	 * @param enemyTeam - ArrayList of enemies to be displayed
	 * @param playerTeam - ArrayList of Good Guys to be displayed
	 */
	public static void printTeams(Entity[] enemyTeam, Entity[] playerTeam){
		System.out.println("Good Guys:");
		System.out.println("---------");
		int health;
		String name;
		for(int i = 0; i < playerTeam.length; i++){
			health = playerTeam[i].getHp();
			name = playerTeam[i].getName();
			System.out.printf("%s%5d \n", name, health);
		}
		System.out.println(" ");
		System.out.println("Bad Guys:");
		System.out.println("---------");
		for(int i = 0; i < enemyTeam.length; i++){
			System.out.println(enemyTeam[i].getName() + " " + enemyTeam[i].getHp());
		}
		System.out.println(" ");
	}
	
	/**
	 * This will print out the list of enemy names for the user to select one to attack
	 * @param dark - the Arraylist containing the enemy entities
	 */
	public static void printEnemyTargets(Entity[] dark){
		System.out.println("Choose someone to attack:");
		for(int i = 1; i <= dark.length; i++){
			System.out.println(i+". "+dark[i-1].getName()+"   "+dark[i-1].getHp());
		}
		System.out.println(" ");
	}
	
	/**
	 * This will display all team members for the user to select who to heal
	 * @param player - the ArrayList containing the user's team
	 */
	public static void healChoice(Entity[] player){
		System.out.println("Choose who you want to heal:");
		for(int i = 1; i <= 6; i++){
			System.out.println(i+". "+player[i-1].getName()+"   "+player[i-1].getHp());
		}
		System.out.println("7. Return to main action menu\n");
	}

	
	/**
	 * This method will check user input within a certain range. It primarily is used to check input for a menu with a limited
	 * amount of options to choose from
	 * @param scan - Scanner to read user input
	 * @param low - Lowest number allowed
	 * @param high - Highest number allowed
	 * @return - valid input
	 */
	public static int getValidMenuChoice(Scanner scan, int low, int high){
		boolean rep = true;
		int choice = 0;
		while(rep){
			if(scan.hasNextInt()){
				choice = scan.nextInt();
				if(choice >= low && choice <= high)
					rep = false;
				else
					System.out.print("Invalid Input -- Please re-enter: ");
			}else{
				System.out.print("Invalid Input -- Please re-enter: ");
				String junk = scan.next();
			}
		}
		return choice;
	}
}