package StarWarsBrawl;
import java.util.ArrayList;
import java.util.Scanner;

public class Teams {
	

	public static void createPlayerTeam(Entity[] playerTeam){
		Entity jedi = createJedi();
		playerTeam[0] = jedi;
		String[] catchPhrases = {"Ooo yea, I always shoot first", 
		                         "ITS A TRAP... for you! BOOM!",
		                         "Uuuuurawwwrr Aaaargh Ahhhrrrr!",
		                         "I can handle anything!",
		                         "I'm stronger than i Look"};
		String[] names = {"H.Solo", "Ackbar", "Chewie", "Ahsoka", "P Leia"};
		//determine if sticking with ArrayLIst or Array [fix]
		for (int index = 1; index < 6; index ++){
			Entity Rebel = new Rebel(catchPhrases[index-1], names[index-1]);
			playerTeam[index] = Rebel;
		}
		Entity MedicalD5 = new MedicalD("21B-MD");
		playerTeam[6] = MedicalD5;
	}
	
	/**
	 * This method is used to initialize the user's primary character. This
	 * character will have its name and crystal color defined by the user.
	 * @param scan Scanner used to take user input.
	 * @return The user's initialized character.
	 */
	public static Entity createJedi(){
		Scanner scan = new Scanner(System.in);
		String jediName, colorCrystal;//define user created character.
		System.out.println("What is the name of your legendary Jedi.");
		jediName = scan.nextLine();
		System.out.println("What is the color of your lightsaber crystal?");
		colorCrystal = scan.nextLine();
		String catchPhrase = "I go hard, I GO LUDICROUS SPEED!.";
		scan.close();
		return new Jedi(jediName, catchPhrase, colorCrystal, "Lightsaber");
	}
	
	public static void createEnemies(Entity[] enemyTeam){
		Entity sith = new Sith("Darth Vader", 
				"Who's your father? This guy! noob.");
		enemyTeam[0] = sith;
		String[] catchPhrases = {"Delta Squad is on the job!", 
				"Hehehe, that blew up real good!", 
				"Eliminating target",
				"Watch the Master at work",
				"I'm on the hunt"};
		String[] names = {"Delta 38-Boss", "Delta 62-Scorch", "Delta 07-Sev", 
				"Delta 40-Fixer", "Boba Fett"};
		
		for(int index = 1; index < 6; index++){
			Entity trooper = new Stormtrooper(catchPhrases[index-1], 
					names[index-1]);
			enemyTeam[index] = trooper;
		}
		
		Entity Medical5D = new MedicalD("66A-Imperial MD");
		enemyTeam[6] = Medical5D;
	}
	
	public static void gameMode2Enemies(Entity[] enemyTeam, Entity[] playerTeam){
		Entity Astro = new AstroMech("T3-M4 A");
		Entity Shields = new Shields();
		Entity Computer = new Computer();
		playerTeam[7] = Astro;
		enemyTeam[6] = Shields;
		enemyTeam[7] = Computer;
	}
	
	/**
	 * This method iterates through the enemy team ArrayList and determines
	 * if there are any active enemy combatants. If there are active enemies 
	 * the 'victory' variable is set to false and game continues. 
	 * Otherwise the 'victory' variable is set as true, the user 
	 * wins, and game ends.
	 * @param team ArrayList containing enemy combatants.
	 * @return True if enemies are all down. False if any enemy is still active.
	 */
	public static boolean isDefeated(Entity[] team){
		boolean victory = true;
		for(int index = 0; index < team.length; index++){
			if(team[index].getActive() == true)
				victory = false;
		}
		return victory;
	}
	
	/**
	 * This method will check to see if there is at least one person on the enemy team that is healable and that
	 * is injured, but no dead.
	 * @param enemyTeam - enemy team
	 * @return true if there is someone that is injured but not dead, false if otherwise
	 */
	public static boolean injuredEnemies(Entity[] enemyTeam){
		boolean injuredNotDead = false;
		if(enemyTeam[0].getHp() != 100 && enemyTeam[0].getHp() != 0)
			injuredNotDead = true;
		for(int index = 1; index <= 5; index++){
			if(enemyTeam[index].getHp() != 50 && enemyTeam[index].getHp() != 0)
				injuredNotDead = true;
		}
		return injuredNotDead;
	}

}
