
/**
 * @author hectordelavega
 *This is the generic abstract blueprint of the Droid class. There is a constructor that will call
 *the super and store the given values (names and hp). and there is a doTask() that will be specified
 *in the subclasses of the Droid class (ie: doTask() - heal, and doTask() - hack)
 */
public abstract class Droid extends Entity {
	private int numTasks = 4;//number of times a droid can do a task
	
	/**
	 * This is the constructor of the Droid class which will be used to call the super Entity class
	 * and store the name and hp of the droid there.
	 * @param n - name of the droid
	 * @param p - hp of the droid
	 */
	public Droid(String n, int p) {
		super(n, p);
	}
	
	/**
	 * This will return the number of times a droid can do a specific task. However I decided not to 
	 * implement a restraint on the number of times a droid can heal or it can hack something.
	 * @return - number of task attempts left.
	 */
	public int getNumTask(){
		return numTasks;
	}
	public void useTask(){
		numTasks--;
	}
}
