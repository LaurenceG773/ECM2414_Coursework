# ECM2414 Software Development - Card Game Simulation

## Requirements 
* JDK 17 or higher (tested with JDK )
* JUnit 5 using the standalone jar in lib directory 

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


## Output Files 
The application generates output files as according to the specification 

The Output files are written to "Output_Files" in the directory where cards.java is run 
    
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

Run this in the root folder terminal 
```
Run javac -d out -cp "lib\*" src\*.java Tests\*.java
```
