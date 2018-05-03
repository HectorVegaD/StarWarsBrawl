package StarWarsBrawl;

/**
 * @author hectordelavega
 *This will be the generic interface that will be implemented to force users, allowing them to attack
 *with the force. This requires for the force users to have a force power and a method to attack with that 
 *force power.
 */
public interface HasForce {
	
	/**
	 * This will be overridden to allow for unique force power.
	 */
	String forcePower = "Power";
	
	/**
	 * This will be overridden to create a function allowing the use of attacking with the force
	 * @param E
	 */
	void useForce(Entity E);
	
}
