/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedaego.syncfiles;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 *
 * @author thedaego
 */
public class SyncFileListener implements FileAlterationListener 
{
    private static final Logger logger = Logger.getLogger(SyncFileListener.class.getName());
    
    
    private Syncer syncer;
    public SyncFileListener(Syncer syncer) {
        this.syncer = syncer;
        System.out.println("starting");
    }
    
    public void onStart(FileAlterationObserver observer) {
        logger.log(Level.INFO, "Starting");
        syncer.sync();
    }

    public void onDirectoryCreate(File directory) {
        System.out.println("directory created");
        syncer.sync();
    }

    public void onDirectoryChange(File directory) {
        System.out.println("directory changed");
        syncer.sync();
    }

    public void onDirectoryDelete(File directory) {
        System.out.println("file deleted");
    }

    public void onFileCreate(File file) {
        System.out.println("file Created");
        syncer.sync();
    }

    public void onFileChange(File file) 
    {
        System.out.println("file changed: "+file.getAbsolutePath());
        syncer.sync();
    }

    public void onFileDelete(File file) {
        System.out.println("file deleted");
    }

    public void onStop(FileAlterationObserver observer) {
        System.out.println(this.getClass().getName()+"Stopped");
    }
    
}
