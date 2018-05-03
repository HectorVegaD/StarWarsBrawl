package StarWarsBrawl;

/**
 * @author hectordelavega
 *This is for the subclass Shields. Because the Shields will only serve as an obstacle to be destroyed 
 *in order to reach success in the second game mode, there will be no functionality to the doTask()
 *It is here to be destoyed by the astromech
 */
public class Shields extends Entity{
	private static int shieldHealth = 100;//health of the shields
	private static String name = "Shield Generators";//name

	/**
	 * constructor for the shields, calls the super() to store the name and the health.
	 */
	public Shields() {
		super(name, shieldHealth);
	}

	@Override
	/**
	 * No functionality will be given to the shields other than being unable to target other enemies while
	 * the shield is active
	 */
	void doTask(Entity e) {
		System.out.println("Shields have been deactivated");
	}

}
