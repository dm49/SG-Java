/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.Library;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author mrder
 */
public class DvdLibraryDaoFileImpl implements DvdLibraryDao {

    public static final String LIBRARY_FILE = "movieLibrary.txt";
    public static final String DELIMITER = "::";
    
    private Map<String, Library> movies = new HashMap<>();
    
    private void loadLibraryFile() throws DvdLibraryDaoException {
        Scanner scanner;
        try {
            scanner = new Scanner(
            new BufferedReader(
            new FileReader(LIBRARY_FILE)));
            } catch (FileNotFoundException e) {
                throw new DvdLibraryDaoException(
                "-_- Could not load DVD into memory.", e);
        }
        String currentLine;
        String[] currentTokens;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Library currentLibrary = new Library(currentTokens[0]);
            currentLibrary.setTitle(currentTokens[0]);
            currentLibrary.setYear(currentTokens[1]);
            currentLibrary.setRating(currentTokens[2]);
            currentLibrary.setDirector(currentTokens[3]);
            currentLibrary.setStudio(currentTokens[4]);
            currentLibrary.setNotes(currentTokens[5]);
            movies.put(currentLibrary.getTitle(), currentLibrary);
        }
        scanner.close();
    }
    
    private void writeLibraryFile() throws DvdLibraryDaoException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new DvdLibraryDaoException(
            "Could not save library data.", e);
        }
        List<Library> libraryList = this.getAllLibrary();
        for (Library currentLibrary : libraryList) {
            out.println(currentLibrary.getTitle() + DELIMITER
            + currentLibrary.getYear() + DELIMITER
            + currentLibrary.getRating() + DELIMITER
            + currentLibrary.getDirector() + DELIMITER
            + currentLibrary.getStudio() + DELIMITER
            + currentLibrary.getNotes());
            out.flush();
        }
        out.close();
    }
    
    @Override
    public Library addLibrary(String title, Library library) 
     throws DvdLibraryDaoException {
        Library newLibrary = movies.put(title, library);
        writeLibraryFile();
        return newLibrary;
    }

    @Override
    public List<Library> getAllLibrary()
     throws DvdLibraryDaoException {
        loadLibraryFile();
        return new ArrayList<Library>(movies.values());
    }

    @Override
    public Library getLibrary(String title) 
     throws DvdLibraryDaoException {
        loadLibraryFile();
        return movies.get(title);
    }

    @Override
    public Library removeLibrary(String title)
     throws DvdLibraryDaoException {
        Library removedLibrary = movies.remove(title);
        writeLibraryFile();
        return removedLibrary;
    }
    
    @Override
    public void editKey(String title, String oldTitle) {
        movies.put(title, movies.get(oldTitle));
        movies.remove(oldTitle);
    }
    @Override
    public Library editLibrary(String title, Library library) 
     throws DvdLibraryDaoException {
        movies.put(title, library);
        writeLibraryFile();
        return library;
            
        }

    }
    

