/**
 *
 * @author Monika Vurigity
 */
package mininet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class PathNode {

    private PathNode parent;
    private Hashtable children;
    private String name;
    private String relation;

    public PathNode(PathNode parent, String name, String relation) {
        this.parent = parent;
        this.name = name;
        this.relation = relation;
        this.children = new Hashtable();
    }

    public PathNode getParent() {
        return parent;
    }

    public void setParent(PathNode parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hashtable getChildren() {
        return children;
    }

    public void setChildren(Hashtable children) {
        this.children = children;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}