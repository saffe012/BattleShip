# BattleShip

A program written in Java that simulates the popular Naval Battle board game, 'Battleship'.

## Prerequisites

A Jave SE Development Kit must be installed to run program.

To play run 'java BattleShip' in cmd after compiling 'BattleShip.java' using javac.

## Usage

This is a text based game. All input and output is handled in the command line. 

The begin a player will be asked whether they'd like to play the game in debug more or not. Debug mode will display the board on the screen with all hits, misses, and boats on the screen. 

After debug mode is enabled or not by the user, the user is asked to specify the size of the game board from 2x2 to 10x10. Depending on the size of the board, there will be a different number of ships (1-5) placed on the board. The program will randomly place boats on the board for both the user and the opponent.

Once the board is built, a starting player will be randomly chosen. Turns will alternate after a starting player is decided. On a user's turn, the opponents game board will be displayed showing ships hit, and spaces fired at. 

The user will be prompted to type a spot to fire at. After firing at a space, the user will be notified whether they hit a ship, sunk a ship, missed, hit a spot they already shot at, or shot off the board. If they hit a spot they already shot at or shoot off the board, they lose a turn. 

On the opponent's turn, the computer will randomly choose a spot on the players board for the opponent to fire at. The user will be notified of the status of that shot.

Once all the ships are sunk, the user will be notified that they won/lost the game and they will be told how many turns it took them to finish the game. The game is now over and the program will close. 

## Authors
Matt Saffert
