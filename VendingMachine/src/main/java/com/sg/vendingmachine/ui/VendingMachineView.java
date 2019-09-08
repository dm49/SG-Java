/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Snacks;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author mrder
 */
public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    //Menus and Banners
    public int printMenuAndGetSelection() {
        io.print("");
        io.print("1: Insert Money");
        io.print("2: Get Snacks");
        io.print("3: Exit");
            
            return io.readInt("Select from the above choices", 1, 3);
    }
    
    public void displayInsertMoneyBanner() {
        io.print("INSERT MONEY");
    }
    
    public void displayGetSnacksBanner() {
        io.print("GET SNACKS");
    }
    
    public void displayExitBanner() {
        io.print("Enjoy your snacks!");
    }
    
    public void fundsAvailableBanner() {
        io.print("Funds Available");
    }
    
    public void changeDueBanner() {
        io.print("Change Due");
    }
    
    public void defaultBanner() {
        io.print("UNKNONW COMMAND");
    }
    
    public void endOfProgramBanner() {
        io.print("Good Bye");
    }
    
    //Functions
    public String pressEnter() {
        return io.readString("Press enter to continue");
    }
    
    public BigDecimal addFunds() {
        return io.readBigDecimal
            ("How much money would you like to insert?")
            .setScale(2, RoundingMode.HALF_UP);
    }
    
    public void displaySnacks (List<Snacks> snackList) {
        List<Snacks> sortedSnacks = snackList.stream()
                .sorted(Comparator.comparing(Snacks::getLocation))
                .collect(Collectors.toList());
                
        for (Snacks currentSnack : sortedSnacks) {
            io.print(currentSnack.getLocation() + ": "
            + currentSnack.getItem() + " $"
            + currentSnack.getCost());
        }

    }
    
    public String chooseItem() {
        String location = io.readString("Enter the location of what snack you would like.");
        return location.toUpperCase();
    }
    
    public void getItem(String item) {
            io.print(item + " has been dispensed");
    }
    
    public BigDecimal newBalance(Snacks snack, BigDecimal userMoney) {
        int adjustedQuantity = snack.getQuantity();
        adjustedQuantity -= 1;
        snack.setQuantity(adjustedQuantity);
        return userMoney.subtract(snack.getCost());
    }
    
    public void userBalance(BigDecimal userMoney) {
        io.print("$" + userMoney.toString());
    }
    
    public void viewChange (Map <String, Integer> changeDue) {
        changeDue.forEach((k,v)-> System.out.println(k + " - " + v));
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("---Error---");
        io.print(errorMsg);
    }
}
