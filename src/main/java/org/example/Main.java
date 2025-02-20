package org.example;

import org.example.service.MainService;

import java.io.IOException;

public class Main {
    private static MainService mainService = new MainService();

    public static void main(String[] args) throws Exception {
        mainService.showMenu();
    }
}