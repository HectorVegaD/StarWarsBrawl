package StarWarsBrawl;
/**
 * @author hectordelavega
 *This class will define the subclass of a Sith. It will store the doTask() method specific to the sith
 *object as well as implement the HasForce Interface in order to use the force. It will provide accessors to the 
 *saberColor and forcePower data members that are specific to this subclass.
 */
public class Sith extends Person implements HasForce, Healable{
	private String saberColor, forcePower = "Force Lightning"; // forcepower specific to the sith
	private static final int FORCE_LIFE = 100;//hp specific to the sith
	
	/**
	 * Constructor for this subclass
	 * @param name - name of the sith
	 * @param quip - catch phrase
	 */
	public Sith(String name, String quip){
		super(name, FORCE_LIFE, "Lightsaber", quip);
		saberColor = "red";
	}
	
	/**
	 * Accessor for the color of lightsaber specific to this subclass
	 * @return sabercolor
	 */
	public String sColor(){
		return saberColor;
	}
	
	/**
	 * This is the doTask() specific for the Sith subclass that is meant to override the Person() super doTask()
	 * function. It will select randomly a choice to attack with the force or with the lightsaber for the sith
	 * object, to the target Entity E. it provides functionality to missing the target or killing the target.
	 * @Param E - the target recieving the attack.
	 */
	public void doTask(Entity E){
		int choice;
		choice = (int)(Math.random()*2+1);//sith choice
		switch(choice){
		case 1://attacking with the lightsaber
			attack(E);
			break;
		case 2://attacking with the force
			useForce(E); // function overriding the HasForce funtion and allowing the sith to use force attacks.
			break;
		}
		if(E.getHp() <= 0){//checks to see if the character died and sets them inactive if so
			System.out.println(E.getName()+" has been taken down!");
			sayCphrase();
			E.setInactive();
		}
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
		this.modifyHealth(health, 1);//sets the final health
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
			if(E instanceof Jedi){
				Jedi tC = (Jedi)E;
				System.out.println("but "+E.getName()+" has blocked the attack with his"+tC.sColor()+" lightsaber");
				tC.sayCphrase();
			}else{
				System.out.println("But "+E.getName()+" moves out of the way and avoids the attack.");
				if(E instanceof Rebel)
					((Rebel)E).sayCphrase();
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
			if(E instanceof Jedi){
				System.out.println("but "+E.getName()+" has evaded "+forcePower+" with force leap.");
				((Jedi)E).sayCphrase();
			}else{
				System.out.println("but "+E.getName()+" rushes behind cover and evades "+forcePower+".");
				if(E instanceof Rebel)
					((Rebel)E).sayCphrase();
			}
		}else{
			System.out.println(forcePower+" voilently strikes "+E.getName()+" for "+hitPower+" damage.");
			E.modifyHealth(hitPower, 2);
		}
	}
}
