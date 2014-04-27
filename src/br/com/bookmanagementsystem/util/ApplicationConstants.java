/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bookmanagementsystem.util;

/**
 *
 * @author renann
 */
public class ApplicationConstants
{
    public static String BASE_PATH = "./";
    public static String FILE_NAME = "data.dat";
    public static String FULL_FILE_PATH = BASE_PATH + FILE_NAME;
    public static String BACKUP_FILE_NAME = "alldata.dat.bkp";
    public static String BACKUP_FILE_PATH = BASE_PATH + BACKUP_FILE_NAME;
    
    public static void setPathConstants(final String basePath)
    {
        BASE_PATH = basePath;
        FILE_NAME = "data.dat";
        FULL_FILE_PATH = BASE_PATH + FILE_NAME;
        BACKUP_FILE_NAME = "alldata.dat.bkp";
        BACKUP_FILE_PATH = BASE_PATH + BACKUP_FILE_NAME;
    }
}
