# Game of Life

The Game of Life is not your typical computer game. It is a 'cellular automaton', and was invented by Cambridge mathematician John Conway.

This game became widely known when it was mentioned in an article published by Scientific American in 1970. It consists of a collection of cells which, based on a few mathematical rules, can live, die or multiply. Depending on the initial conditions, the cells form various patterns throughout the course of the game. 

See [Game of Life](http://www.bitstorm.org/gameoflife/) to see the applet in action.


## Getting started

This project originally built by **Ant** (inferred from build.xml).

Our team doesn't have an exeriance of using Ant without Eclipse. 
But, one of member know hot to convert this Ant project into Gradle project.

For a convenience  of original project author, build.xml and project's file structure  are reserved.
So that the author can easily try our code when we create PR.(maybe)

```bash
# clone this repo first
git clone http://~

# change your working directroy into repo.
cd foo

# We don't know how to use 'ant' build system, So here explains Gradle way only.

#  Because, Most CAU students use Windows as primary OS, let's explain Windows way First
# run on Windows
gradlew run
# unittest on Windows
gradlew test


# Mac OSX & Linux
./gradlew run

./gradlew test

# Please note that dot and slash in front of the command line are added.

```