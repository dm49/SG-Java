/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.ui;

import com.sg.dvdlibrary.dto.Library;
import java.util.ArrayList;
import java.util.List;





/**
 *
 * @author mrder
 */
public class DvdLibraryView {
    
    private UserIO io;
    
    public DvdLibraryView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1: Add DVD");
        io.print("2: Remove DVD");
        io.print("3: View DVD");
        io.print("4: View Collection");
        io.print("5: Edit Collection");
        io.print("6: Exit");
        
        return io.readInt("Select from the above choices", 1, 6);
    }
    
    public Library getNewLibraryInfo() {
        String title = io.readString("Enter the movie title.");
        String year = io.readString("Enter the year the movie was released.");
        String rating = io.readString("Enter the MPAA rating of the movie.");
        String director = io.readString("Enter the movie's director.");
        String studio = io.readString("Enter the studio that released the movie.");
        String notes = io.readString("Enter any additional information you would like to add");
        Library currentLibrary = new Library(title);
        currentLibrary.setYear(year);
        currentLibrary.setRating(rating);
        currentLibrary.setDirector(director);
        currentLibrary.setStudio(studio);
        currentLibrary.setNotes(notes);
        return currentLibrary;
    }
    
    public void displayAddLibraryBanner() {
        io.print("--- Add A DVD ---");
    }
    
    public void displayAddLibrarySucessBanner() {
        io.print("--- DVD Added ---");
    }
    
    public int doAgain() {
        return io.readInt("1 Repeat Step \n2 Go Back To Menu", 1, 2);
    }
    
    public void displayLibraryList(List<Library> libraryList) {
        for (Library currentLibrary : libraryList) {
            io.print(currentLibrary.getTitle()
            + ( " - " ) + currentLibrary.getYear()
            + ( " - " ) + currentLibrary.getRating()
            + ( " - " ) + currentLibrary.getDirector()
            + ( " - " ) + currentLibrary.getStudio()
            + ( " - " ) + currentLibrary.getNotes());
        }
        io.readString("Press enter to continue.");
    }
    
    public void displayDislayAllBanner() {
        io.print("--- View Library ---");
    }
    
    public void displayDisplayDvdBanner() {
        io.print("--- View DVD ---");
    }
    
    public String getDvdChoice() {
        return io.readString("Enter the DVD title.");
    }
    
    public void displayLibraryTitle(Library library) {
        if(library != null) {
            io.print(library.getTitle()
            + ( " - " ) + library.getYear()
            + ( " - " ) + library.getRating()
            + ( " - " ) + library.getDirector()
            + ( " - " ) + library.getStudio()
            + ( " - " ) + library.getNotes());
        } else {
            io.print("That DVD is not in your library.");
        }
        io.readString("Press enter to continue.");
    }
    
    public void displayRemoveDvdBanner() {
        io.print("--- Remove DVD ---");
    }
    
    public void displayRemoveDVDSuccessBanner() {
        io.readString("DVD removed. Press enter to continue.");
    }
    
    public void displayDisplayEditDvdBanner() {
        io.print("--- Edit DVD ---");
    }
    
    public int printEditDvdMenu() {
        io.print("1: Edit Title");
        io.print("2: Edit Year");
        io.print("3: Edit Rating");
        io.print("4: Edit Director");
        io.print("5: Edit Studio");
        io.print("6: Edit Notes");
        io.print("7: Exit");
        
        return io.readInt("Select from the above choices", 1, 7);
    }
    
    public String getEditDvdChoice() {
        return io.readString("Enter the information you would like changed.");
    }
    
    public void displayDisplayEditSuccessBanner() {
        io.print("DVD successfully edited.");
        io.readString("Press enter to continue");
    }
 
    public Library editDvd(Library library, int editDvdChoice, String editInfo) {
        editDvdChoice = editDvdChoice - 1;
        ArrayList<String> libraryList = new ArrayList<String>();
        libraryList.add(0, library.getTitle());
        libraryList.add(1, library.getYear());
        libraryList.add(2, library.getRating());
        libraryList.add(3, library.getDirector());
        libraryList.add(4, library.getStudio());
        libraryList.add(5, library.getNotes());
        libraryList.set(editDvdChoice, editInfo);
        String title = libraryList.get(0);
        String year = libraryList.get(1);
        String rating = libraryList.get(2);
        String director = libraryList.get(3);
        String studio = libraryList.get(4);
        String notes = libraryList.get(5);
        Library currentLibrary = new Library(title);
        currentLibrary.setYear(year);
        currentLibrary.setRating(rating);
        currentLibrary.setDirector(director);
        currentLibrary.setStudio(studio);
        currentLibrary.setNotes(notes);
        return currentLibrary;
    }
    
    public void displayDisplayNullLibraryException() {
        io.print("That title is not in your library.");
    }
    
    public void displayDisplayUnknownCommandBanner() {
        io.print("Unknown Command");
    }
    
    public void displayDisplayExitBanner() {
        io.print("Goodbye!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("--- ERROR ---");
        io.print(errorMsg);
    }
}
