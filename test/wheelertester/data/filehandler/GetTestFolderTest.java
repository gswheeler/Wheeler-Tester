/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheelertester.data.filehandler;

import static org.junit.Assert.*;
import org.junit.Test;
import wheeler.generic.data.FileHandler;
import wheelertester.data.TestFileHandler;

/**
 * Test our function for getting folder paths
 */
public class GetTestFolderTest {
    
    @Test
    public void testGetTestFolder() throws Exception{
        String expected =
                TestFileHandler.expectedWheelerTesterFolder
                + "\\wheelertester.data.filehandler.GetTestFolderTest.testGetTestFolder"
            ;
        String actual = TestFileHandler.getTestFolder(0);
        assertEquals("Did not get the expected test folder", expected, actual);
        FileHandler.ensureFolderExists(actual);
        assertTrue("Failed to create the test folder", FileHandler.folderExists(actual));
        FileHandler.deleteFolder(actual);
        assertFalse("Failed to delete the test folder", FileHandler.folderExists(actual));
    }
    
    @Test
    public void testGetTestFolderWithArgumentTag() throws Exception{
        String expected =
                "C:\\Program Files\\Wheeler\\Wheeler Tester"
                + "\\wheelertester.data.filehandler.GetTestFolderTest.testGetTestFolderWithArgumentTag"
                + "-string"
            ;
        String actual = testGetTestFolderWithArgumentTagWorker("string");
        assertEquals("Did not get the expected tagged test folder", expected, actual);
        FileHandler.ensureFolderExists(actual);
        assertTrue("Failed to create the tagged test folder", FileHandler.folderExists(actual));
        FileHandler.deleteFolder(actual);
        assertFalse("Failed to delete the tagged test folder", FileHandler.folderExists(actual));
    }
    private String testGetTestFolderWithArgumentTagWorker(String tag) throws Exception{
        return TestFileHandler.getTestFolder(tag, 1);
    }
    
}
