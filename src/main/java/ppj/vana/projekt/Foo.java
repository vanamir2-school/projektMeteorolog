package ppj.vana.projekt;


/**
 * Třída demonstruje použití profilů - je nakonfigurována ve třídě FooConfiguration.
 */
public class Foo {

    private final String text;

    public Foo(String text) {
        this.text = text;
    }

    public void makeSound() {
        System.out.println(text);
    }

}
