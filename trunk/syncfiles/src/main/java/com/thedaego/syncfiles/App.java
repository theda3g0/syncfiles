package com.thedaego.syncfiles;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;


public class App 
{
    static final Logger logger = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        logger.setLevel(Level.ALL);
        File localFile;
        File dropBoxFile;
        String backupLocation = "D:\\Dropbox\\Dropbox\\WoW\\syncfiles\\backup";
//        String localFilePath = "C:\\Program Files (x86)\\World of Warcraft\\WTF\\Account\\DA3G0\\SavedVariables";
        String localFilePath = "C:\\Users\\thedaego\\Desktop\\Auc-ScanData.lua";
        String dropBoxFilePath = "D:\\Dropbox\\Dropbox\\WoW\\syncfiles\\Auc-ScanData.lua";
        localFile = new File(localFilePath);
        dropBoxFile = new File(dropBoxFilePath);

        if(localFile.canRead() && dropBoxFile.canRead())
        {
            //compare the last modified data of each file
            //if the localFile is newer than the dropBoxFile
                // - backup the dropboxFile
                // - copy the localFile over to the dropboxFilePath
                //
            //if the DropBox File is newer than the localFile
                // - backup the localFile to the backupdirectory
                // - copy the dropboxFile over to the localPath

            if(localFile.lastModified() > dropBoxFile.lastModified())
            {
                
                logger.log(Level.INFO, "localfile is newer");
                DateTime dt = new DateTime(dropBoxFile.lastModified());
                logger.log(Level.INFO, "backing up {0}", dropBoxFile.getAbsolutePath());
                dropBoxFile.renameTo(new File(backupLocation+"\\"+dropBoxFile.getName()+"."+dt.toString("yyyyMMdd.kkmmss")));
                try {
                    logger.log(Level.INFO, "copying {0} to {1}", new Object[]{localFile.getAbsolutePath(), dropBoxFile.getAbsolutePath()});
                    FileUtils.copyFile(localFile, dropBoxFile);
                } catch (IOException ex) {
                    logger.throwing("", "", ex);
                }
                
            }else if(localFile.lastModified() < dropBoxFile.lastModified())
            {
                DateTime dt = new DateTime(dropBoxFile.lastModified());
                localFile.renameTo(new File(backupLocation+"\\"+localFile.getName()+"."+dt.toString("yyyyMMdd.kkmmss")));
                try {
                    FileUtils.copyFile(dropBoxFile, localFile);
                } catch (IOException ex) {
                    logger.throwing("", "", ex);
                }
            }else{
                logger.log(Level.INFO, "nothin to do");
            }
        }


    }
}
