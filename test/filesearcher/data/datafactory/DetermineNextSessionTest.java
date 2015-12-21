/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.data.datafactory;

import filesearcher.data.DataFactory;
import filesearcher.data.FileHandler;
import filesearcher.data.TestFileHandler;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Greg
 */
public class DetermineNextSessionTest {
    
    @Test
    public void testNextSessionDetermination() throws Exception{
        // We're going to be creating session files; need to set the program files folder
        String testFolder = TestFileHandler.getTestFolder(0);
        // If the test failed earlier there'll be signal files left over; delete them
        FileHandler.clearFolder(testFolder);
        
        // First session: empty string
        // ()
        String firstSession = DataFactory.determineNextSession();
        assertEquals("The first session did not return an empty string", "", firstSession);
        
        // If the empty-string is present, return 0
        // ("")
        FileHandler.ensureFileExists(FileHandler.signalFile(firstSession));
        String session0 = DataFactory.determineNextSession();
        assertEquals("The second sesion did not return 0", "0", session0);
        
        // Remove the initial session, make sure empty-string is returned again
        // ()
        FileHandler.deleteFile(FileHandler.signalFile(firstSession));
        assertEquals("Removing all sessions did not allow the empty-string to be returned", firstSession, DataFactory.determineNextSession());
        
        // Put the initial session back, create the 0 signal file, now expect 1 to be returned
        // ("", 0)
        FileHandler.ensureFileExists(FileHandler.signalFile(firstSession));
        FileHandler.ensureFileExists(FileHandler.signalFile(session0));
        String session1 = DataFactory.determineNextSession();
        assertEquals("The third session did not return 1", "1", session1);
        
        // Create the 1 file, remove the 0 file, expect 0 to be returned
        // ("", 1)
        FileHandler.ensureFileExists(FileHandler.signalFile(session1));
        FileHandler.deleteFile(FileHandler.signalFile(session0));
        assertEquals("Removing the second session did not allow 0 to be returned", session0, DataFactory.determineNextSession());
        
        // Replace the 0 file, expect 2 to be returned
        // ("", 0, 1)
        FileHandler.ensureFileExists(FileHandler.signalFile(session0));
        String session2 = DataFactory.determineNextSession();
        assertEquals("The fourth session did not return 2", "2", session2);
        
        // Create the 2 file, remove the 1 file, expect 1 to be returned
        // ("", 0, 2)
        FileHandler.ensureFileExists(FileHandler.signalFile(session2));
        FileHandler.deleteFile(FileHandler.signalFile(session1));
        assertEquals("Removing the third session did not allow 1 to be returned", session1, DataFactory.determineNextSession());
        
        // Remove the initial file, expect an empty string to be returned
        // (0, 2)
        FileHandler.deleteFile(FileHandler.signalFile(firstSession));
        assertEquals("Removing the initial session did not allow empty-string to be returned", firstSession, DataFactory.determineNextSession());
        
        // We're done here, delete all signal files
        FileHandler.deleteFolder(testFolder);
        
    }
    
}
