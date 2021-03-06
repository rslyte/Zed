Zed Version 0.1
===============
Authors:
Adam Bennett - adam.m.bennett@wsu.edu
Richard Barella - richard.t.barella@email.wsu.edu
Ryan Slyter - rabemensch@gmail.com

Description:
    Zed is a zelda clone game where the player explores an island while fighting his way through various creatures. The current prototype has implemented  character movement, attack animation, a default tile set, and the basic HUD (Heads-up Display) which contains the players health/life displayed as hearts.  Currently other elements like enemies and their AI, collision, and menus to to access save, resume, and quit features have not been implemented.

Note: Zombies take 3 hits to kill.
      Press esc. key to go to menu.
      Press space bar to attack
      Use arrow keys or wasd keys to move
      Click on menu item choices

Installation (for Linux):
1. Extract tar.gz file containing project
2. Navigate to project directory inside of the terminal where the Zed.jar file is located.
3a. Run this command inside of the terminal for linux or mac: (although we tested it, if this command doesn't work, e-mail us)
	Linux 32 bit: java -jar ZedLinux32.jar
	Linux 64 bit: java -jar ZedLinux64.jar
	MacOSX: java -jar ZedMacOSX.jar
3b. Run this for windows
	Windows 32 bit: Run ZedWindows32.exe
	Windows 64 bit: Run ZedWindows64.exe
4. Java Code is located in Zed-Eclipse/src/zed

Run GCharacter unit tests inside of linux
1. Run this command inside of the terminal:
	java -jar -Djava.library.path=lib/natives/linux Zed_GCharacter.jar

Run File_Manager unit tests inside of linux
1. Run this command inside of the terminal:
	java -jar -Djava.library.path=lib/natives/linux Zed_File_Manager.jar
