# Nintendo High Scores Challenges

This code is for a series of coding challenges that follow along with the 
Treehouse course Introduction to Functional Programming.  It uses a dataset I found on the 
Internet for high scores reported for Nintendo games.

## Basic Instructions
In [com.teamtreehouse.challenges.highscores.Main](src/main/java/com/teamtreehouse/challenges/highscores/Main.java),
there are *TODO*s to complete.  The program is setup so that you can run it to see the expected results.
There is an imperative version of the specific method of this challenge, and you need to write the declarative
approach.  Feel free to explore and make sure you are producing similar output.

When you are ready, run the Gradle task `treehouse > prepareSubmission` which will run tests and
zip up your code to a folder by the name of [submissions](submissions).  Upload this zip file
and when you get the tests passing.  **You got this!!!**

## Current Challenge
For this challenge you will modify the method `Main.printBurgerTimeScoresDeclaratively` to 
use a stream to filter scores of a specific game, Burger Time, and then further filter 
the stream to scores greater than 20,000 points.  Finally, print out all the scores using a 
declarative `forEach`.