
/**
 * @author hectordelavega
 *This Class will define the very generic and abstract blueprint of what the other subclasses will be like by
 *extending this one, through Polymorphism and inheritance. This class will keep track of the name and hp of
 *each extended object. It will also hold methods that apply to the Entity subclass object.
 */
public abstract class Entity {
	
	private String name, task;
	private int hp;//hitpoints / life
	private boolean active;

	/**
	 * This is the constructor for each entity, which will be overridden, but still be called through the super() 
	 * function. It will receive the name and the hitpoints of the objects to be stored
	 * @param n
	 * @param p
	 */
	public Entity(String n, int p){
		name = n;
		hp = p;
		active = true;
	}
	/**
	 * The doTask function will define the actions of all subclasses for this superclass. It is abstract as it 
	 * is essential that it gets overridden. This will be the abstract attack function of the Person class
	 * which will branch off into different doTasks()/attacks for its subclasses; furthermore, this will serve
	 * as the doTask for the Droid class which will also branch off into different tasks according
	 * to the subclass.
	 * @param e - the target of the current Entity that will be on the receiving end of the task being done
	 */
	abstract void doTask(Entity e);
	
	/**
	 * Return the name of the entity - subclass object
	 * @return the name of the object
	 */
	public String getName(){
		return name;
	}
	/**
	 * This will modify the health of the entity, to be healed or to take damage. The choice will be
	 * decided at the call with a 1 for heal and !1 for damage;
	 * @param change - the total health after heal, or the damage that is to be taken
	 * @param choice - the choice for taking damage or healing up.
	 */
	public void modifyHealth(int change, int choice){
		if(choice == 1)
			hp = change;
		else
			hp -= change;
	}
	/**
	 * Returns the current health of the entity, does not need to be overwritten
	 * @return - the current health
	 */
	public int getHp(){
		return hp;
	}
	/**
	 * Determines whether the current entity is active or not
	 * @return - true if active, false if not
	 */
	public boolean getActive(){
		return active;
	}
	
	/**
	 * If an entity dies, this will set the hp to zero and will put "-Defeated" next to their name so the user
	 * is aware
	 */
	public void setInactive(){
		hp = 0; //used for accurate representation in main (no negative health)
		name = name + "-Defeated";
		active = false;
	}
}
