/**
 *
 * @author Monika Vurigity
 */
package mininet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class FileOperation {

    public static int AGE = 16;

    public static List<Person> readPeople() throws Exception {
        String message = "";

        String line;
        String args[];
        List<Person> people = new ArrayList();

        File f = new File("people.txt");

//            if (!f.exists() || f.isDirectory())
//                f.createNewFile();
        BufferedReader inputStream
                = new BufferedReader(new FileReader("people.txt"));

        line = "";

        while (true) {
            line = inputStream.readLine();

            if (line == null) {
                break;
            }
            args = line.split(",");

            if (Integer.parseInt(args[4]) > AGE) {

                people.add(new Adult(args[0], args[1], args[2],
                        args[3].charAt(0), Integer.parseInt(args[4]), args[5]));
            } else {
                people.add(new Dependent(args[0], args[1], args[2],
                        args[3].charAt(0), Integer.parseInt(args[4]), args[5]));
            }
        }
        //release the resource
        inputStream.close();

        return people;
    }

    public static List<Relation> readRelations() throws Exception {
        String message = "";

        List<Relation> relations = new ArrayList();
        String line;
        String args[];

        File f = new File("relations.txt");

        BufferedReader inputStream
                = new BufferedReader(new FileReader("relations.txt"));

        line = "";

        while (true) {
            line = inputStream.readLine();

            if (line == null) {
                break;
            }
            args = line.split(",");

            Relation relation = new Relation();

            relation.setName1(args[0]);
            relation.setName2(args[1]);
            relation.setRelationType(args[2]);

            relations.add(relation);
        }
        //release the resource
        inputStream.close();
        return relations;
    }

    public static void writePeopleToFile(List<Person> people) throws IOException {
        String message = "";

        PrintWriter outputStream = null;

        File f = new File("people.txt");

        outputStream
                = new PrintWriter(new FileOutputStream("people.txt"));

        for (int i = 0; i < people.size(); i++) {
            String personInfo = "";
            Person p = people.get(i);
            personInfo = p.getName() + "," + p.getPhotoPath() + ","
                    + p.getStatus() + "," + p.getGender() + "," + p.getAge()
                    + "," + p.getState();

            outputStream.println(personInfo);
        }
        //release resource
        outputStream.close();
    }

    public static void writeRelationsToFile(List<Relation> relations) throws IOException {
        String message = "";

        PrintWriter outputStream = null;

        File f = new File("relations.txt");

        outputStream
                = new PrintWriter(new FileOutputStream("relations.txt"));

        for (int i = 0; i < relations.size(); i++) {
            String relation = "";
            Relation r = relations.get(i);
            relation = r.getName1() + "," + r.getName2() + "," + r.getRelationType();

            outputStream.println(relation);
        }
        //release the resource
        outputStream.close();
    }

    /**
     *
     * @param name
     * @param people
     * @return a particular user existed in the ArrayList people
     *
     */
    private static Person getPersonByName(String name, List people) {
        for (Object o : people) {
            Person p = (Person) o;
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
}