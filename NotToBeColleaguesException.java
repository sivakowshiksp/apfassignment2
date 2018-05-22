/**
 *
 * @author Monika Vurigity
 */
package mininet;

public class NotToBeColleaguesException extends Exception {

    public NotToBeColleaguesException() {
        super("A child cannot be in the colleague relation !");
    }
}