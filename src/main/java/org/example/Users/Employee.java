package org.example.Users;

import org.example.Enums.RequestType;
import org.example.Exceptions.WrongTypeExeption;
import org.example.Models.Request;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.example.Utils.Methods.getAvailableRequestTypes;
import static org.example.Utils.Methods.getRequestName;

public class Employee extends User {
    private String company;

    public Employee(String name, String company) {
        super(name);
        this.company = company;
    }

    public Employee() {
        super("");
    }

    public String getCompany() {
        return company;
    }

    public Employee setCompany(String company) {
        this.company = company;
        return this;
    }

    @Override
    public String writeRequest(RequestType requestType) throws WrongTypeExeption {
        ArrayList<RequestType> availableRequestTypes = getAvailableRequestTypes(new Employee());

        if (!availableRequestTypes.contains(requestType))
            throw new WrongTypeExeption("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + getRequestName(requestType));

        return "Subsemnatul " + this.getName() + ", angajat la compania " + this.getCompany() + ", va rog sa-mi aprobati urmatoarea solicitare: " + getRequestName(requestType);
    }

    @Override
    public Request createRequest(RequestType requestType, Integer priority, LocalDateTime localDateTime, String fileName) throws IOException {

        Request request = null;
        try {
            String requestText = writeRequest(requestType);
            request = new Request(requestText, priority, requestType, localDateTime);
        } catch (WrongTypeExeption exeption) {
            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            bufferedWriter.write("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + getRequestName(requestType)+"\n");
            bufferedWriter.close();

            return null;
        }

        ArrayList<Request> requests = getPendingRequests();
        requests.add(request);
        setPendingRequests(requests);
        return request;
    }
}
