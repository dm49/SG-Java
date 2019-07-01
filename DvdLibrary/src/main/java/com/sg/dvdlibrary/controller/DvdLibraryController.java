/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DvdLibraryDao;
import com.sg.dvdlibrary.dao.DvdLibraryDaoException;
import com.sg.dvdlibrary.dao.DvdLibraryDaoFileImpl;
import com.sg.dvdlibrary.dto.Library;
import com.sg.dvdlibrary.ui.DvdLibraryView;
import java.util.List;



/**
 *
 * @author mrder
 */
public class DvdLibraryController {
    
    DvdLibraryView view;
    DvdLibraryDao dao;
        
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        int choice = 1;
        boolean again = true;
        
        try {
            while (keepGoing) {
            
            menuSelection = getMenuSelection();
            
            switch (menuSelection) {
                case 1:
                    again = true;
                    choice =1;
                    while (again) {
                        switch(choice) {
                        case 1:
                            addDvd();
                            choice = repeat();
                            break;
                        case 2:
                            again = false;
                            break;
                        }
                    }break;
                case 2:
                    again = true;
                    choice = 1;
                    while (again) {
                        switch(choice) {
                            case 1:
                                removeDvd();
                                choice = repeat();
                                break;
                            case 2:
                                again = false;
                                break;
                        }
                    }break;
                case 3:
                    viewDvd();
                    break;
                case 4:
                    allMovies();
                    break;
                case 5:
                    again = true;
                    choice = 1;
                    while (again) {
                        switch(choice) {
                            case 1:
                                editDvd();
                                choice = repeat();
                                break;
                            case 2:
                                again = false;
                                break;
                        }
                    }break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }
        }
        exitMessage();
    } catch (DvdLibraryDaoException e) {
        view.displayErrorMessage(e.getMessage());
        }
    }
    
    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }
    
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void addDvd() throws DvdLibraryDaoException {
        view.displayAddLibraryBanner();
        Library newLibrary = view.getNewLibraryInfo();
        dao.addLibrary(newLibrary.getTitle(), newLibrary);
        view.displayAddLibrarySucessBanner();
    }
    
    private int repeat() {
        return view.doAgain();
    }
    
    private void allMovies() throws DvdLibraryDaoException {
        view.displayDislayAllBanner();
        List<Library> libraryList = dao.getAllLibrary();
        view.displayLibraryList(libraryList);
    }
    
    private void viewDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String title = view.getDvdChoice();
        Library library = dao.getLibrary(title);
        view.displayLibraryTitle(library);
    }
    
    private void removeDvd() throws DvdLibraryDaoException {
        view.displayRemoveDvdBanner();
        String title = view.getDvdChoice();
        dao.removeLibrary(title);
        view.displayRemoveDVDSuccessBanner();
    }
    
    private void editDvd() throws DvdLibraryDaoException {
        view.displayDisplayEditDvdBanner();
        //get the title of the dvd to be changed
        
        String title = view.getDvdChoice();
        //save the title for reference to the map key
        String oldTitle = title;
        //get the library from the dao
        Library library = dao.getLibrary(title);
        //show the user the dvd info so they can review the saved info
       if(library == null) {
           view.displayDisplayNullLibraryException();
           return;
       }
        view.displayLibraryTitle(library);
        //get the menu choice for the edit menu and to be used as reference
        //for the arraylist location of variables
        int editDvdChoice = view.printEditDvdMenu();
        //get the info the user would like changed
        if(editDvdChoice == 7) {
            return;
        }
        String editInfo = view.getEditDvdChoice();
        //put the library in an arraylist, edit it, and set as the new dvd info
        library = view.editDvd(library, editDvdChoice, editInfo);
        //replace the key in case this info is changed
        dao.editKey(title, oldTitle);
        //update the library in the hashmap
        dao.editLibrary(library.getTitle(), library);
        view.displayDisplayEditSuccessBanner();
    }
    
    private void exitMessage() {
        view.displayDisplayExitBanner();
    }
    
    private void unknownCommand() {
        view.displayDisplayExitBanner();
    }
}
