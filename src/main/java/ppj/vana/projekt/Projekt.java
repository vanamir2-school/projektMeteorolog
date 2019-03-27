package ppj.vana.projekt;

import java.util.Arrays;
import java.util.stream.Collectors;

// ------------------------------------------------ PATH to project folder( temp personal usage )
// C:\Users\mirav\IdeaProjects\projektMeteorolog

// ------------------------------------------------ BUILD + RUN (v POMku je spusteni ihned po compile)
// mvn compile

// ------------------------------------------------ DEPLOY
// mvn deploy
// cesta k JAR (C:\Users\mirav\Desktop\TUL\Semestr 2\PPJ\SEMESTRALKA\projektVana\meteorolog)
// RUN: java -jar .\meteorolog-0.1_BETA.jar args

public class Projekt
{
    public static void main(String [] args)
    {
        System.out.println("This is my package! Those are args: " + (args != null ? String.join(", ", args) : "null") );
    }

}