/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Snacks;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.ui.MathOperator;
import com.sg.vendingmachine.ui.VendingMachineMath;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;



/**
 *
 * @author mrder
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    
    VendingMachineMath myMath = new VendingMachineMath();
    
    public static final BigDecimal PENNIES_IN_DOLLARS = new BigDecimal ("100");
    public static final BigDecimal QUARTERS_IN_DOLLARS = new BigDecimal ("25");
    public static final BigDecimal DIMES_IN_DOLLARS = new BigDecimal ("10");
    public static final BigDecimal NICKELS_IN_DOLLARS = new BigDecimal ("5");
    public static final BigDecimal REMAINDER = new BigDecimal ("1");
    public static final BigDecimal NO_FUNDS = new BigDecimal ("0");
    public static final String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";
    
    private Map<String, Snacks> snacksMap = new HashMap<>();
    private List<BigDecimal> bank = new ArrayList<>();
    private Map<String, Integer> changeMap = new HashMap<>();
   
    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                new BufferedReader(
                    new FileReader(INVENTORY_FILE)));
            } catch (FileNotFoundException e) {
                throw new VendingMachinePersistenceException(
                "Could not load inventory data into memory.", e);
            }
        String currentLine;
        String[] currentTokens;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Snacks currentSnacks = new Snacks(currentTokens[0]);
            currentSnacks.setItem(currentTokens[1]);
            BigDecimal cost = new BigDecimal(currentTokens[2])
                    .setScale(2, RoundingMode.HALF_UP);
            currentSnacks.setCost(cost);
            int quantity = Integer.parseInt(currentTokens[3]);
            currentSnacks.setQuantity(quantity);
            snacksMap.put(currentSnacks.getLocation(), currentSnacks);
        }
        scanner.close();
    }
    
    public void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException (
            "Could not save inventory data.", e);
        }
        List<Snacks> snacksList = this.getAllSnacks();
        for (Snacks currentSnacks : snacksList) {
            out.println(currentSnacks.getLocation() + DELIMITER
            + currentSnacks.getItem() + DELIMITER
            + currentSnacks.getCost() + DELIMITER
            + currentSnacks.getQuantity());
            out.flush();
        }
        out.close();
    }
   
    @Override
    public Snacks getSnack(String location) throws
        NoItemInventoryException {
        
        return snacksMap.get(location);
    }
    
    @Override
    public String dispenseSnack(Snacks snack, BigDecimal userMoney)
        throws InsufficientFundsException {
        
        return snack.getItem();
    }

    @Override
    public List<Snacks> getAllSnacks() 
            throws VendingMachinePersistenceException {
        loadInventory();
        return snacksMap.values()
            .stream()
            .filter(s -> s.getQuantity() > 0)
            .collect(Collectors.toList());
    }

    @Override
    public Snacks removeSnack(String location, Snacks snack) 
            throws VendingMachinePersistenceException {
        Snacks adjustedSnack = snacksMap.put(location, snack);
        writeInventory();
        return adjustedSnack;
    }

    @Override
    public void addMoney(BigDecimal userMoney) {
        bank.add(0, userMoney);
    }

    @Override
    public BigDecimal getBalance() {
        boolean checkBalance = bank.isEmpty();
        if(checkBalance == true) {
           bank.add(0, NO_FUNDS); }
        BigDecimal currentBalance = bank.get(0);
        return currentBalance;
    }
  
    @Override
    public Map<String, Integer> makeChange(BigDecimal userMoney) {
        BigDecimal totalPennies = myMath.calculate(MathOperator.MULTIPLY, userMoney, PENNIES_IN_DOLLARS);
        
        BigDecimal ones = myMath.calculate(MathOperator.DIVIDE, totalPennies, PENNIES_IN_DOLLARS);
        totalPennies = myMath.calculate(MathOperator.MAKE_CHANGE, totalPennies, PENNIES_IN_DOLLARS);
        
        BigDecimal quarterCents = myMath.calculate(MathOperator.DIVIDE, totalPennies, QUARTERS_IN_DOLLARS);
        totalPennies = myMath.calculate(MathOperator.MAKE_CHANGE, totalPennies, QUARTERS_IN_DOLLARS);
        
        BigDecimal tenCents = myMath.calculate(MathOperator.DIVIDE, totalPennies, DIMES_IN_DOLLARS);
        totalPennies = myMath.calculate(MathOperator.MAKE_CHANGE, totalPennies, DIMES_IN_DOLLARS);
        
        BigDecimal fiveCents = myMath.calculate(MathOperator.DIVIDE, totalPennies, NICKELS_IN_DOLLARS);
        totalPennies = myMath.calculate(MathOperator.MAKE_CHANGE, totalPennies, NICKELS_IN_DOLLARS);
        
        BigDecimal oneCents = myMath.calculate(MathOperator.DIVIDE, totalPennies, REMAINDER);
        
        Change newChange = new Change();
        newChange.setDollars(ones.intValue());
        newChange.setQuarters(quarterCents.intValue());
        newChange.setDimes(tenCents.intValue());
        newChange.setNickels(fiveCents.intValue());
        newChange.setPennies(oneCents.intValue());
        
        changeMap.put("Dollars", newChange.getDollars());
        changeMap.put("Quarters", newChange.getQuarters());
        changeMap.put("Dimes", newChange.getDimes());
        changeMap.put("Nickels", newChange.getNickels());
        changeMap.put("Pennies", newChange.getPennies());
        
        Map<String, Integer> filteredChange = changeMap.entrySet()
            .stream()
            .filter(s -> s.getValue() > 0)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return filteredChange;
    }
 
}
