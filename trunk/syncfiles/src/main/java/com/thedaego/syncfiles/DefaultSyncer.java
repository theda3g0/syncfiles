/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedaego.syncfiles;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

/**
 *
 * @author thedaego
 */
public class DefaultSyncer implements Syncer
{
    private File fileLocation1;
    private File fileLocation2;
    private File backupLocation;
    private static final Logger logger = Logger.getLogger(App.class.getName());
    
    /**
     * 
     * @param fileLocation1
     * @param fileLocation2 
     */
    public DefaultSyncer(File fileLocation1, File fileLocation2) {
        this.fileLocation1 = fileLocation1;
        this.fileLocation2 = fileLocation2;
    }

    /**
     * 
     * @param fileLocation1
     * @param fileLocation2
     * @param backupLocation 
     */
    public DefaultSyncer(File fileLocation1, File fileLocation2, File backupLocation) {
        this.fileLocation1 = fileLocation1;
        this.fileLocation2 = fileLocation2;
        this.backupLocation = backupLocation;
    }
    
    /**
     * 
     * @param file 
     */
    private void backup(File file)
    {
        if(backupLocation != null)
        {
            if(backupLocation.exists())
            {
                DateTime dt = new DateTime();
                logger.log(Level.INFO, "backing up {0}", file.getAbsolutePath());
                file.renameTo(new File(backupLocation+"\\"+file.getName()+"."+dt.toString("yyyyMMdd.kkmmss")));                    
            }                
        }        
    }
    /**
     * 
     * @return 
     */
    public boolean sync() 
    {
        boolean rc =false;
        if(fileLocation1.lastModified() > fileLocation2.lastModified())
        {
            logger.log(Level.INFO, "{0} is newer", fileLocation1.getAbsolutePath());
            backup(fileLocation2);
            logger.log(Level.INFO, "copying {0} to {1}"
                    , new Object[]{fileLocation1.getAbsolutePath()
                    , fileLocation2.getAbsolutePath()});            
            try {
                FileUtils.copyFile(fileLocation1, fileLocation2);
            } catch (IOException ex) {
                rc =false;
                logger.throwing("", "", ex);
            }
            rc=true;
        }else if(fileLocation1.lastModified() < fileLocation2.lastModified())
        {
            logger.log(Level.INFO, "{0} is newer", fileLocation2.getAbsolutePath());
            backup(fileLocation1);
            logger.log(Level.INFO, "copying {0} to {1}"
                    , new Object[]{fileLocation2.getAbsolutePath()
                    , fileLocation1.getAbsolutePath()});            
            try {
                FileUtils.copyFile(fileLocation2, fileLocation1);
            } catch (IOException ex) {
                rc =false;
                logger.throwing("", "", ex);
            }
            rc=true;
        }else{
            rc =false;
            logger.log(Level.INFO, "nothin to do");
        }
        return rc;
    }
    
}
