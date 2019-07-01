/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.Library;
import java.util.List;

/**
 *
 * @author mrder
 */
public interface DvdLibraryDao {
    
    Library addLibrary(String title, Library library)
     throws DvdLibraryDaoException;
             
    List<Library> getAllLibrary()
     throws DvdLibraryDaoException;
             
    Library getLibrary(String title)
     throws DvdLibraryDaoException;
             
    Library removeLibrary(String title)
     throws DvdLibraryDaoException;
             
    void editKey(String title, String oldTitle);
    
    Library editLibrary(String title, Library library)
     throws DvdLibraryDaoException;
}
