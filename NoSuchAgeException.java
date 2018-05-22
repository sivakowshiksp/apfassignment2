/**
 *
 * @author Monika Vurigity
 */
package mininet;
 
public class NoSuchAgeException extends Exception {

    public NoSuchAgeException() {
        super("No such age, a valid age must be between 0 and 150 !");
    }
}