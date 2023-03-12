package org.example.Users;

import org.example.Enums.RequestType;
import org.example.Exceptions.WrongTypeExeption;
import org.example.Models.Request;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class User {
    private String name;
    private ArrayList<Request> pendingRequests = new ArrayList<>();
    private ArrayList<Request> closedRequests = new ArrayList<>();

    public User(String name){
        this.name = name;
    }

    public abstract String writeRequest(RequestType requestType) throws WrongTypeExeption;
    public abstract Request createRequest(RequestType requestType, Integer priority, LocalDateTime localDateTime,String fileName)throws IOException;

    public void closeRequest(LocalDateTime localDateTime){
        Request currentRequest = null;
        for(Request request: this.pendingRequests){
            if (request.getDate().compareTo(localDateTime) == 0)
            {
                currentRequest = request;
                break;
            }
        }

        this.pendingRequests.remove(currentRequest);
    }

    public ArrayList<Request> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(ArrayList<Request> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public ArrayList<Request> getClosedRequests() {
        return closedRequests;
    }

    public void setClosedRequests(ArrayList<Request> closedRequests) {
        this.closedRequests = closedRequests;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
