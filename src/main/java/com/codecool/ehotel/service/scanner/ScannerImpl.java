package com.codecool.ehotel.service.scanner;

import java.util.Scanner;

public class ScannerImpl implements ScannerInterface {
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public String getStringInput() {
        return scanner.nextLine();
    }

    @Override
    public int getIntInput() {
        return  scanner.nextInt();
    }
}
