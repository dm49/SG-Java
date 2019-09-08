/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snacks;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mrder
 */
public interface VendingMachineServiceLayer {
    
    Snacks getSnack(String location) throws
         NoItemInventoryException;
    
    String dispenseSnack(Snacks snack, BigDecimal userMoney) throws
        InsufficientFundsException;
    
    List<Snacks> getAllSnacks() throws
        VendingMachinePersistenceException;
    
    Snacks removeSnack(String location, Snacks snack) throws
        VendingMachinePersistenceException;
    
    void addMoney(BigDecimal userMoney);
    
    BigDecimal getBalance();
    
    Map<String, Integer> makeChange(BigDecimal userMoney);
}
