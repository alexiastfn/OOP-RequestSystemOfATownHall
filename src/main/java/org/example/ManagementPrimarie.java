package org.example;


import org.example.Models.Office;
import org.example.Users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.example.Utils.Instructions.*;
import static org.example.Utils.Methods.getResourcesPath;


public class ManagementPrimarie {
    public static ArrayList<User> users;
    public static ArrayList<Office<? extends User>> offices;


    public static void main(String[] args) throws IOException, ParseException {

        String fileName = args[0];
        BufferedReader bufferedReader = getBufferReader(fileName);
        initVariables();
        String line = "";

        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(";");
                for (int i = 1; i < words.length; i++) {
                    words[i] = words[i].substring(1);
                }
                switch (words[0]) {
                    case "adauga_utilizator":
                        addUser(words);
                        break;
                    case "cerere_noua":
                        addRequest(words, "src/main/resources/output/" + fileName);
                        break;
                    case "afiseaza_cereri_in_asteptare":
                        getPendingRequests(words, fileName);
                        break;
                    case "adauga_funcionar":
                        addClerk(words);
                        break;
                    case "retrage_cerere":
                        removeRequest(words);
                        break;
                    case "rezolva_cerere":
                        solveRequest(words);
                        break;
                    case "afiseaza_cereri_finalizate":
                        getClosedRequests(words);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}