/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.data;

/**
 * A class with special functions for setting protected fields according to test values
 */
public class TestFileHandler extends wheelertester.data.FileHandler{
    
    /**Get a test folder unique to the calling test.
     * Sets the programFolder to a folder named "Program Files" in the test folder.
     * @param indirection Zero starting from the function that creates the folder
     * @return The filepath of a folder in Wheeler Tester named after the calling function
     * @throws Exception If there's an issue creating the Wheeler Tester folder or getting the method signature
     */
    public static String getTestFolder(int indirection) throws Exception{
        String testFolder = wheelertester.data.FileHandler.getTestFolder(indirection + 1);
        new TestFileHandler().innerHandler.setProgramFolderByTestFolder(testFolder);
        return testFolder;
    }
    
    /**Get a test folder unique to the calling test.
     * Sets the programFolder to a folder named "Program Files" in the test folder.
     * @param argTag An extra tag to add if the same function tests multiple situations
     * @param indirection Zero starting from the function that creates the folder
     * @return The filepath of a folder in Wheeler Tester named after the calling function
     * @throws Exception If there's an issue creating the Wheeler Tester folder or getting the method signature
     */
    public static String getTestFolder(String argTag, int indirection) throws Exception{
        String testFolder = wheelertester.data.FileHandler.getTestFolder(argTag, indirection + 1);
        new TestFileHandler().innerHandler.setProgramFolderByTestFolder(testFolder);
        return testFolder;
    }
    
    /**Return the current Program Files folder being used by the File Searcher FileHandler
     * @return The value of programFolder in File Searcher's FileHandler
     */
    public static String getProgramFolder(){
        return new TestFileHandler().innerHandler.getProgramFolder();
    }
    
    
    
    /** Our sole constructor; creates an instance of the inner FileHandler */
    private TestFileHandler(){
        // Subclasses need a non-static class instance to be instantiated
        innerHandler = new InnerFileHandler();
    }
    
    /** A non-static instance of our inner FileHandler */
    private final InnerFileHandler innerHandler;
    /** The inner FileHandler we use to reach protected fields in Wheeler's FileHandler */
    class InnerFileHandler extends wheeler.generic.data.FileHandler{
        /**Redirects operations involving the Program Files folder to a folder within the provided test folder.
         * @param testFolder Folder in which the test's Program Files folder should be created.
         */
        public void setProgramFolderByTestFolder(String testFolder){
            wheelerFolder = composeFilepath(testFolder, "Program Files");
        }
        /**Return the current Program Files folder being used by the File Searcher FileHandler
         * @return The value of programFolder in File Searcher's FileHandler
         */
        public String getProgramFolder(){ return wheelerFolder; }
    }
    
}
