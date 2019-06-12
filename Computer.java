import java.io.*;
import java.util.*;

/**
 * Used to store attack patterns and make predictions.
 * 
 * @author Jonathan Sohrabi 2018
 */
public class Computer {
	/**
	 * Stores and keeps a count of all previously encountered patterns.
	 */
	private HashMap< Pattern, Integer > patterns;
	
	/**
	 * Default constructor for a Computer object.
	 * Creates a new HashMap instance.
	 */
	public Computer() {
		this.patterns = new HashMap< Pattern, Integer >();
	}
	
	/**
	 * Makes a prediction based on the current input pattern and previous input patterns.
	 * 
	 * @param p The last 4 user inputs.
	 * 
	 * @return	The predicted next input.
	 */
	public char makePrediction( String p ) {
		Random rand = new Random();
		String entryKey;
		int fireCount = 0, waterCount = 0, grassCount = 0;
		//If parameter p String is not of length 4.
		if ( p.length() != 4 ) {
			int guess = rand.nextInt( 100 );	//Get a random number to make a decision from.
			//If the random number % 3 = 0, return a fire attack.
			if ( guess % 3 == 0 ) {
				return 'f';
			//If the random number % 3 = 1, return a water attack.
			} else if ( guess % 3 == 1 ) {
				return 'w';
			//If the random number % 3 = 2, return a grass attack.
			} else {
				return 'g';
			}
		//If the parameter p is of length 4.
		} else {
			p = p.substring( 1, 4 );			//Get the last 3 characters of p.
			//Iterate through the entries in the HashMap.
			for ( Map.Entry< Pattern, Integer > entry : this.patterns.entrySet() ) {
				entryKey = entry.getKey().getPattern();
				//If the first three characters of the entry is equal to the substring of p, get the count of that specific pattern's occurrence.
				if ( p.equals( entryKey.substring( 0, 3 ) ) ) {
					if ( entryKey.substring( 3 ).equals( "f" ) ) {
						fireCount = entry.getValue();
					} else if ( entryKey.substring( 3 ).equals( "w" ) ) {
						waterCount = entry.getValue();
					} else if ( entryKey.substring( 3 ).equals( "g" ) ) {
						grassCount = entry.getValue();
					}
				}
			}
			//If fire is the most used Pattern, return water (Water beats fire).
			if ( fireCount > waterCount && fireCount > grassCount ) {			//Predict user will choose fire, so use water
				return 'w';
			//If water is the most used Pattern, return grass (Grass beats water).
			} else if ( waterCount > fireCount && waterCount > grassCount ) {	//Predict user will choose water, so use grass
				return 'g';
			//If grass is the most used Pattern, return fire (Fire beats grass).
			} else if ( grassCount > fireCount && grassCount > waterCount ) {	//Predict user will choose grass, so use fire
				return 'f';
			//If two of the counts are equal, choose an attack at random.
			} else {
				int guess = rand.nextInt( 100 );
				if ( guess % 3 == 0 ) {
					return 'f';
				} else if ( guess % 3 == 1 ) {
					return 'w';
				} else {
					return 'g';
				}
			}
		}
	}
	
	/**
	 * Stores the pattern in the patterns HashMap.
	 * If the pattern does not exist, create a new key and set its value to 1.
	 * If the pattern exists, increment its value by 1.
	 * 
	 * @param p	The pattern to check for in the patterns HashMap.
	 */
	public void storePattern( String p ) {
		int value;
		p = p.toLowerCase();			//Convert the String parameter to lowercase.
		Pattern key = new Pattern( p );	//Make a new Pattern object with the String.
		//If the Pattern is already in the HashMap, get its value. Add its value + 1 to the value integer.
		if ( this.patterns.containsKey( key ) ) {
			value = this.patterns.get( key ) + 1;
			this.patterns.put( key, value );	//Add the new KV pair to the HashMap.
		//If the Pattern is not in the HashMap, add it with a value of 1.
		} else {
			this.patterns.put( key, 1 );
		}
	}
	
	/**
	 * Writes the keys and their corresponding values of the pattern HashMap to a textfile in plaintext.
	 * 
	 * @param f	The File object to use for writing.
	 */
	public void saveMapToFile( File f ) {
		//Exception check for a FileNotFound exception.
		try {
			PrintWriter pw = new PrintWriter( f );	//Create a new PrintWriter object with the File parameter.
			//For every entry in the HashMap, write the key-value pairs to the file as plaintext.
			for ( Map.Entry< Pattern, Integer > entry : this.patterns.entrySet() ) {
				pw.println( entry.getKey().getPattern() + "," + entry.getValue() );
			}
			pw.close();		//Close the PrintWriter object.
		//If an exception occurs, say there was an error in writing to the file.
		} catch ( FileNotFoundException e ) {
			System.out.println( "Error writing to text file" );
		}
	}
	
	/**
	 * Reads in HashMap keys and values in plaintext from a textfile and populates the patterns HashMap with those values.
	 * 
	 * @param f	The File object to read HashMap data from.
	 * @return	The most frequently used String pattern (i.e. The key with the greatest value).
	 */
	public String readFile( File f ) {
		Scanner read = null;
		String highestKey = "ffff";			//Set highestKey to "ffff" as a failsafe.
		
		//Exception check for FileNotFound exception.
		try {
			String line;
			String[] lineVals;
			read = new Scanner( f );		//Create a new Scanner object to read from the file.
			int highestValue =  0;
			
			//Loop while there is unread data in the textfile.
			while ( read.hasNext() ) {
				line = read.nextLine();
				//If the line is not null.
				if ( line != null ) {
					lineVals = line.split( "," );		//Split the line into an array at every comma
					this.patterns.put( new Pattern( lineVals[0] ), Integer.parseInt( lineVals[1] ) );	//Add the key-value pair from the textfile into the HashMap.
					//If the line's value is higher than the ones that have been processed, set its key as the most occurred key.
					if ( Integer.parseInt( lineVals[ 1 ] ) > highestValue ) {
						highestKey = lineVals[ 0 ];
						highestValue = Integer.parseInt( lineVals[ 1 ] );
					}
				}
			}
			read.close();		//Close the Scanner object.
		//If an exception occurred, write an error to the console.
		} catch ( FileNotFoundException e ) {
			System.out.println( "Error occured while loading the save file." );
		}
		
		return highestKey;		//Return the most occurred key.
	}
}