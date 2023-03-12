package org.example.Exceptions;

import javax.swing.*;
import java.io.File;

public class WrongTypeExeption extends Exception{
    public WrongTypeExeption(String message){
        System.out.println(message);
//        JOptionPane.showMessageDialog(null,message);
    }
}
