# Zillow Housing Information

This code is for a series of coding challenges that follow along with the 
Treehouse course Introduction to Functional Programming.  It uses a dataset made available from [Zillow](https://www.zillow.com/research/data/).

## Basic Instructions
In [com.teamtreehouse.challenges.homes.Main](src/main/java/com/teamtreehouse/challenges/homes/Main.java),
there are *TODO*s to complete.  The program is setup so that you can run it to see the expected results.
There is an imperative version of the specific method of this challenge, and you need to write the declarative
approach.  Feel free to explore and make sure you are producing similar output.

When you are ready, run the Gradle task `treehouse > prepareSubmission` which will run tests and
zip up your code to a folder by the name of [submissions](submissions).  Upload this zip file
and when you get the tests passing.  **You got this!!!**

## Current Challenge
Hey thanks for your help composing that function!  I was able to refactor things a bit.
Can you take a look at the `main` method and see how the first one is using a hardcoded version for Argentina.

It seems like with a little work we can create a Higher Order Function that returns a 
closure that would work for any country!  I've started it at `createPriceConverter`, can you 
look at how `getArgentinaPriceConverter` is working and make createPriceConverter close over
the parameters passed in, so that we can create a dynamic function for any country please?