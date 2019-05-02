package ppj.vana.projekt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to demonstrate profile usage. Configured in FooConfiguration.
 */
public class Foo {

    private static final Logger logger = LoggerFactory.getLogger(Foo.class);

    private final String text;

    public Foo(String text) {
        this.text = text;
    }

    public void printLog() {
        logger.info(text);
    }
}
