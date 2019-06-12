/**
 * Used to store the user's input history as the keys for a Map.
 * 
 * @author Jonathan Sohrabi 2018
 */
public class Pattern {
	/**
	 * The pattern of user inputs.
	 */
	private String pattern;
	
	/**
	 * Parameterized constructor for the Pattern class.
	 * Sets the pattern field to the String parameter.
	 * 
	 * @param p	The String to set the pattern to.
	 */
	public Pattern( String p ) {
		this.pattern = p;		//Set this.pattern String to parameterized String in constructor.
	}
	
	/**
	 * Getter for the pattern field.
	 * 
	 * @return	The class' pattern field.
	 */
	public String getPattern() {
		return this.pattern;	//Get this.pattern String.
	}
	
	/**
	 * Calculates the Hash Code of the String pattern.
	 */
	@Override
	public int hashCode() {
		return this.pattern.hashCode();		//Return the Hash Code of this.pattern String.
	}
	
	/**
	 * Checks if this Pattern object is equal to another object, passed as the parameter.
	 * 
	 * @param o	The object to check equality against.
	 */
	@Override
	public boolean equals( Object o ) {
		//If the parameterized object is a Pattern instance.
		if ( o instanceof Pattern ) {
			//Check if the pattern Strings of both objects are equal. If they are, return true. Else, return false.
			if ( ( ( Pattern ) o ).getPattern().equals( this.getPattern() ) )
				return true;
			else
				return false;
		}
		//If the parameterized object is not a Pattern instance, return false.
		else
			return false;
	}
}