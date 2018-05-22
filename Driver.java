/**
 *
 * @author Monika Vurigity
 */
package mininet;

import gui.MiniNet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Driver {

    private List<Person> theMiniNet;
    private List<Relation> relations;
    public DBConnect dbTest;

    public Driver() throws Exception {
        try {
            dbTest = new DBConnect();
            dbTest.connect();
            theMiniNet = dbTest.readFromDB();
        } catch (Exception e) {
            //System.out.println(e);
            theMiniNet = FileOperation.readPeople();
        }
        relations = FileOperation.readRelations();
    }

    public List<Relation> getRelations() {
        Collections.sort(relations, Relation.nameComparator);
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    //Store relations
    public String addRelations(Relation r) {
        String message = "";

        if (r.getRelationType().equals("Friend")) {
            try {
                message = makeFriends(r.getName1(), r.getName2());
            } catch (NotToBeFriendsException ntbfe) {
                message = ntbfe.getMessage();
            }
        }

        if (r.getRelationType().equals("Parent")) {
            try {
                message = setParents(r.getName1(), r.getName2());
            } catch (NoParentException npe) {
                message = npe.getMessage();
            }

        }

        if (r.getRelationType().equals("Couple")) {
            try {
                message = setSpouse(r.getName1(), r.getName2());
            } catch (NoAvailableException nae) {
                message = nae.getMessage();
            } catch (NotToBeCoupledException ntbe) {
                message = ntbe.getMessage();
            }
        }

        if (r.getRelationType().equals("Classmate")) {
            try {
                message = setClassmate(r.getName1(), r.getName2());
            } catch (NotToBeClassmatesException nbce) {
                message = nbce.getMessage();
            }
        }

        if (r.getRelationType().equals("Colleague")) {
            try {
                message = setColleague(r.getName1(), r.getName2());
            } catch (NotToBeColleaguesException nbcle) {
                message = nbcle.getMessage();
            }
        }
        return message;
    }

    public List<Person> getTheMiniNet() {
        return theMiniNet;
    }

    public void setTheMiniNet(List<Person> theMiniNet) {
        Collections.sort(theMiniNet, Person.nameComparator);
        this.theMiniNet = theMiniNet;
    }

    public String makeFriends(String name1, String name2)
            throws NotToBeFriendsException {
        String message = "";

        if (getPersonByName(name1).getClass()
                != getPersonByName(name2).getClass()) {
            throw new NotToBeFriendsException();

        } else {
            if (getPersonByName(name1) instanceof Dependent
                    && Math.abs(getPersonByName(name1).getAge()
                            - getPersonByName(name2).getAge()) > 3) {
                throw new NotToBeFriendsException();
            } else {
                if (isFriend(name1, name2)) {
                    message = "They are already friends !";
                } else {
                    Relation r = new Relation();

                    r.setName1(compareName(name1, name2, true));
                    r.setName2(compareName(name1, name2, false));
                    r.setRelationType("Friend");

                    this.relations.add(r);

                    message = "Successfully make them friends !";
                }

            }
        }
        return message;
    }

    public String setClassmate(String name1, String name2)
            throws NotToBeClassmatesException {
        String message = "";

        if (getPersonByName(name1).getAge() <= 2
                || getPersonByName(name2).getAge() <= 2) {
            throw new NotToBeClassmatesException();
        } else {
            if (isClassmate(name1, name2)) {
                message = "They are already classmates !";
            } else {
                Relation r = new Relation();
                r.setName1(compareName(name1, name2, true));
                r.setName2(compareName(name1, name2, false));
                r.setRelationType("Classmate");

                this.relations.add(r);
                Collections.sort(relations, Relation.nameComparator);

                message = "Successfully set each other as classmate !";
            }
        }
        return message;
    }

    public String setColleague(String name1, String name2)
            throws NotToBeColleaguesException {
        String message = "";

        if ((getPersonByName(name1).getClass()
                != getPersonByName(name2).getClass())
                || (getPersonByName(name1) instanceof Dependent
                && getPersonByName(name2) instanceof Dependent)) {
            throw new NotToBeColleaguesException();
        } else {
            if (isColleague(name1, name2)) {
                message = "They are already colleagues !";
            } else {
                Relation r = new Relation();
                r.setName1(compareName(name1, name2, true));
                r.setName2(compareName(name1, name2, false));
                r.setRelationType("Colleague");

                this.relations.add(r);

                message = "Successfully set each other as colleague !";
            }
        }

        return message;
    }

    private String compareName(String name1, String name2, boolean b) {
        if (b) {
            return name1.compareTo(name2) <= 0 ? name1 : name2;
        } else {
            return name1.compareTo(name2) > 0 ? name1 : name2;
        }
    }

    /*
    As stated in the specification,
    all couples are exclusive to other couples,
    hence before coupling two particular adults,
    their spouse name will be checked, two particular adults can only
    become spouses when they are both not in a couple relationship with others, and
    we assume that no homosexual couples in our case.
    
    Plus, dependents are not eligible for becoming couples.
     */
    public String setSpouse(String name1, String name2)
            throws NoAvailableException, NotToBeCoupledException {
        String message = "";

        if (getPersonByName(name1).getClass()
                != getPersonByName(name2).getClass()) {
            throw new NotToBeCoupledException();
        } else if (getPersonByName(name1) instanceof Adult
                && getPersonByName(name2) instanceof Adult) {
            if (findSpouse(name1).equals(name2)) {
                message = "The selected two persons are already a couple !";
            } else if (!findSpouse(name1).equals("") || !findSpouse(name2).equals("")) {
                throw new NoAvailableException();
            } else {
                if (getPersonByName(name1).getGender()
                        == getPersonByName(name2).getGender()) {
                    message = "Only people of different genders can be couple !";
                } else {
                    Relation r = new Relation();
                    r.setName1(compareName(name1, name2, true));
                    r.setName2(compareName(name1, name2, false));
                    r.setRelationType("Couple");

                    this.relations.add(r);

                    message = "Successfully set each other as spouse";
                }

            }
        }
        return message;
    }

    public String setParents(String name1, String name2)
            throws NoParentException {
        String message = "";

        if ((getPersonByName(name1).getClass()
                == getPersonByName(name2).getClass())) {
            message = "The same type of person cannot"
                    + " be in parent-child relation !";
        } else if (getPersonByName(name1) instanceof Adult) {
            if (findSpouse(name1).equals("")) {
                throw new NoParentException();
            } else {
                if (isParent(name1, name2)) {
                    message = "They are already in a"
                            + " parent-child relationship !";
                } else {
                    Relation r = new Relation();
                    r.setName1(compareName(name1, name2, true));
                    r.setName2(compareName(name1, name2, false));
                    r.setRelationType("Parent");

                    //Avoid recursivelly call
                    this.relations.add(r);

                    Relation r1 = new Relation();
                    name1 = findSpouse(name1);
                    r1.setName1(compareName(name1, name2, true));
                    r1.setName2(compareName(name1, name2, false));
                    r1.setRelationType("Parent");

                    this.relations.add(r1);

                    message = "Successfully set them as parent-child !";
                }
            }
        } else if (getPersonByName(name2) instanceof Adult) {
            if (findSpouse(name2).equals("")) {
                throw new NoParentException();
            } else {
                if (isParent(name2, name1)) {
                    message = "They are already in a"
                            + " parent-child relationship !";
                } else {
                    Relation r = new Relation();
                    r.setName1(compareName(name1, name2, true));
                    r.setName2(compareName(name1, name2, false));
                    r.setRelationType("Parent");

                    //Avoid recursivelly call
                    this.relations.add(r);

                    Relation r1 = new Relation();
                    name2 = findSpouse(name2);
                    r1.setName1(compareName(name1, name2, true));
                    r1.setName2(compareName(name1, name2, false));
                    r1.setRelationType("Parent");

                    this.relations.add(r1);

                    message = "Successfully set them as parent-child !";
                }
            }
        }
        return message;
    }

    public boolean isClassmate(String name1, String name2) {
        List<Relation> relations = MiniNet.driver.getRelations();

        for (int i = 0; i < relations.size(); i++) {
            Relation r = relations.get(i);

            if (r.getRelationType().equals("Classmate")) {
                if ((r.getName1().equals(name1) && r.getName2().equals(name2))
                        || (r.getName2().equals(name1) && r.getName1().equals(name2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isColleague(String name1, String name2) {
        List<Relation> relations = MiniNet.driver.getRelations();

        for (int i = 0; i < relations.size(); i++) {
            Relation r = relations.get(i);

            if (r.getRelationType().equals("Colleague")) {
                if ((r.getName1().equals(name1) && r.getName2().equals(name2))
                        || (r.getName2().equals(name1) && r.getName1().equals(name2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFriend(String name1, String name2) {
        List<Relation> relations = MiniNet.driver.getRelations();

        for (int i = 0; i < relations.size(); i++) {
            Relation r = relations.get(i);

            if (r.getRelationType().equals("Friend")) {
                if ((r.getName1().equals(name1) && r.getName2().equals(name2))
                        || (r.getName2().equals(name1) && r.getName1().equals(name2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isParent(String name1, String name2) {
        List<Relation> relations = MiniNet.driver.getRelations();

        for (int i = 0; i < relations.size(); i++) {
            Relation r = relations.get(i);

            if (r.getRelationType().equals("Parent")) {
                if ((r.getName1().equals(name1) && r.getName2().equals(name2))
                        || (r.getName2().equals(name1) && r.getName1().equals(name2))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String findSpouse(String name) {
        List<Relation> relations = MiniNet.driver.getRelations();

        for (int i = 0; i < relations.size(); i++) {
            Relation r = relations.get(i);

            if (r.getRelationType().equals("Couple")) {
                if (name.equals(r.getName1())) {
                    return r.getName2();
                } else if (name.equals(r.getName2())) {
                    return r.getName1();
                }
            }
        }
        return "";
    }

    public String addDependent(String name, String photoPath, String status,
            char gender, int age, String state,
            String fatherName, String motherName) throws NoParentException {
        boolean isFatherExisted;
        boolean isMotherExisted;
        String message = "";

        isFatherExisted = isPersonExisted(fatherName);
        isMotherExisted = isPersonExisted(motherName);

        if (isFatherExisted && isMotherExisted) {
            Person p1 = getPersonByName(fatherName);
            Person p2 = getPersonByName(motherName);

            if (findSpouse(p1.getName()).equals(p2.getName())) {
                Dependent kid = new Dependent(name, photoPath, status, gender, age, state);

                theMiniNet.add(kid);

                Collections.sort(theMiniNet, Person.nameComparator);

                setParents(p1.getName(), name);

                message = "This dependent is successfully added !";
            } else {
                throw new NoParentException();
            }
        } else {
            throw new NoParentException();
        }
        return message;
    }

    public String addAdult(String name, String photoPath, String status,
            char gender, int age, String state) {
        String message = "";

        Person adult = new Adult(name, photoPath, status, gender, age, state);

        theMiniNet.add(adult);
        Collections.sort(theMiniNet, Person.nameComparator);
        message = "This adult is successfully added !";

        return message;
    }

    public boolean isPersonExisted(String name) {
        for (Person p : theMiniNet) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Person getPersonByName(String name) {
        for (Person p : theMiniNet) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
}