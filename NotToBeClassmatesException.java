/**
 *
 * @author Monika Vurigity
 */
package mininet;

public class NotToBeClassmatesException extends Exception {

    public NotToBeClassmatesException() {
        super("This child is too yound "
                + "to be classmates of others !");
    }
}