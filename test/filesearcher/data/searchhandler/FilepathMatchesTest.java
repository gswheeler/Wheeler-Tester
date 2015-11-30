/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.data.searchhandler;

import filesearcher.data.TestSearchHandler;
import filesearcher.structs.Parameters;
import filesearcher.structs.TestParameters;
import org.junit.*;
import static org.junit.Assert.*;
import wheeler.generic.data.StringHandler;
import wheeler.generic.structs.StringList;

/**
 * Test SearchHandler's function for checking if a filepath matches the appropriate search parameters
 */
public class FilepathMatchesTest {
    
    private TestSearchHandler handler;
    private Parameters params;
    
    private final String fileA = "C:\\folderA\\fileA.txt";
    
    @Before
    public void setup(){
        params = new TestParameters();
        handler = new TestSearchHandler(params);
    }
    
    @Test
    public void testNoParameters(){
        // Don't add any parameters, expect both file and folder to pass
        assertTrue("The file failed to pass a check with no parameters", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed to pass a check with no parameters", handler.filepathMatches(fileA, true));
    }
    
    @Test
    public void testFilenameInclude(){
        // Add a name parameter, expect both file and folder to pass
        params.includeFiles = new StringList("\\fILeA").toArray();
        assertTrue("The file failed to pass a check with a name parameter", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed to pass a check with a name parameter", handler.filepathMatches(fileA, true));
        
        // Add a parameter that the path doesn't have, expect file and folder to fail
        params.includeFiles = new StringList(params.includeFiles).add("\\fILeB").toArray();
        assertFalse("The file passed a two-item name check when it should not have", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a two-item name check when it should not have", handler.filepathMatches(fileA, true));
        
        // Set the name to something not in the name, expect both file and folder to fail
        String searchItem = "folder";
        params.includeFiles = new StringList(searchItem).toArray();
        assertTrue("The test item was supposed to be in the filepath", StringHandler.contains(fileA, searchItem, true));
        assertFalse("The file passed a name check when the search item wasn't in its name", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a name check when the search item wasn't in its name", handler.filepathMatches(fileA, true));
    }
    
    @Test
    public void testFilenameExclude(){
        // Check for a name the path does not have, check that both file and folder pass
        params.excludeFiles = new StringList("\\fILeB").toArray();
        assertTrue("The file failed to pass a check with a non-matching name exclusion", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed to pass a check with a non-matching name exclusion", handler.filepathMatches(fileA, true));
        
        // Add a name the path has, check that neither file nor folder pass
        params.excludeFiles = new StringList(params.excludeFiles).add("\\fILeA").toArray();
        assertFalse("The file passed a two-item name check when it should not have", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a two-item name check when it should not have", handler.filepathMatches(fileA, true));
        
        // Check for a name the path has but not in its name, check that both file and folder pass
        String searchItem = "folder";
        params.excludeFiles = new StringList(searchItem).toArray();
        assertTrue("The test exclusion was supposed to be in the filepath", StringHandler.contains(fileA, searchItem, true));
        assertTrue("The file failed a name check when the excluder wasn't in its name", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed a name check when the excluder wasn't in its name", handler.filepathMatches(fileA, true));
        
        // Include and exclude the file name, check that neither file nor folder pass
        params.includeFiles = new StringList("\\fILeA").toArray();
        params.excludeFiles = new StringList(params.includeFiles).toArray();
        assertFalse("The file passed a name check when the included name was also excluded", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a name check when the included name was also excluded", handler.filepathMatches(fileA, true));
    }
    
    @Test
    public void testPathInclude(){
        // Check for a path in the folder, check that both file and folder pass
        params.includePaths = new StringList("\\foLDerA").toArray();
        assertTrue("The file failed to pass a check with a folder string as a path parameter", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed to pass a check with a folder string as a path parameter", handler.filepathMatches(fileA, true));
        
        // Add a path in the name, check that both file and folder pass
        params.includePaths = new StringList(params.includePaths).add("\\fILeA").toArray();
        assertTrue("The file failed to pass a check with a file string as a path parameter", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed to pass a check with a file string as a path parameter", handler.filepathMatches(fileA, true));
        
        // Add a path not in either, check that neither file nor folder pass
        params.includePaths = new StringList(params.includePaths).add("\\foLDerB").toArray();
        assertFalse("The file passed a path check when it was missing one of the search items", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a path check when it was missing one of the search items", handler.filepathMatches(fileA, true));
    }
    
    @Test
    public void testPathExclude(){
        // Check for a path it has in its folder, check that neither file nor folder pass
        params.excludePaths = new StringList("\\foLDerA").toArray();
        assertFalse("The file passed a path exclusion when it had the search item", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a path exclusion when it had the search item", handler.filepathMatches(fileA, true));
        
        // Check for a path it doesn't have, check that both file and folder pass
        params.excludePaths = new StringList("\\foLDerB").toArray();
        assertTrue("The file failed a path exclusion when it didn't have the search item", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed a path exclusion when it didn't have the search item", handler.filepathMatches(fileA, true));
        
        // Add a path it has in its name, check that neither file nor folder pass
        params.excludePaths = new StringList(params.excludePaths).add("\\fILeA").toArray();
        assertFalse("The file failed a two-item path exclusion when it should have failed", handler.filepathMatches(fileA, false));
        assertFalse("The folder failed a two-item path exclusion when it should have failed", handler.filepathMatches(fileA, true));
        
        // Include and exclude the path, check that neither file nor folder pass
        params.includePaths = new StringList("\\foLDerA").toArray();
        params.excludePaths = new StringList(params.includePaths).toArray();
        assertFalse("The file passed a path check when the path was both included and excluded", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a path check when the path was both included and excluded", handler.filepathMatches(fileA, true));
    }
    
    @Test
    public void testTypeInclude(){
        // Check for the path's type, check that the file passes while the folder does not
        params.includeTypes = new StringList("LeA.txt").toArray();
        assertTrue("The file failed a type check when it had the search item", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a type check when the folder key was not present", handler.filepathMatches(fileA, true));
        
        // Add the folder key, check that both the file and the folder pass
        params.includeTypes = new StringList(params.includeTypes).add("\\").toArray();
        assertTrue("The file failed a type check with its type and the folder key", handler.filepathMatches(fileA, false));
        assertTrue("The folder failed a type check when the folder key was present", handler.filepathMatches(fileA, true));
        
        // Check for a type that is not and a type that is the file's type, check that the file passes
        params.includeTypes = new StringList("LeB.txxt").add("LeA.txt").toArray();
        assertTrue("The file failed a two-item type check when one of the types was a match", handler.filepathMatches(fileA, false));
        
        // Check for the folder key, check that the folder passes while the file does not
        params.includeTypes = new StringList("\\").toArray();
        assertTrue("The folder failed a type check when the folder key was the only search item", handler.filepathMatches(fileA, true));
        assertFalse("The file passed a type check when the folder key was the only search item", handler.filepathMatches(fileA, false));
        
        // Check for a different type, check that neither file nor folder pass
        params.includeTypes = new StringList("LeB.txt").toArray();
        assertFalse("The file passed a type check when it didn't have the search item", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a type check when a non-match was the only item present", handler.filepathMatches(fileA, true));
    }
    
    @Test
    public void testTypeExclude(){
        // Check the path's type, check that neither file nor folder pass
        params.excludeTypes = new StringList("LeA.txt").toArray();
        assertFalse("The file passed a type check when an exclusion match was present", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a type check when an exclusion match was present", handler.filepathMatches(fileA, true));
        
        // Check for a type that isn't the path's, check that the file passes while the folder does not
        params.excludeTypes = new StringList("LeB.txt").toArray();
        assertTrue("The file failed a type check when the search item was an exclusion non-match", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a type check when the search item was an exclusion non-match", handler.filepathMatches(fileA, true));
        
        // Add the path's type, check that neither file nor folder pass
        params.excludeTypes = new StringList(params.excludeTypes).add("LeA.txt").toArray();
        assertFalse("The file failed a two-item type exclusion when a match was present", handler.filepathMatches(fileA, false));
        assertFalse("The folder failed a two-item type exclusion with a match and a non-match", handler.filepathMatches(fileA, true));
        
        // Check for the folder key, check that the file passes while the folder does not
        params.excludeTypes = new StringList("\\").toArray();
        assertTrue("The file failed a type check that only used the folder key", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a type check that looked for the folder key", handler.filepathMatches(fileA, true));
        
        // Include and exclude the file type, check that neither file nor folder pass
        params.includeTypes = new StringList("LeA.txt").toArray();
        params.excludeTypes = new StringList(params.includeTypes).toArray();
        assertFalse("The file passed a type check when the type was included and excluded", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a type check when the type was included and excluded", handler.filepathMatches(fileA, true));
        
        // Include and exclude the folder key, check the neither file nor folder pass
        params.includeTypes = new StringList("\\").toArray();
        params.excludeTypes = new StringList(params.includeTypes).toArray();
        assertFalse("The file passed a type check when the folder key was included and excluded", handler.filepathMatches(fileA, false));
        assertFalse("The folder passed a type check when the folder key was included and excluded", handler.filepathMatches(fileA, true));
    }
    
}
