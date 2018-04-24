import java.util.*;//used to import scanner

/**
 * @author hectordelavega
 *This is the subclass Jedi which will contain the saberColor and the force power specific to this subclass.
 *It will also contain a generica weapon specific to the Jedi and the Sith classes which could have been
 *passed in from the main to the super class person but i decided just to leave it here (which was a mistake i think :()
 *It will have a doTask() function that will replace the attack function that was to be overriden from the 
 *Person class. The doTask() will take in user input to decide 1 thing: how to attack the already selected target
 *Entity E: with lightsaber or with force. It will check user input through a user input check form the super Person.it
 *will also have an accessor for the saber color and a heal function created from implementing Healable. Lastly it will
 *have a useForce function created from implementing HasForce. Being a subclass that can be healed, it will
 *allow the droid function to gain access to its Max Health through an accessor and type casting in order to
 *prevent overhealing.
 */
public class Jedi extends Person implements HasForce, Healable{
	private String saberColor, forcePower = "Force Blast";//subclass unique force power and sabercolor
	private static final int FORCE_LIFE = 100;//life
	//quip = catch phrases
	
	/**
	 * Constructor. It will store the sabercolor and pass on the other data members to be stored in the
	 * super classes.
	 * @param name name of the jedi
	 * @param quip - catch phrase
	 * @param c - color of saber
	 */
	public Jedi(String name, String quip, String c, String weapon){
		super(name, FORCE_LIFE, weapon, quip);
		saberColor = c;
	}
	
	/**
	 * This doTask() is meant to override the super Person class' attack() function. It provides functionality
	 * to attack and allows the user to select the attack style. Then it sets the health of the target, Entity E, 
	 * by calling E's modifyHealth() function from the Entity super class.
	 */
	public void doTask(Entity E){
		Scanner scan = new Scanner(System.in);
		int choice;
		System.out.println("How to attack?");//change this up
		System.out.println("1. "+this.getWeapon()+".");
		System.out.println("2. Force.");
		choice = getValidMenuChoice(scan, 1, 2); // implement the user input check method
		scan.nextLine();//clears scan
		switch(choice){
		case 1:
			attack(E);
			break;
		case 2:
			useForce(E);
			break;
		}
		if(E.getHp() <= 0){//checks to see if the character died and sets them inactive if so
			System.out.println(E.getName()+" has been taken down!");
			sayCphrase();
			E.setInactive();
		}
	}
	
	public String sColor(){
		return saberColor;
	}
	
	/**
	 * Function overriding the heal() of the implemented Healable interface. This will be called from
	 * the MedicalD doTask() heal function.
	 * @param heals - the amount being healed
	 */
	public void heal(int heals){
		int health;
		health = this.getHp() + heals;
		if(health >= FORCE_LIFE){//makes sure there is no overhealing
			health = FORCE_LIFE;
		}
		this.modifyHealth(health, 1);
	}
	
	/**
	 * Accessor overriden from the Healable interface. This is used to return the Max Health of a Person
	 * subclass in order to prevent the target from being overhealed in the MedicalD doTask() function.
	 */
	public int maxHealth(){
		return FORCE_LIFE;
	}
	public void attack(Entity E){
		int hitPower = (int)(Math.random()*10+1);
		int MissChance = (int)(Math.random()*100+1);
		if(E instanceof Shields || E instanceof Computer)//if your attacking shields or computers, Miss chance is set at 0%
			MissChance = 5;
		System.out.println(getName()+" attacks "+ E.getName() +" with their "+saberColor+" "+this.getWeapon());
		if(MissChance % 4 == 0){ //Miss chance is set at 25% or 0% if its a computer or shields being attacked
			if(E instanceof Sith ){
				System.out.println("but "+E.getName()+" has blocked the attack with his red Lightsaber");
				((Sith)E).sayCphrase();
			}else{
				System.out.println("But "+E.getName()+" moves out of the way and avoids the attack.");
				if(E instanceof Stormtrooper)
					((Stormtrooper)E).sayCphrase();
			}
		}else{
			System.out.println("Swinging with a deadly strike "+getName()+" hits "+E.getName()+" for "+hitPower+" damage.");
			E.modifyHealth(hitPower, 2);
		}
	}
	@Override
	/**
	 * Function overriden from the HasForce Interface used to provide functionality for attacking with
	 * the force. If this attack option is chosen in the doTask() function. It will attack the target Entity E
	 * with a unique force power specific to this subclass and then set the damage taken to the overall health
	 * of E through the modifyHealth() method of the Entity superclass
	 */
	public void useForce(Entity E) {
		int hitPower = (int)(Math.random()*10 + 5);//higher force hit
		int MissChance = (int)(Math.random()*100 + 1)%3;//higher miss chance for stronger force attack
		if(E instanceof Shields || E instanceof Computer)//0% miss chance for computer and shield targets
			MissChance = 5;
		System.out.println(this.getName()+" attacks "+ E.getName() +" with "+forcePower);
		if(MissChance == 0){ //Miss chance is set at 33% or set at 0% for computers and shields
			if(E instanceof Sith){
				System.out.println("but "+E.getName()+" has evaded "+forcePower+" with force leap.");
				((Sith)E).sayCphrase();
			}
			else{
				System.out.println("but "+E.getName()+" rushes behind cover and evades "+forcePower+".");
				if(E instanceof Stormtrooper)
					((Stormtrooper)E).sayCphrase();
			}
		}else{
			System.out.println(forcePower+" voilently strikes "+E.getName()+" for "+hitPower+" damage.");
			E.modifyHealth(hitPower, 2);
		}
	}
	/**
	 * This will check user input when needed within the subclasses of Person.
	 * @param scan - the scanner used to read input
	 * @param low - lowest accepted int input
	 * @param high - highest accepted int input
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
