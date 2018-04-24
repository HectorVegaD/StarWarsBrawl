
/**
 * @author hectordelavega
 *This will be the generic interface that will be implemented to Healable targets, ie Person subclasses, allowing them to 
 *be healed by the MedicalD healing doTask() function. Also it will force the healable targets to keep track
 *of their max health in order to not be overhealed.
 */
public interface Healable {
	/**
	 * A funtion used to return the max health of any healable subclass. The Max Health data member is used for
	 * preventing overhealing to occur / not being able to select a full health character to be healed.
	 * @return - the max health of a subclass
	 */
	int maxHealth();
	
	/**
	 * Function that will be overriden by healable subclasses of Person. This will be used to provide
	 * functionality to the MedicalD heal doTask() function, allowing the Person subclasses implementing 
	 * Healable to be healed.
	 * @param Heals - the amount of healing recieved
	 */
	void heal(int Heals);
}