/**
 *
 * @author Monika Vurigity
 */
package mininet;
 
public class TooYoungException extends Exception {

    public TooYoungException() {
        super("The selected kid is too yound to make friends");
    }

}