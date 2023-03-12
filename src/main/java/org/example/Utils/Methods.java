package org.example.Utils;

import org.example.Enums.RequestType;
import org.example.Models.Office;
import org.example.Models.Request;
import org.example.Users.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static org.example.ManagementPrimarie.offices;
import static org.example.ManagementPrimarie.users;


public class Methods {
    public static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");

    public static ArrayList<RequestType> getAvailableRequestTypes(User user) {
        ArrayList<RequestType> availableTypes = new ArrayList<>();

        if (user instanceof Person) {
            availableTypes.add(RequestType.InlocuireBuletin);
            availableTypes.add(RequestType.InlocuireCarnetDeSofer);
        }

        if (user instanceof Employee) {
            availableTypes.add(RequestType.InlocuireBuletin);
            availableTypes.add(RequestType.InlocuireCarnetDeSofer);
            availableTypes.add(RequestType.InregistrareVenitSalarial);
        }

        if (user instanceof OldPerson) {
            availableTypes.add(RequestType.InlocuireBuletin);
            availableTypes.add(RequestType.InlocuireCarnetDeSofer);
            availableTypes.add(RequestType.InregistrareCupoaneDePensie);
        }

        if (user instanceof Student) {
            availableTypes.add(RequestType.InlocuireBuletin);
            availableTypes.add(RequestType.InlocuireCarnetDeElev);
        }

        if (user instanceof JuridicPerson) {
            availableTypes.add(RequestType.CreareActConstitutiv);
            availableTypes.add(RequestType.ReinnoireAutorizatie);
        }
        return availableTypes;
    }

    public static String getUserType(User user){

        if (user instanceof Person) {
            return "persoana";
        }

        if (user instanceof Employee) {
            return "angajat";
        }

        if (user instanceof OldPerson) {
            return "pensionar";
        }

        if (user instanceof Student) {
            return "elev";
        }

        if (user instanceof JuridicPerson) {
            return "entitate juridica";
        }
        return "";
    }

    public static User getUser(String[] words) {
        User user = null;
        words[1] = words[1].replace(" ", "");
        switch (words[1]) {
            case "angajat":
                user = new Employee(words[2], words[3]);
                break;
            case "elev":
                user = new Student(words[2], words[3]);
                break;
            case "pensionar":
                user = new OldPerson(words[2]);
                break;
            case "entitatejuridica":
                user = new JuridicPerson(words[2], words[3]);
                break;
            case "persoana":
                user = new Person(words[2]);
                break;
        }
        return user;
    }

    public static RequestType parseRequestType(String requestType) {
        switch (requestType) {
            case "inlocuire carnet de sofer":
                return RequestType.InlocuireCarnetDeSofer;
            case "inlocuire carnet de elev":
                return RequestType.InlocuireCarnetDeElev;
            case "creare act constitutiv":
                return RequestType.CreareActConstitutiv;
            case "reinnoire autorizatie":
                return RequestType.ReinnoireAutorizatie;
            case "inlocuire buletin":
                return RequestType.InlocuireBuletin;
            case "inregistrare venit salarial":
                return RequestType.InregistrareVenitSalarial;
            default:
                return RequestType.InregistrareCupoaneDePensie;
        }
    }

    public static String getRequestName(RequestType requestType) {
        switch (requestType) {
            case InlocuireCarnetDeSofer:
                return "inlocuire carnet de sofer";
            case InlocuireCarnetDeElev:
                return "inlocuire carnet de elev";
            case CreareActConstitutiv:
                return "creare act constitutiv";
            case ReinnoireAutorizatie:
                return "reinnoire autorizatie";
            case InlocuireBuletin:
                return "inlocuire buletin";
            case InregistrareVenitSalarial:
                return "inregistrare venit salarial";
            default:
                return "inregistrare cupoane de pensie";
        }
    }

    public static LocalDateTime parseToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        return LocalDateTime.parse(date, formatter);
    }

    public static Office getSpecificOffice(User user) {

        if (user instanceof Person) {
            return getOfficeByType("person");
        }

        if (user instanceof Employee) {
            return getOfficeByType("employee");
        }

        if (user instanceof OldPerson) {
            return getOfficeByType("oldPerson");
        }

        if (user instanceof Student) {
            return getOfficeByType("student");
        }

        if (user instanceof JuridicPerson) {
            return getOfficeByType("juridicPerson");
        }
        return null;
    }

    public static Office getOfficeByType(String type) {
        for (Office office : offices) {
            if (office.getType().equals(type)) return office;
        }
        return null;
    }

    public static User getUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }

    public static void removePendingRequest(User user, Request request) {
        ArrayList<Request> pendingRequests = user.getPendingRequests();
        pendingRequests.remove(request);
        user.setPendingRequests(pendingRequests);
    }

    public static void addClosedRequest(User user, Request request) {
        ArrayList<Request> closedRequests = user.getClosedRequests();
        closedRequests.add(request);
        user.setClosedRequests(closedRequests);
    }

    public static String getResourcesPath(){
        return System.getProperty("user.dir") + "\\src\\main\\resources";
    }
}
