package ppj.vana.projekt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Třída demonstruje použití profilů - je nakonfigurována ve třídě FooConfiguration.
 */
public class Foo {

    private static final Logger logger = LoggerFactory.getLogger(Foo.class);

    private final String text;

    public Foo(String text) {
        this.text = text;
    }

    public void makeSound() {
        logger.info(text);
    }

}
