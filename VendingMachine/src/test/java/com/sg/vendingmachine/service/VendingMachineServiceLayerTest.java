/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.sg.vendingmachine.dto.Snacks;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mrder
 */
public class VendingMachineServiceLayerTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        service = new VendingMachineServiceLayerImpl(dao);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetSnack() throws Exception {
        service.getSnack("A1");
    }
    
    @Test
    public void testGetNoSnack() throws Exception {
        String location = "A2";
        try {
            service.getSnack(location);
            fail("Expected NoItemInventoryException was not thrown.");
        } catch (NoItemInventoryException e) {
            return;
        }
    }

    /**
     * Test of dispenseSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testDispenseSnack() throws Exception {
        Snacks snack = new Snacks("A3");
        snack.setItem("gummy bears");
        snack.setCost(BigDecimal.TEN);
        snack.setQuantity(1);
        try {
            service.dispenseSnack(snack, BigDecimal.ZERO);
            fail("Expected InsufficientFundsException was not thrown.");
        } catch (InsufficientFundsException e) {
            return;
        }
    }

    /**
     * Test of getAllSnacks method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetAllSnacks() throws Exception {
        service.getAllSnacks();
    }

    /**
     * Test of removeSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testRemoveSnack() throws Exception {
        Snacks snack = new Snacks("A4");
        snack.setItem("chocolate");
        snack.setCost(BigDecimal.TEN);
        snack.setQuantity(1);
        service.removeSnack(snack.getLocation(), snack);
    }

    /**
     * Test of addMoney method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testAddMoney() {
        service.addMoney(BigDecimal.ZERO);
    }

    /**
     * Test of getBalance method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetBalance() {
        service.getBalance();
    }

    /**
     * Test of makeChange method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testMakeChange() {
        service.makeChange(BigDecimal.ZERO);
    }
    
}
