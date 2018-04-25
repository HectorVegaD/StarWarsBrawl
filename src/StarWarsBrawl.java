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
	//[doesn't need to be global - fix]

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
		
		ArrayList<Entity> player = new ArrayList<Entity>(); //player's team
		ArrayList<Entity> enemyTeam= new ArrayList<Entity>(); //enemy team
		
		Entity jedi = createJedi(scan);
		player.add(jedi); //initialize and add player's character
		
		Entity sith = new Sith(SITHNAME, "Who's your father? This guy! noob.");
		enemyTeam.add(sith); //initialize enemy leader
		
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
		
		createTeams(enemyTeam, player, gameMode); //this creates the teams
		
		/**
		 * Stage 3 - Deathmatch Game Mode Loop.
		 */
		
		while(gameMode != 3){
			if(isDefeated(enemyTeam)){
				gameMode = 3;
				break;
			}else if(isDefeated(player)){
				
			}
		}
		
		while(gameMode != 3){ //runs until user selects to exit.
			
			if(isDefeated(enemyTeam)){ // enemies defeated = victory.
				gameMode = 3;
				break;
			}else if(allyCount == 0){ //player characters defeated = defeat.
				System.out.println("Your team has been defeated! "
						+ "The galaxy is doomed!!");
				break;
			}else if(gameMode == 2 && !player.get(7).getActive()){
				System.out.println("Your AstroMech has been taken down! "
						+ "The mission has failed! you must retreat!");
				break;
			}//if gamemode 2 & astromech character is dead = defeat.
			printTeams(enemyTeam, player);//prints teams and their health
			if(player.get(0).getActive()){//checks if user's character is alive
				do{
					printTeamDeathMatch();//prints main user menu
					System.out.println(((MedicalD)player.get(6)).getNumTask()+
							" Heals Remaining");
					if(enemyTeam.get(6) instanceof Shields && enemyTeam.get(6).getActive())
						System.out.println("Enemy shields are still up!");
					if(gameMode == 2)
						if(enemyCount > 1)//checks if computer can be attacked
							System.out.println("Enemy units are still up, "
									+ "defeat them in order to gain access to,"
									+ " and hack or attack the computer!");
						else
							System.out.println("The computer is exposed!");
					do{
						choice = getValidMenuChoice(scan, 1, 2);
						scan.nextLine();//clear line
						//checks whether the medical droid is still alive
						if(!player.get(6).getActive() && choice == 2)
							System.out.println("Your Medical Droid has been "
									+ "destroyed! You can't heal!");
						if(((MedicalD)player.get(6)).getNumTask() == 0){
							System.out.println("Your Medical Droid has run out"
									+ " of Medpacks and cannot heal!");
						}
					}while(!player.get(6).getActive() && choice == 2 || 
							((MedicalD)player.get(6)).getNumTask() == 0 && 
							choice == 2);
					//Repeat if the user keeps pressing heal without a droid
					exitHeal = 0;
					switch(choice){
					case 1://user attacks
						do{
							//prints out the list of targets to attack
							attackChoice(enemyTeam);
							choice = getValidMenuChoice(scan, 1, enemyTeam.size());
							choice--;
							if(enemyTeam.get(6) instanceof Shields && 
									enemyTeam.get(6).getActive()){
								System.out.println("The shield generators are"
										+ " still active! You are forced to "
										+ "attack the shields");
								choice = 6;
							}
							if(!enemyTeam.get(choice).getActive()){
								System.out.println("This target has been "
										+ "defeated, please select a different"
										+ " target.");
							}
							if(enemyCount > 1 && choice == 7){
								System.out.println("Enemy units are still "
										+ "defending the computer! "
										+ "Defeat them first!");
							}
						}while(enemyTeam.get(choice).getHp() == 0 || 
								enemyCount > 1 && choice == 7 );
						//user can attack with the jedi
						jedi.doTask(enemyTeam.get(choice));
						System.out.println(" ");
						//lowers the counter of remaining enemies
						if(enemyTeam.get(choice).getHp() == 0)
							enemyCount--;
						if(enemyCount == 0){
							System.out.println("Congratulations! The enemy "
									+ "team has been eradicated! You are "
									+ "successful and the galaxy is safe!");
						}
						break;
						
					case 2://implement healing
						do{
							healChoice(player);
							choice = getValidMenuChoice(scan, 1, 7);
							if(choice == 7){
								exitHeal = choice;
								break;
							}
							choice--;
							if(player.get(choice) instanceof Jedi){
								Jedi downC = (Jedi)player.get(choice);
								maxHealth = downC.maxHealth();
							}else{
								Rebel downC = (Rebel)player.get(choice);
								maxHealth = downC.maxHealth();
							}
							//checks if the target is dead:
							if(!player.get(choice).getActive() || 
									player.get(choice).getHp() == maxHealth)
								System.out.println("This target is either "
										+ "defeated or at full health. Please "
										+ "select another target.");
						}while(!player.get(choice).getActive() || 
								player.get(choice).getHp() == maxHealth);
						//check if the computer is still active or 
						//the rest of the team is still active
						if(choice != 7)
							player.get(6).doTask(player.get(choice));
					}
				}while(exitHeal == 7);	
			}else{//if the user's jedi has been slain, 
				//this will allow the user to see the outcome of the fight
				System.out.println("Your Jedi has been defeated! But your "
						+ "squad fights on in hopes of avenging you!");
				System.out.println("Enter any key to continue viewing the "
						+ "fierce battle!");
				scan.nextLine();
			}
			
			for(int i = 1; i <= 5; i++){//rebels attack
				if(enemyCount == 0){
					break;
				}
				if(player.get(i).getActive() && enemyCount != 0){//checks to see there are enemies remaining and current rebel is alive
					do{
						npcTarget = (int)(Math.random()*enemyTeam.size());//put in a check to see if the shields are still active and if the rest of the enemy team is still active
						if(gameMode == 2){
							if(enemyTeam.get(6).getActive())
								npcTarget = 6;
						}
					}while(!enemyTeam.get(npcTarget).getActive() || enemyCount > 1 && npcTarget == 7);//unable to target 6 until others are defeated
					player.get(i).doTask(enemyTeam.get(npcTarget));
					System.out.println(" ");
					if(!enemyTeam.get(npcTarget).getActive())//lowers the counter of remaining enemies
						enemyCount--;
					if(enemyCount == 0){
						System.out.println("Congratulations! The enemy team has been eradicated! You are successful and the galaxy is safe!");
						break;
					}
				}
			}
			if(gameMode == 2){
				if(player.get(7).getActive() && enemyTeam.get(6).getActive()){
					player.get(7).doTask(enemyTeam.get(6));
					if(!enemyTeam.get(6).getActive())
						enemyCount--;
				}else if(player.get(7).getActive() && enemyCount == 1){
					player.get(7).doTask(enemyTeam.get(7));
					System.out.println("Congratulations! Imperial Command is under your control and the enemies have been slain!");
					System.out.println("You are successful and the galaxy is safe!");
					enemyCount--;
					break;
				}
			}
			
			injuredNotDeadE = injuredEnemies(enemyTeam);//checks for injured but not dead teammates, if found it returns true
			enemyChoice = (int)(Math.random()*10+1);//random number
			if(!enemyTeam.get(0).getActive() && gameMode == 1)//if the sith is dead, the enemy team's only choice will be to heal
				enemyChoice = 4;
			if(gameMode == 1){
				if(enemyChoice % 4 == 0 && injuredNotDeadE && enemyTeam.get(6).getActive() && gameMode == 1 || ((MedicalD)enemyTeam.get(6)).getNumTask() == 0){
					//20% chance to select healing, and if an enemy is injured, and the Medical droid is still alive then the computer will heal
					do{
						enemyChoice = (int)(Math.random()*6);
						if(enemyTeam.get(enemyChoice) instanceof Sith){
							Sith downC = (Sith)enemyTeam.get(enemyChoice);
							maxHealth = downC.maxHealth();
						}else{
							Stormtrooper downC = (Stormtrooper)enemyTeam.get(enemyChoice);
							maxHealth = downC.maxHealth();
						}//gets the maxHealth of the chosen target to be healed.
					}while(!enemyTeam.get(enemyChoice).getActive() || enemyTeam.get(enemyChoice).getHp() == maxHealth);
					//prevent a dead or full health target from being healed
					enemyTeam.get(6).doTask(enemyTeam.get(enemyChoice));
				}else{//if the choice to heal didnt activate, the they just attack
					if(enemyTeam.get(0).getActive()){//checks to see if the sith lord is still alive
						do{
							npcTarget = (int)(Math.random()*player.size());
						}while(!player.get(npcTarget).getActive());//it will select a valid target
						enemyTeam.get(0).doTask(player.get(npcTarget));
						System.out.println(" ");
						if(!player.get(npcTarget).getActive())//lowers the counter of remaining enemies
							allyCount--;
						if(allyCount == 0){
							System.out.println("The Sith Lord and the Imperial Delta Squad are Victorious!");
						}
					}
				}
			}else{
				if(enemyTeam.get(0).getActive()){//checks to see if the sith lord is still alive
					do{
						npcTarget = (int)(Math.random()*player.size());
					}while(!player.get(npcTarget).getActive());//it will select a valid target
					enemyTeam.get(0).doTask(player.get(npcTarget));
					System.out.println(" ");
					if(!player.get(npcTarget).getActive())//lowers the counter of remaining enemies
						allyCount--;
					if(allyCount == 0){
						System.out.println("The Sith Lord and the Imperial Delta Squad are Victorious!");
					}
				}
			}
			for(int i = 1; i <= 5; i++){//enemy team attack
				if(enemyTeam.get(i).getActive() && allyCount != 0){
					do{
						npcTarget = (int)(Math.random()*player.size());
					}while(!player.get(npcTarget).getActive());
					enemyTeam.get(i).doTask(player.get(npcTarget));
					System.out.println(" ");
					if(!player.get(npcTarget).getActive())//lowers the counter of remaining enemies
						allyCount--;
					if(allyCount == 0){
						System.out.println("The Sith Lord and the Imperial Delta Squad are Victorious!");
						break;
					}
				}
			}
		}
		System.out.println("Exiting game...");
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
	 * @param dark - ArrayList of enemies to be displayed
	 * @param player - ArrayList of Good Guys to be displayed
	 */
	public static void printTeams(ArrayList<Entity> dark, ArrayList<Entity> player){
		System.out.println("Good Guys:");
		System.out.println("---------");
		int health;
		String name;
		for(int i = 0; i < player.size(); i++){
			health = player.get(i).getHp();
			name = player.get(i).getName();
			System.out.printf("%s%5d \n", name, health);
		}
		System.out.println(" ");
		System.out.println("Bad Guys:");
		System.out.println("---------");
		for(int i = 0; i < dark.size(); i++){
			System.out.println(dark.get(i).getName() + " " + dark.get(i).getHp());
		}
		System.out.println(" ");
	}
	
	/**
	 * This will print out the list of enemy names for the user to select one to attack
	 * @param dark - the Arraylist containing the enemy entities
	 */
	public static void attackChoice(ArrayList<Entity> dark){
		System.out.println("Choose someone to attack:");
		for(int i = 1; i <= dark.size(); i++){
			System.out.println(i+". "+dark.get(i-1).getName()+"   "+dark.get(i-1).getHp());
		}
		System.out.println(" ");
	}
	
	/**
	 * This will display all team members for the user to select who to heal
	 * @param player - the ArrayList containing the user's team
	 */
	public static void healChoice(ArrayList<Entity> player){
		System.out.println("Choose who you want to heal:");
		for(int i = 1; i <= 6; i++){
			System.out.println(i+". "+player.get(i-1).getName()+"   "+player.get(i-1).getHp());
		}
		System.out.println("7. Return to main action menu\n");
	}
	
	/**
	 * This method will check to see if there is at least one person on the enemy team that is healable and that
	 * is injured, but no dead.
	 * @param dark - enemy team
	 * @return true if there is someone that is injured but not dead, false if otherwise
	 */
	public static boolean injuredEnemies(ArrayList<Entity> dark){
		int maxHealth;
		boolean injuredNotDead = false;
		for(int i = 0; i <= 5; i++){
			if(dark.get(i) instanceof Sith){
				Sith downC = (Sith)dark.get(i);
				maxHealth = downC.maxHealth();
			}else{
				Stormtrooper downC = (Stormtrooper)dark.get(i);
				maxHealth = downC.maxHealth();
			}
			if(dark.get(i).getHp() != maxHealth && dark.get(i).getHp() != 0)
				injuredNotDead = true;
		}
		return injuredNotDead;
	}
	
	/**
	 * This method iterates through the enemy team ArrayList and determines
	 * if there are any active enemy combatants. If there are active enemies 
	 * the 'victory' variable is set to false and game continues. 
	 * Otherwise the 'victory' variable is set as true, the user 
	 * wins, and game ends.
	 * @param dark ArrayList containing enemy combatants.
	 * @return True if enemies are all down. False if any enemy is still active.
	 */
	public static boolean isDefeated(ArrayList<Entity> dark){
		boolean victory = true;
		for(int i = 0; i < dark.size(); i++){
			if(dark.get(i).getActive() == true)
				victory = false;
		}
		return victory;
	}
	/**
	 * This method will print out the player's team and the Enemy team. The names and catch phrases
	 * have already been pre-defined and can only be changed by changing the code itself. Adds the created
	 * Entities to the Entity Arraylist of the two teams. It uses a pass by reference to change original arraylists
	 * @param dark - enemy team arraylist
	 * @param player - player team arraylist
	 */
	public static void createTeams(ArrayList<Entity> dark, ArrayList<Entity> player, int gMode){
		Entity Rebel = new Rebel("H.Solo", "Ooo yea, I always shoot first");
		Entity Rebel1 = new Rebel("Ackbar", "ITS A TRAP... for you! BOOM!");
		Entity Rebel2 = new Rebel("Chewie", "Uuuuurawwwrr Aaaargh Ahhhrrrr!");
		Entity Rebel3 = new Rebel("Ahsoka", "I can handle anything!");
		Entity Rebel4 = new Rebel("P Leia", "I'm stronger than i Look");
		Entity MedicalD5 = new MedicalD("21B-MD");
		//I know ahsoka is not a rebel soldier but still x)
		player.add(Rebel);
		player.add(Rebel1);
		player.add(Rebel2);
		player.add(Rebel3);
		player.add(Rebel4);
		player.add(MedicalD5);
		Entity Trooper = new Stormtrooper("Delta 38-Boss", "Delta Squad is on the job!");
		Entity Trooper1 = new Stormtrooper("Delta 62-Scorch", "Hehehe, that blew up real good!");
		Entity Trooper2 = new Stormtrooper("Delta 07-Sev", "Eliminating target");
		Entity Trooper3 = new Stormtrooper("Delta 40-Fixer", "Watch the Master at work");
		Entity Trooper4 = new Stormtrooper("Boba Fett", "I'm on the hunt");
		dark.add(Trooper);
		dark.add(Trooper1);
		dark.add(Trooper2);
		dark.add(Trooper3);
		dark.add(Trooper4);
		if(gMode == 2){//if game mode is 2, then add the extra team members required
			Entity Astro = new AstroMech("T3-M4 A");
			Entity Shields = new Shields();
			Entity Computer = new Computer();
			player.add(Astro);
			dark.add(Shields);
			dark.add(Computer);
		}else{
			Entity Medical5D = new MedicalD("66A-Imperial MD");
			dark.add(Medical5D);
		}
	}
	
	/**
	 * This method is used to initialize the user's primary character. This
	 * character will have its name and crystal color defined by the user.
	 * @param scan Scanner used to take user input.
	 * @return The user's initialized character.
	 */
	public static Entity createJedi(Scanner scan){
		String jediName, colorCrystal;//define user created character.
		System.out.println("What is the name of your legendary Jedi.");
		jediName = scan.nextLine();
		System.out.println("What is the color of your lightsaber crystal?");
		colorCrystal = scan.nextLine();
		String catchPhrase = "I go hard, I GO LUDICROUS SPEED!.";
		return new Jedi(jediName, catchPhrase, colorCrystal, "Lightsaber");
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
