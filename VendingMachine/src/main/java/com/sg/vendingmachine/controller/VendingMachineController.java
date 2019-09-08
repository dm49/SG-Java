/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snacks;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mrder
 */
public class VendingMachineController {
    
    VendingMachineView view;
    VendingMachineServiceLayer service;
      
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        int choice =1;
        boolean again = true;
        try {
        while (keepGoing) {
            start();
            menuSelection = getMenuSelection();
            
            switch (menuSelection) {
                case 1:
                    addFunds();
                    break;
                case 2:
                    getSnacks();
                    break;
                case 3:
                    giveChange();
                    keepGoing = false;
                    break;
                default:
                    defaultCommand();
            } //end of switch
        }//end of loop
        exit(); 

} catch (VendingMachinePersistenceException |
             NoItemInventoryException | InsufficientFundsException e) {
        view.displayErrorMessage(e.getMessage());
}
    }
//end of run
    
    //Functions
    public VendingMachineController
        (VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }
    
    private void start() throws
        VendingMachinePersistenceException {
        
        List <Snacks> snackList = service.getAllSnacks();
        view.displaySnacks(snackList);
    }    
        
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void addFunds() {
        view.displayInsertMoneyBanner();
        BigDecimal userMoney = view.addFunds();
        service.addMoney(userMoney);
        view.pressEnter();
    }
    
    private void chooseSnacks() throws
        NoItemInventoryException, 
        InsufficientFundsException, 
        VendingMachinePersistenceException {
            String location = view.chooseItem();
            Snacks snack = service.getSnack(location);
            BigDecimal userMoney = service.getBalance();
            String item = service.dispenseSnack(snack, userMoney);
            view.getItem(item);
            userMoney = view.newBalance(snack, userMoney);
            service.addMoney(userMoney);
            service.removeSnack(location, snack);
            view.fundsAvailableBanner();
            userMoney = service.getBalance();
            view.userBalance(userMoney);
    }
    
    private void getSnacks() throws
        VendingMachinePersistenceException,
        InsufficientFundsException,
        NoItemInventoryException {
            
            boolean hasErrors = false;
            do {
                view.displayGetSnacksBanner();
                try {
                    chooseSnacks();
                    hasErrors = false;
                } catch (NoItemInventoryException | InsufficientFundsException e) {
                    view.displayErrorMessage(e.getMessage());
                    hasErrors = false;
                }
            } while (hasErrors);     
    }
   
    private void giveChange() {
        BigDecimal userMoney = service.getBalance();
        Map<String, Integer> changeDue = service.makeChange(userMoney);
        view.changeDueBanner();
        view.viewChange(changeDue);
    }
    
    private void defaultCommand() {
        view.defaultBanner();
    }
    
    private void exit() {
        view.displayExitBanner();
    }
    
    
}//end of controller
