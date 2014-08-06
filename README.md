# Hangman

This is my implementation of Evil Hangman, a CS project I had early on in my classes, except using Java and with a working GUI. After players correctly guess a single letter, the program will actively change the secret word to frustrate the user so long as another suitable word still exists.

## Compiling

Compilation is handled through [Apache Ant](http://ant.apache.org/). From the root directory, the following commands can be used:

+ `ant init` will construct a `build` directory at the root level.
+ `ant compile` will compile the source code to the `build` directory.
+ `ant jar` will create the directories `dist` and `dist/lib`, and thenbundle the class files into an executable jar file in `dist/lib`.
+ `ant run-jar` will bundle the jar file and then execute it.
+ `ant bundle-app` will create an OSX executable .app file in the `dist` directory.
+ `ant run-app` will create the executable .app file and run it.
+ `ant clean` will erase the `build` and `dist` directories.

All icons made by Vlad Marin (https://www.iconfinder.com/iconsets/brain-games)