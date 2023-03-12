package org.example.Models;

import org.example.Exceptions.WrongTypeExeption;
import org.example.Users.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.example.Utils.Methods.*;

public class Office <T>{
    private String type ;
    private HashMap<Request, T> requests;
    private ArrayList<PublicClerk<T>> publicClerks;

    public Office(String type){
        requests = new HashMap<>();
        publicClerks = new ArrayList<>();
        this.type = type;
    }

    public Office(HashMap<Request, T> requests, ArrayList<PublicClerk<T>> publicClerks) {
        this.requests = requests;
        this.publicClerks = publicClerks;
    }

    public void addClerk(String clerkName){
        PublicClerk<T> newClerk = new PublicClerk<T>(clerkName);
        publicClerks.add(newClerk);
    }

    public void addRequest(Request request, T user) throws WrongTypeExeption {
        if (!(user instanceof User))
            throw new WrongTypeExeption("Utilizatorul de tip "  + getUserType((User) user) + "nu poate inainta o cerere de tip "+ getRequestName(request.getType()));

        requests.put(request,user);
    }

    public void resolveRequest(String clerkName) throws IOException {
        final Integer[] maxPriority = {0};
        HashMap<Request, T> filteredRequests = new HashMap<>();

        requests.forEach((request, userType) -> {
            if (request.getPriority() > maxPriority[0])
                maxPriority[0] = request.getPriority();
        });

        requests.forEach((request,userType) ->{
            if (request.getPriority() == maxPriority[0])
                filteredRequests.put(request,userType);
        });

        AtomicReference<Request> solvedRequest = null;
        AtomicReference<User> user = null;
        LocalDateTime currentDate = LocalDateTime.now();
        filteredRequests.forEach((request,userType)->{
            if (request.getDate().compareTo(currentDate) < 0)
            {
                solvedRequest.set(request);
            }
        });

        removePendingRequest(user.get(),solvedRequest.get());
        addClosedRequest(user.get(),solvedRequest.get());


        String fileName = "functionar_"+clerkName+".txt";
        File file = new File("src/main/resources/output/"+fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
        String message = solvedRequest.get().getDate().toString() + " - " + solvedRequest.get().getText();

        bufferedWriter.write(message);
        bufferedWriter.close();
    }

    public HashMap<Request, T> getRequests() {
        return requests;
    }

    public Office<T> setRequests(HashMap<Request, T> requests) {
        this.requests = requests;
        return this;
    }

    public String getType() {
        return type;
    }

    public Office<T> setType(String type) {
        this.type = type;
        return this;
    }
}
