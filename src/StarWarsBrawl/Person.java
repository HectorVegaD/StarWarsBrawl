package StarWarsBrawl;
import java.util.Scanner;

/**
 * @author hectordelavega
 *This class will define the abstract generic properties and abilities/behavior of the 'people' type of classes
 *such as the jedi, sith, rebel, and stormtroopers. Because the main abilities of these classes will be to
 *attack a target, their doTask function will provide functionality to their attacks
 */
public abstract class Person extends Entity {
	private String weapon, cPhrase; //Stores the person objects name and CatchPhrase
	
	public Person(String name, int hp, String wep, String catchP){
		super(name, hp);
		weapon = wep;
		cPhrase = catchP;
	}
	
	/**
	 * This method will return the weapon name, which is stored in the person class, of the object
	 * @return weapon
	 */
	public String getWeapon(){
		return weapon;
	}
	abstract void attack(Entity E);
	/**
	 * Accessor for the Cphrase 
	 * This will have the implicit object say their catch phrase
	 */
	public void sayCphrase(){
		System.out.println(getName()+": "+cPhrase);
	}
}
