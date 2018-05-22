/**
 *
 * @author Monika Vurigity
 */

package mininet;

import java.util.Comparator;

public class Relation {

    private String name1;
    private String name2;
    private String relationType;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
    public static Comparator<Relation> nameComparator = new Comparator<Relation>() {
        @Override
        public int compare(Relation r1, Relation r2) {
            if (r1.getName1().compareTo(r2.getName1()) == 0) {
                return r1.getName2().compareTo(r2.getName2());
            }
            return r1.getName1().compareTo(r2.getName1());
        }
    };
}