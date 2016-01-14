/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheelertester.data;

import wheeler.generic.data.LogicHandler;

/**
 * Handle file operations common across project tests
 */
public class FileHandler extends wheeler.generic.data.FileHandler {
    
    protected static String testerFolder = "C:\\Program Files\\Wheeler\\Wheeler Tester";
    protected static boolean programFolderTested = false;
    
    /**
     * Get a folder in the Wheeler Tester folder named after the method signature
     * @param argTag An extra tag to add if the same function tests multiple situations
     * @param indirection Zero starting from the function that creates the folder
     * @return The filepath of a folder in Wheeler Tester named after the calling function
     * @throws Exception If there's an issue creating the Wheeler Tester folder or getting the method signature
     */
    protected static String getTestFolder(String argTag, int indirection) throws Exception{
        return getTestFolder(indirection + 1) + "-" + argTag;
    }
    /**
     * Get a folder in the Wheeler Tester folder named after the method signature
     * @param indirection Zero starting from the function that creates the folder
     * @return The filepath of a folder in Wheeler Tester named after the calling function
     * @throws Exception If there's an issue creating the Wheeler Tester folder or getting the method signature
     */
    protected static String getTestFolder(int indirection) throws Exception{
        // Make sure we can access the Wheeler Tester folder
        if(!programFolderTested){
            testProgramFolder(testerFolder, null);
            programFolderTested = true;
        }
        
        // Compose and return the filepath
        return composeFilepath(
                composeFilepath(testerFolder, "test"),
                LogicHandler.getCallingMethod(indirection + 1)
            );
    }
    
}
