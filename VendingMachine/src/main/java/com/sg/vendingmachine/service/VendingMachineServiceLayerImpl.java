/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snacks;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mrder
 */
public class VendingMachineServiceLayerImpl implements 
        VendingMachineServiceLayer {

    VendingMachineDao dao;
    
    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;
    }
    
    @Override
    public Snacks getSnack(String location) 
       throws NoItemInventoryException {
            Snacks currentSnacks = dao.getSnack(location);
            if (currentSnacks == null) {
                throw new NoItemInventoryException(
                        "ERROR: That item does not exist");
            }
            return dao.getSnack(location);
    }
    
    @Override
    public String dispenseSnack(Snacks snack, BigDecimal userMoney) 
        throws InsufficientFundsException {
        
            validateFunds(snack, userMoney);
            return dao.dispenseSnack(snack, userMoney);        
    }

    @Override
    public List<Snacks> getAllSnacks() throws 
        VendingMachinePersistenceException {
        
            return dao.getAllSnacks();
    }

    @Override
    public Snacks removeSnack(String location, Snacks snack) throws 
        VendingMachinePersistenceException {
        
            return dao.removeSnack(location, snack);
    }

    @Override
    public void addMoney(BigDecimal userMoney) {
        dao.addMoney(userMoney);
    }

    @Override
    public BigDecimal getBalance() {
        return dao.getBalance();
    }

    @Override
    public Map<String, Integer> makeChange(BigDecimal userMoney) {
        return dao.makeChange(userMoney);
    }
    
    private void validateFunds(Snacks snack, BigDecimal userMoney) throws
        InsufficientFundsException {
        
        BigDecimal costCheck = (snack.getCost().subtract(userMoney));
        if (costCheck.compareTo(BigDecimal.ZERO) > 0) {
            throw new InsufficientFundsException(
            "Insufficient Funds - Make Another Selection");
        }
    }    
}
