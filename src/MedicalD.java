
/**
 * @author hectordelavega
 *This is the subclass of Droid for the class of Medical Droids. It will contain a doTask() function
 *that will override the droid's doTask() function and allow the Medical Droid Entities to heal appropriate
 *targets. It will provide checking in the main so that full health, or defeated targets are unhealable.
 *The doTask() healing function will call the subclass' heal function through typecasting and send in
 *the healed amount to be added to the health.
 */
public class MedicalD extends Droid {
	private int heals;//healed amount
	private static int hp = 50;//droid life
	
	/**
	 * Constructor, passing in the name, and passing on the name and hp to ultimately the entity superclass to be stored.
	 * @param n - name of the droid
	 */
	public MedicalD(String n) {
		super(n, hp);
	}
	/**
	 * doTask() function that will override the Droid's doTask() function and allow the MedicalD subclass
	 * to heal the target Entity E through type casting and calling their subclasses which have implemented
	 * the Healable interface.
	 */
	public void doTask(Entity E){
		if(getNumTask() > 0){
			heals = (int)(Math.random()*7+5);
			System.out.println(this.getName()+" heals "+E.getName()+" for "+heals+" hp");
			if(E instanceof Healable){
				Healable downC = (Healable)E;
				downC.heal(heals);
			}
			useTask();
			System.out.println(getNumTask()+" Medpacks remaining");
			System.out.println(" ");
		}else
			System.out.println(getName()+" tried to heal "+E.getName()+" but has run out of MedPacks!");
	}
}
