/**
 *
 * @author Monika Vurigity
 */
package mininet;

public class RelationPath {

    private String path = "";
    private int length = 0;

    public RelationPath(String path) {
        this.path = path;
        this.length = path.split("<").length;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
