/*@todo add some sort of monitor or listener. look at
 * http://commons.apache.org/io/apidocs/org/apache/commons/io/monitor/FileAlterationMonitor.html
 * http://commons.apache.org/io/apidocs/org/apache/commons/io/monitor/FileAlterationObserver.html
 */
package com.thedaego.syncfiles;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 
 * @author thedaego
 */
public class App 
{
    static final Logger logger = Logger.getLogger(App.class.getName());
    static final long pollingInerval = 10000;
    public static void main( String[] args )
    {
        logger.setLevel(Level.ALL);
        File localFile;
        File dropBoxFile;
        String backupLocation = "D:\\Dropbox\\Dropbox\\WoW\\syncfiles\\backup";
        String localFilePath = "C:\\Program Files (x86)\\World of Warcraft\\WTF\\Account\\DA3G0\\SavedVariables\\Auc-ScanData.lua";
        //String localFilePath = "C:\\Users\\thedaego\\Desktop\\Auc-ScanData.lua";
        String dropBoxFilePath = "D:\\Dropbox\\Dropbox\\WoW\\syncfiles\\Auc-ScanData.lua";
        localFile = new File(localFilePath);
        dropBoxFile = new File(dropBoxFilePath);
        FileAlterationObserver observer = new FileAlterationObserver("C:\\Users\\thedaego\\Desktop\\", new NameFileFilter("Auc-ScanData.lua"));
        DefaultSyncer syncer = new DefaultSyncer(localFile, dropBoxFile, new File(backupLocation));
        observer.addListener(new SyncFileListener(syncer));
        FileAlterationMonitor monitor = new FileAlterationMonitor(pollingInerval);
        monitor.addObserver(observer);
        try {
            monitor.start();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
