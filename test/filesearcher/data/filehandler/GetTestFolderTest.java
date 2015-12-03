/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.data.filehandler;

import filesearcher.data.FileHandler;
import filesearcher.data.TestFileHandler;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import wheeler.generic.data.LogicHandler;

/**
 * Test that we set the Program Files variable in the FileHandler when we get the test folder
 */
public class GetTestFolderTest {
    
    @Test
    public void testGetTestFolder() throws Exception{
        
        // The expected test folder: WheelerTester plus method signature
        String expected =
                wheelertester.data.TestFileHandler.expectedWheelerTesterFolder
                + "\\" + LogicHandler.getCallingMethod(0);
        FileHandler.deleteFolder(expected);
        String testFolder = TestFileHandler.getTestFolder(0);
        assertEquals("Did not get the expected test folder", expected, testFolder);
        
        // Get the data folder, make sure it's what we expect, make sure it got created
        String dataSubpath = "\\Program Files\\data";
        String dataFolder = expected + dataSubpath;
        assertEquals("FileHandler's programFolder was not set as we expected", dataFolder, FileHandler.dataFolder());
        assertTrue("The programFolder was not created", FileHandler.folderExists(dataFolder));
        
        // Clean up after ourselves
        FileHandler.deleteFolder(testFolder);
        
        
        // Do it again, this time with an argTag
        expected += "-intstring";
        FileHandler.deleteFolder(expected);
        testFolder = TestFileHandler.getTestFolder("intstring", 0);
        assertEquals("Did not get the expected tagged test folder", expected, testFolder);
        dataFolder = expected + dataSubpath;
        assertEquals("FileHandler's programFolder was not re-set as we expected", dataFolder, FileHandler.dataFolder());
        assertTrue("The new programFolder was not created", FileHandler.folderExists(dataFolder));
        FileHandler.deleteFolder(testFolder);
        
    }
    
}
