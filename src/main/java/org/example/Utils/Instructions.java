package org.example.Utils;

import org.example.Enums.RequestType;
import org.example.Exceptions.WrongTypeExeption;
import org.example.Models.Office;
import org.example.Models.Request;
import org.example.Users.*;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static org.example.ManagementPrimarie.offices;
import static org.example.ManagementPrimarie.users;
import static org.example.Utils.Methods.*;

public class Instructions {

    public static BufferedReader getBufferReader(String fileName) throws FileNotFoundException {

        fileName = "src/main/resources/input/"  + fileName;

        File file = new File(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader;
    }

    public static void initVariables() {
        offices = new ArrayList<>();
        users = new ArrayList<>();
        Office<User> userOffice = new Office<User>("user");
        Office<OldPerson> oldPersonOffice = new Office<OldPerson>("oldPerson");
        Office<Employee> employeeOffice = new Office<Employee>("employee");
        Office<Student> studentOffice = new Office<Student>("student");
        Office<JuridicPerson> juridicPersonOffice = new Office<JuridicPerson>("juridicPerson");
        Office<Person> personOffice = new Office<Person>("person");

        offices.add(userOffice);
        offices.add(oldPersonOffice);
        offices.add(employeeOffice);
        offices.add(studentOffice);
        offices.add(juridicPersonOffice);
        offices.add(personOffice);
    }

    public static void addUser(String[] words) {
        User user = getUser(words);
        users.add(user);
    }

    public static void addRequest(String[] words, String fileName) throws IOException {
        User currentUser = getUserByName(words[1]);
        RequestType requestType = parseRequestType(words[2]);

        Office office = getSpecificOffice(currentUser);

        try{
            Request request = currentUser.createRequest(requestType, Integer.parseInt(words[4]), parseToLocalDateTime(words[3]), fileName);
            if (request!=null)
                office.addRequest(request, currentUser);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void getPendingRequests(String[] words, String fileName) throws IOException {
        User currentUser = getUserByName(words[1]);
        File file = new File( "src/main/resources/output/" + fileName );

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
        bufferedWriter.write(words[1]+ " - "+"cereri in asteptare:\n");
        String line = "";

        ArrayList<Request> pendingRequests = currentUser.getPendingRequests();
        Collections.sort(pendingRequests, new Comparator<Request>() {
            @Override
            public int compare(Request o1, Request o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        for (Request request : pendingRequests) {
            line = request.getDate().format(format) + " - " + request.getText() + "\n";
            bufferedWriter.write(line);
        }
        bufferedWriter.close();
    }

    public static void addClerk(String[] words) {
        Office office = getOfficeByType(words[1]);
        office.addClerk(words[2]);
    }

    public static void removeRequest(String[] words) {
        User user = getUserByName(words[1]);
        user.closeRequest(parseToLocalDateTime(words[2]));
    }

    public static void solveRequest(String[] words) {
        Office office = getOfficeByType(words[1]);

        try {
            office.resolveRequest(words[2]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getClosedRequests(String[] words) throws IOException {
        User currentUser = getUserByName(words[1]);
        File file = new File("src/main/resources/output/" + words[1] + ".txt");

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        String line = "";
        for (Request request : currentUser.getClosedRequests()) {
            line = request.getDate() + " - " + request.getText() + "\n";
            bufferedWriter.write(line);
        }
        bufferedWriter.close();
    }
}
