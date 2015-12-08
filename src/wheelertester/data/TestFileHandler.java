/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheelertester.data;

/**
 * Class for exposing protected methods for testing
 */
public class TestFileHandler extends wheelertester.data.FileHandler{
    
    /** The parent folder of the folders in which we expect to be storing and using test data */
    public static final String expectedWheelerTesterFolder = "C:\\Program Files\\Wheeler\\Wheeler Tester\\test";
    
    public static String callGetTestFolder(int indirection) throws Exception{
        return FileHandler.getTestFolder(indirection + 1);
    }
    
    public static String callGetTestFolder(String argTag, int indirection) throws Exception{
        return FileHandler.getTestFolder(argTag, indirection + 1);
    }
    
}
