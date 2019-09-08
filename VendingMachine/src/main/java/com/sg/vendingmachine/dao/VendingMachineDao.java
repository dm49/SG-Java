/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Snacks;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 *
 * @author mrder
 */
public interface VendingMachineDao {
    
    public Snacks getSnack (String location)
        throws NoItemInventoryException;
    public String dispenseSnack(Snacks snack, BigDecimal userMoney)
        throws InsufficientFundsException;
    public List<Snacks> getAllSnacks()
        throws VendingMachinePersistenceException;
    public Snacks removeSnack(String location, Snacks snack)
        throws VendingMachinePersistenceException;
    public void addMoney (BigDecimal userMoney);
    public BigDecimal getBalance();
    public Map<String, Integer> makeChange(BigDecimal userMoney);
}
