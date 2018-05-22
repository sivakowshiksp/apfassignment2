/**
 *
 * @author Monika Vurigity
 */
package mininet;

public class NotToBeCoupledException extends Exception {

    public NotToBeCoupledException() {
        super("At leaste one person is not adult,"
                + " failed to coupling them !");
    }
}