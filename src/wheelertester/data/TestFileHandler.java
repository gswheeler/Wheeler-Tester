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
    
    public static String getTestFolder(int indirection) throws Exception{
        return FileHandler.getTestFolder(indirection + 1);
    }
    
    public static String getTestFolder(String argTag, int indirection) throws Exception{
        return FileHandler.getTestFolder(argTag, indirection + 1);
    }
    
}
