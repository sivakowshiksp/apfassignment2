/**
 *
 * @author Monika Vurigity
 */
package mininet;

public class NoParentException extends Exception {

    public NoParentException() {
        super("A dependent cannot have no parents or only one parent !");
    }
}