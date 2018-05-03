package StarWarsBrawl;

/**
 * @author hectordelavega
 *This is one of 2 subclasses of the abstract class Droid and by extension: Entity. The DoTasks() for this
 *subclass will override the Droid and Entity's doTask() function and allow the Astromech to interract with
 *the computer and the Shields, ultimately destroying them/taking them down.
 */
public class AstroMech extends Droid {
	private static int hp = 150;//more health because of importance of Astromech for second game mode
	private int hackChance; // the chance at successfully hacking / destroying a obstacle (shields / door)

	/**
	 * This is the constructor for the Astromech, sending the name and the hp all the way up to the Entity 
	 * to be stored
	 * @param n - the name given to the AstroMech
	 */
	public AstroMech(String n) {
		super(n, hp);
	}
	/**
	 * Overrides the super doTask() methods to provide functionality to this subclass of droid. The doTask()
	 * of the astromech will provide interaction with the droid and the computer, allowing for a small 
	 * chance of taking them down - Unlimited amount of attempts, no need for numtasks (did this to make up 
	 * for the fact that the User's team is still being attacked while the shields are up. Otherwise if the 
	 * shield hacking fails, the team wont fall behind and automatically lose.)
	 */
	public void doTask(Entity E){
		if(E instanceof Shields){//checks if it is interracting with a shield or a computer
			hackChance = (int)(Math.random()*10+1);
			if(hackChance % 2 == 0){//50% chance of successfully taking down the door
				System.out.println("Overriding Defenses");
				System.out.println("Shield Generators have been shut down... Beep Boop");
				System.out.println("");
				E.setInactive();//kills shields
			}else
				System.out.println("Overriding Shield defenses");
				System.out.println("Overriding attempt : failed");//fail at overriding, 50% chance
				System.out.println("");
		}else{//if we're dealing with the computer, then we've defeated the enemies and we instantly take over the base
			System.out.println("Overriding Imperial Base Defenses:");
			System.out.println("Defenses Overriden, control of Imperial Base: Success");
			System.out.println("");
			E.setInactive();//sets inactive to win the game.
		}
	}	
}
