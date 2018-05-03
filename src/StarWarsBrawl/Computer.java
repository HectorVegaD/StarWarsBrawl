package StarWarsBrawl;

/**
 * @author hectordelavega
 *This is the class of one of two objects / obstacles to be destroyed in the second game mode
 *It will contain a doTask() out of necessity (to override the Entity's doTask()) but will possess 
 *no need for a task to be done at all.
 */
public class Computer extends Entity {
	private static String name = "Imperial Base Computer";//name of the computer
	private static int hp = 1000000;//hp of the computer, emphasizing reliance on the Astromech

	/**
	 * Constructor for the computer, storing the name and the hp in the Entity super class
	 */
	public Computer() {
		super(name, hp);
	}

	@Override
	/**
	 * The computer will serve only as an obstacle to be destroyed so it will have no true task to do
	 * but to be destroyed.
	 */
	void doTask(Entity e) {
		System.out.println("Imperial base defenses have been deactivated");
	}
	
}
