package StarWarsBrawl;
/**
 * @author hectordelavega
 *This is the subclass of Person defining the Rebel objects. It will have a max health set at
 *TROOPER_LIFE, which will be kept track of in order to use the maxHealth() function overriden by the 
 *Healable interface. This class will also have a doTask() function that will override the Person and Entity's
 *doTask() function and allow the rebel objects to attack their targets: Entity E. It will also have a heal
 *function used to override the one in Healable.
 */
public class Rebel extends Person implements Healable{
	private static final int TROOPER_LIFE = 50;
	
	/**
	 * The constructor for the Rebel class, where a name and catch phrase will be passed in, and passed along
	 * to the super Person and Entity along with the weapon type and hp.
	 * @param name - name of the rebel
	 * @param cPhrase - Catch phrase of the rebel
	 */
	public Rebel(String name, String cPhrase){
		super(name, TROOPER_LIFE, "Blaster", cPhrase);
	}
	
	/**
	 * Overriden from the healable interface, this will allow the MedicalD objects to heal this object
	 * @param heals is the amount being healed.
	 */
	public void heal(int heals){
		int health;
		health = getHp() + heals;
		if(health >= TROOPER_LIFE){//makes sure there is no overhealing
			health = TROOPER_LIFE;
		}
		this.modifyHealth(health, 1);//set the new healed health
	}
	
	/**
	 * Accessor overriden from the Healable interface. This is used to return the Max Health of a Person
	 * subclass in order to prevent the target from being overhealed in the MedicalD doTask() function.
	 */
	public int maxHealth(){
		return TROOPER_LIFE;
	}
	
	/**
	 * This function will override the doTask() function in Person and become the attack function, allowing
	 * the Rebel to attack the given target Entity E and set the health of Entity E after the damage is
	 * done.
	 */
	public void doTask(Entity E){
		attack(E);
	}
	
	public void attack(Entity E){
		int hitPower = (int)(Math.random()*10+1);
		int MissChance = (int)(Math.random()*100+1);
		if(E instanceof Shields || E instanceof Computer)
			MissChance = 5;
		System.out.println(this.getName()+" blasts "+ E.getName() +" with their "+this.getWeapon());
		if(MissChance % 4 == 0){ //Miss chance is set at 25% or set at 0% for computers and shields
			if(E instanceof Sith){
				System.out.println("but "+E.getName()+" has deflected the attack with his red lightsaber");
				((Sith)E).sayCphrase();
			}else{
				System.out.println("But "+E.getName()+" takes cover and avoids the attack.");
				if(E instanceof Stormtrooper)
					((Stormtrooper)E).sayCphrase();
			}
		}else{
			System.out.println("Aiming with deadly precision "+this.getName()+" hits "+E.getName()+" for "+hitPower+" damage.");
			E.modifyHealth(hitPower, 2);
		}
		if(E.getHp() <= 0){//checks to see if the character died and sets them inactive if so
			System.out.println(E.getName()+" has been taken down!");
			sayCphrase();
			E.setInactive();
		}
	}
}
