# Creating Modules

Module Tutorial
http://openjdk.java.net/projects/jigsaw/quick-start

```sh
1. 
    a. mkdir src
    b. mkdir mods

2. 
    # Create the module directory
    a. mkdir com.greetings
    # create the package directory inside the module
    b. mkdir -p com.greetings/com/greetings
    # Create the class inside the package
    c. touch com.greetings/com/greetings/Main.java
    d. Add class object
    e. compile the class
        i.  mkdir -p mods/com.greetings
        ii. javac -d mods/com.greetings src/com.greetings/module-info.java src/com.greetings/com/greetings/Main.java

    f. Run the code 
        i. java --module-path mods -m com.greetings/com.greetings.Main
```