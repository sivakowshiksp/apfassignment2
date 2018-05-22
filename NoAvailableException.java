/**
 *
 * @author Monika Vurigity
 */
package mininet;

public class NoAvailableException extends Exception {

    public NoAvailableException() {
        super("One or more persons are already spouse of others !");
    }
}