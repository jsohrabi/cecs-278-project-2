import java.io.*;

/**
 * The main class of the Pokemon MindReader game.
 * 
 * @author Jonathan Sohrabi 2018
 */
public class Main {
	/**
	 * The main method of the Pokemon MindReader program.
	 * 
	 * @param args The command line arguments passed into the program.
	 */
	public static void main( String[] args ) {
		int computerScore = 0;							//Tracks computer's score.
		int playerScore = 0;							//Tracks player's score.
		boolean gameExit = false;						//Tracks whether the player is finished playing.
		boolean roundDone = false;						//Tracks whether the round is finished.
		char computerGuess;								//Stores the computer's prediction.
		String lastString = "";							//Tracks the history of the last 4 player inputs.
		String playerChoice;							//Stores the player's attack choice.
		Computer computer = new Computer();				//New Computer object.
		File saveFile = new File( "./SaveFile.txt" );	//File object for the save file "SaveFile.txt".
		
		//If save file exists in the given filepath, ask the user if they want to load it.
		if ( saveFile.exists() ) {
			System.out.println( "Previous game data has been found. Would you like to load it? (y/n)" );
			//Load file if the user says yes.
			if ( CheckInput.getYesNo() == 1 ) {
				lastString = computer.readFile( saveFile );
				System.out.println( "The previous save file has been loaded." );
			} else {
				System.out.println( "The previous save file was not loaded." );
			}
		}
		//Loop until the user decides to quit.
		do {
			System.out.println();
			System.out.println( "Player: " + playerScore );
			System.out.println( "Computer: " + computerScore);
			System.out.println( "Trainer, choose your attack type: Fire, Water, or Grass (f/w/g). To quit, enter q." );
			//Loop until the user makes a valid input.
			do {
				computerGuess = computer.makePrediction( lastString );	//Computer makes a prediction at the start of the round based on the previous input history.
				playerChoice = CheckInput.getString();
				//Logic for if the player chose fire.
				if ( playerChoice.equalsIgnoreCase( "f" ) ) {
					//If there has been at least 4 rounds, append the user's choice to the end of the history string and drop the first character.
					if ( lastString.length() == 4 ) {
						lastString = lastString.substring( 1, 4 );
						lastString = lastString + 'f';
						computer.storePattern( lastString );	//Store the Pattern in the Computer object.
					//If there has been less than 4 rounds, append the user's choice to the end of the history string.
					} else {
						lastString = lastString + 'f';
						//If the history string is now length 4, store the Pattern in the Computer object.
						if (lastString.length() == 4)
							computer.storePattern(lastString);
					}
					//If Computer chose water, Computer wins so add 1 to Computer score.
					if ( computerGuess == 'w' ) {
						System.out.println( "You chose Fire. The computer chose Water. The computer wins." );
						computerScore++;
					}
					//If Computer chose fire, there is a tie. Nobody gets points.
					if ( computerGuess == 'f' ) {
						System.out.println( "You chose Fire. The computer chose Fire. You have tied." );
					}
					//If Computer chose grass, player wins so add 1 to player score.
					if ( computerGuess == 'g' ) {
						System.out.println( "You chose Fire. The computer chose Grass. You win!" );
						playerScore++;
					}
					roundDone = true;		//End the round.
				//Logic for if the player chose water.
				} else if ( playerChoice.equalsIgnoreCase( "w" ) ) {
					//If there has been at least 4 rounds, append the user's choice to the end of the history string and drop the first character.
					if ( lastString.length() == 4 ) {
						lastString = lastString.substring( 1, 4 );
						lastString = lastString + 'w';
						computer.storePattern( lastString );	//Store the Pattern in the Computer object.
					//If there has been less than 4 rounds, append the user's choice to the end of the history string.
					} else {
						lastString = lastString + 'w';
						//If the history string is now length 4, store the Pattern in the Computer object.
						if (lastString.length() == 4)
							computer.storePattern(lastString);
					}
					//If Computer chose water, there is a tie. Nobody gets points.
					if ( computerGuess == 'w' ) {
						System.out.println( "You chose Water. The computer chose Water. You have tied." );
					}
					//If Computer chose fire, player wins so add 1 to player score.
					if ( computerGuess == 'f' ) {
						System.out.println( "You chose Water. The computer chose Fire. You win!" );
						playerScore++;
					}
					//If Computer chose grass, Computer wins so add 1 to Computer score.
					if ( computerGuess == 'g' ) {
						System.out.println( "You chose Water. The computer chose Grass. The computer wins." );
						computerScore++;
					}
					roundDone = true;		//End the round.
				//Logic for if the player chose grass.
				} else if ( playerChoice.equalsIgnoreCase( "g" ) ) {
					//If there has been at least 4 rounds, append the user's choice to the end of the history string and drop the first character.
					if ( lastString.length() == 4 ) {
						lastString = lastString.substring( 1, 4 );
						lastString = lastString + 'g';
						computer.storePattern( lastString );	//Store the Pattern in the Computer object.
					//If there has been less than 4 rounds, append the user's choice to the end of the history string.
					} else {
						lastString = lastString + 'g';
						//If the history string is now length 4, store the Pattern in the Computer object.
						if (lastString.length() == 4)
							computer.storePattern(lastString);
					}
					//If Computer chose water, player wins so add 1 to player score.
					if ( computerGuess == 'w' ) {
						System.out.println( "You chose Grass. The computer chose Water. You win!" );
						playerScore++;
					}
					//If Computer chose fire, Computer wins so add 1 to Computer score.
					if ( computerGuess == 'f' ) {
						System.out.println( "You chose Grass. The computer chose Fire. The computer wins." );
						computerScore++;
					}
					//If Computer chose grass, there is a tie. Nobody gets points.
					if ( computerGuess == 'g' ) {
						System.out.println( "You chose Grass. The computer chose Grass. You have tied." );
					}
					roundDone = true;		//End the round.
				//Logic for if the user wants to quit.
				} else if ( playerChoice.equalsIgnoreCase( "q" ) ) {
					gameExit = true;		//Set the game exit flag to true.
					roundDone = true;		//Set the round finish flag to true.
					//Prompt user to save the game data if they would like.
					System.out.println( "Would you like to save this game's data? (y/n)" );
					if ( CheckInput.getYesNo() == 1 ) {
						computer.saveMapToFile( saveFile );
					}
				//If the input is invalid, write invalid input to console.
				} else {
					System.out.println( "Invalid input." );
				}
			} while ( !roundDone );
		} while ( !gameExit );
	}
}