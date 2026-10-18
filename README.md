# ECM2414 Software Development - Card Game Simulation
## Project Description 
The card game simulates the picking up and discarding of cards in a concurrent manner until a player has 4 of the same card. 
    
## Requirements 
* JDK 17 or higher (tested with JDK 25)
* JUnit 5 using the standalone jar in lib directory 
## Project Structure 
* src/ — Java source code for the application.
* Tests/ — JUnit test classes.
* Test_Input_Files/ — Input files containing card values for games.
* Output_Files/ — Generated player and deck log files.
* README.md — Project documentation
## Running the application 
from the directory containing Cards.jar run :
```
java -jar Cards.jar
```

The application prompts the user to enter:
1. The number of players 
2. The path to a valid input deck 

The path to the input deck should be relative to the folder where the Cards.jar executable is 

For example, when running from the project root directory:

```
Enter number of players: 2
Enter file path: Test_Input_Files/TwoPlayer.txt
```
## Input Files 
The input file must contain the card values for the selected number of players (e.g for two players there must be 4 1,s and 4 2's)

The input file needs to have eight times the number of players (for example two players require 16 cards)

Finally, the card values must be non-Negative and each value must have its own line (see Test_Input_Files for examples) 

## Output Files 
The application generates output files as according to the specification 

The Output files are written to "Output_Files" in the directory where cards.java is run 

1. PlayerN_output.txt - records actions done by the player N
2. DeckN_output.txt - records the final state of deck N
    
## Compiling source code
Open powershell in the root directory of the project 
```
javac -d out src\Main.java
```
This will compile main which in turn compiles all its dependencies compiling the entire project 

Alternatively if this fails each java class can be compiled using 
```
javac -d out src\className.java
```

## Compiling and running Tests
*JUnit5 requirement must be met*

Run this in a root folder terminal:
```
Run javac -d out -cp "lib\*" src\*.java Tests\*.java
```

To run all tests: 
```
java -cp "lib\*;out" org.junit.platform.console.ConsoleLauncher execute --scan-class-path
```

If using an IDE such as IntelliJ IDEA or Eclipse the tests can be run through IDE promtps.