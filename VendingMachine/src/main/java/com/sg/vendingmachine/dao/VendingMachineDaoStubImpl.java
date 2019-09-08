/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import static com.sg.vendingmachine.dao.VendingMachineDaoFileImpl.DIMES_IN_DOLLARS;
import static com.sg.vendingmachine.dao.VendingMachineDaoFileImpl.NICKELS_IN_DOLLARS;
import static com.sg.vendingmachine.dao.VendingMachineDaoFileImpl.PENNIES_IN_DOLLARS;
import static com.sg.vendingmachine.dao.VendingMachineDaoFileImpl.QUARTERS_IN_DOLLARS;
import static com.sg.vendingmachine.dao.VendingMachineDaoFileImpl.REMAINDER;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Snacks;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.ui.MathOperator;
import com.sg.vendingmachine.ui.VendingMachineMath;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author mrder
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    VendingMachineMath myMath = new VendingMachineMath();
    Snacks testSnacks;
    List<Snacks> snackTestList = new ArrayList<>();
    List<BigDecimal> testBank = new ArrayList<>();
    Map<String, Integer> changeMap = new HashMap<>();
    
    public VendingMachineDaoStubImpl() {
        testSnacks = new Snacks("A1");
        testSnacks.setItem("Chips");
        testSnacks.setCost(BigDecimal.TEN);
        testSnacks.setQuantity(5);
        snackTestList.add(testSnacks);
    
        BigDecimal userMoney = new BigDecimal("1");
        testBank.add(userMoney);
    }

    @Override
    public Snacks getSnack(String location) throws NoItemInventoryException {
        if (location.equals(testSnacks.getLocation())) {
            return testSnacks;
        } else {
            return null;
        }
    }

    @Override
    public String dispenseSnack(Snacks snack, BigDecimal userMoney) throws InsufficientFundsException {
        return testSnacks.getItem();
    }

    @Override
    public List<Snacks> getAllSnacks() throws VendingMachinePersistenceException {
        return snackTestList;
    }

    @Override
    public Snacks removeSnack(String location, Snacks snack) throws VendingMachinePersistenceException {
        if (location.equals(testSnacks.getLocation())) {
            return testSnacks;
        } else {
            return null;
        }
    }

    @Override
    public void addMoney(BigDecimal userMoney) {
        testBank.add(0, userMoney);
    }

    @Override
    public BigDecimal getBalance() {
        BigDecimal currentBalance = testBank.get(0);
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
