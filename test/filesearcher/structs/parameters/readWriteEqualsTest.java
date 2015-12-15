/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.structs.parameters;

import filesearcher.data.FileHandler;
import filesearcher.data.TestFileHandler;
import filesearcher.structs.Parameters;
import filesearcher.structs.TestParameters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import wheeler.generic.structs.StringList;

/**
 *
 * @author Greg
 */
public class readWriteEqualsTest {
    
    @Test
    public void testEqualsAndReadWriteFile() throws Exception{
        // We're going to be creating parameters files; set the ProgramFiles path
        String testFolder = TestFileHandler.getTestFolder(0);
        FileHandler.clearFolder(testFolder);
        
        // Create the two objects to compare
        TestParameters params1 = new TestParameters();
        TestParameters params2 = new TestParameters();
        assertTrue("Two newly-created parameters objects were not equal", params1.equals(params2));
        // Skip the file test here: can't do anything while values are still null
        
        // Put together some values and some alternate values
        String searchRootA = "This Is A Search Root";
        String searchRootB = "This Is Another Search Root";
        StringList includeFilesA = new StringList("").add("itemA").add("").add("-").add("-itemC").add("");
        StringList includeFilesB = new StringList("").add("itemB").add("").add("-").add("-itemD").add("");
        StringList includePathsA = new StringList("").add("itemE").add("").add("-").add("-itemG").add("");
        StringList includePathsB = new StringList("").add("itemF").add("").add("-").add("-itemH").add("");
        StringList includeTypesA = new StringList("").add("itemI").add("").add("-").add("-itemK").add("");
        StringList includeTypesB = new StringList("").add("itemJ").add("").add("-").add("-itemL").add("");
        StringList excludeFilesA = new StringList("").add("itemM").add("").add("-").add("-itemO").add("");
        StringList excludeFilesB = new StringList("").add("itemN").add("").add("-").add("-itemP").add("");
        StringList excludePathsA = new StringList("").add("itemQ").add("").add("-").add("-itemS").add("");
        StringList excludePathsB = new StringList("").add("itemR").add("").add("-").add("-itemT").add("");
        StringList excludeTypesA = new StringList("").add("itemU").add("").add("-").add("-itemW").add("");
        StringList excludeTypesB = new StringList("").add("itemV").add("").add("-").add("-itemX").add("");
        String lineA = "This Is A Search String";
        String lineB = "This Is Another Search String";
        String excludeA = "This Is An Exclusion String";
        String excludeB = "This Is Another Exclusion String";
        
        // Put in values (the same values)
        params1.searchRoot = searchRootA;               params2.searchRoot = searchRootA;
        params1.includeFiles = includeFilesA.toArray(); params2.includeFiles = includeFilesA.toArray();
        params1.includePaths = includePathsA.toArray(); params2.includePaths = includePathsA.toArray();
        params1.includeTypes = includeTypesA.toArray(); params2.includeTypes = includeTypesA.toArray();
        params1.excludeFiles = excludeFilesA.toArray(); params2.excludeFiles = excludeFilesA.toArray();
        params1.excludePaths = excludePathsA.toArray(); params2.excludePaths = excludePathsA.toArray();
        params1.excludeTypes = excludeTypesA.toArray(); params2.excludeTypes = excludeTypesA.toArray();
        params1.searchLines = false;                    params2.searchLines = false;
        params1.line = lineA;                           params2.line = lineA;
        params1.excludeLines = false;                   params2.excludeLines = false;
        params1.exclude = excludeA;                     params2.exclude = excludeA;
        params1.hideLines = false;                      params2.hideLines = false;
        params1.checkCase = false;                      params2.checkCase = false;
        params1.useRegex = false;                       params2.useRegex = false;
        compare("being populated", params1, params2, true);
        
        // Change the search root
        params1.searchRoot = searchRootB;
        compare("changing the search root", params1, params2, false);
        params1.searchRoot = "";
        compare("clearing a search root", params1, params2, false);
        params2.searchRoot = "";
        compare("clearing both search roots", params1, params2, true);
        params1.searchRoot = searchRootB;
        compare("setting a search root", params1, params2, false);
        params2.searchRoot = searchRootB;
        compare("setting both search roots", params1, params2, true);
        
        // Change the included files
        params1.includeFiles = includeFilesB.toArray();
        compare("changing the included files", params1, params2, false);
        params1.includeFiles = new String[0];
        compare("clearing an included files set", params1, params2, false);
        params2.includeFiles = new String[0];
        compare("clearing both included files sets", params1, params2, true);
        params1.includeFiles = includeFilesB.toArray();
        compare("setting an included files set", params1, params2, false);
        params2.includeFiles = includeFilesB.toArray();
        compare("setting both included files sets", params1, params2, true);
        
        // Change the included paths
        params1.includePaths = includePathsB.toArray();
        compare("changing the included paths", params1, params2, false);
        params1.includePaths = new String[0];
        compare("clearing an included paths set", params1, params2, false);
        params2.includePaths = new String[0];
        compare("clearing both included paths sets", params1, params2, true);
        params1.includePaths = includePathsB.toArray();
        compare("setting an included paths set", params1, params2, false);
        params2.includePaths = includePathsB.toArray();
        compare("setting both included paths sets", params1, params2, true);
        
        // Change the included types
        params1.includeTypes = includeTypesB.toArray();
        compare("changing the included types", params1, params2, false);
        params1.includeTypes = new String[0];
        compare("clearing an included types set", params1, params2, false);
        params2.includeTypes = new String[0];
        compare("clearing both included types sets", params1, params2, true);
        params1.includeTypes = includeTypesB.toArray();
        compare("setting an included types set", params1, params2, false);
        params2.includeTypes = includeTypesB.toArray();
        compare("setting both included types sets", params1, params2, true);
        
        // Change the excluded files
        params1.excludeFiles = excludeFilesB.toArray();
        compare("changing the excluded files", params1, params2, false);
        params1.excludeFiles = new String[0];
        compare("clearing an excluded files set", params1, params2, false);
        params2.excludeFiles = new String[0];
        compare("clearing both excluded files sets", params1, params2, true);
        params1.excludeFiles = excludeFilesB.toArray();
        compare("setting an excluded files set", params1, params2, false);
        params2.excludeFiles = excludeFilesB.toArray();
        compare("setting both excluded files sets", params1, params2, true);
        
        // Change the excluded paths
        params1.excludePaths = excludePathsB.toArray();
        compare("changing the excluded paths", params1, params2, false);
        params1.excludePaths = new String[0];
        compare("clearing an excluded paths set", params1, params2, false);
        params2.excludePaths = new String[0];
        compare("clearing both excluded paths sets", params1, params2, true);
        params1.excludePaths = excludePathsB.toArray();
        compare("setting an excluded paths set", params1, params2, false);
        params2.excludePaths = excludePathsB.toArray();
        compare("setting both excluded paths sets", params1, params2, true);
        
        // Change the excluded types
        params1.excludeTypes = excludeTypesB.toArray();
        compare("changing the excluded types", params1, params2, false);
        params1.excludeTypes = new String[0];
        compare("clearing an excluded types set", params1, params2, false);
        params2.excludeTypes = new String[0];
        compare("clearing both excluded types sets", params1, params2, true);
        params1.excludeTypes = excludeTypesB.toArray();
        compare("setting an excluded types set", params1, params2, false);
        params2.excludeTypes = excludeTypesB.toArray();
        compare("setting both excluded types sets", params1, params2, true);
        
        // Change the search line
        params1.searchLines = true;
        compare("setting a line-search", params1, params2, false);
        params2.searchLines = true;
        compare("setting both line-searches", params1, params2, true);
        params1.line = lineB;
        compare("changing a search line", params1, params2, false);
        params1.line = "";
        compare("clearing a search line", params1, params2, false);
        params2.line = "";
        compare("clearing both search lines", params1, params2, true);
        params1.line = lineB;
        compare("setting a search line", params1, params2, false);
        params2.line = lineB;
        compare("setting both search lines", params1, params2, true);
        
        // Change the exclude line
        params1.excludeLines = true;
        compare("setting a line-exclude", params1, params2, false);
        params2.excludeLines = true;
        compare("setting both line-excludes", params1, params2, true);
        params1.exclude = excludeB;
        compare("changing an exclude line", params1, params2, false);
        params1.exclude = "";
        compare("clearing an exclude line", params1, params2, false);
        params2.exclude = "";
        compare("clearing both exclude lines", params1, params2, true);
        params1.exclude = excludeB;
        compare("setting an exclude line", params1, params2, false);
        params2.exclude = excludeB;
        compare("setting both exclude lines", params1, params2, true);
        
        // Set hide-lines
        params1.hideLines = true;
        compare("setting a hide-lines", params1, params2, false);
        params2.hideLines = true;
        compare("setting both hide-lines", params1, params2, true);
        
        // Set check-case
        params1.checkCase = true;
        compare("setting a check-case", params1, params2, false);
        params2.checkCase = true;
        compare("setting both check-cases", params1, params2, true);
        
        // Set use-regex
        params1.useRegex = true;
        compare("setting a use-regex", params1, params2, false);
        params2.useRegex = true;
        compare("setting both use-regex", params1, params2, true);
        
        // Cleanup
        FileHandler.deleteFolder(testFolder);
        
    }
    
    private void compare(String testCase, TestParameters params1, TestParameters params2, boolean expectEqual) throws Exception{
        // Compare the two Parameters objects, make sure they are or are not equal as expected
        String message = "After " + testCase + " the Parameters objects were"
                + ((expectEqual) ? " not" : "") + " equal";
        assertEquals(message, expectEqual, params1.equals(params2));
        
        // Write the two Parameters objects to file, make sure reading said files produces Parameters objects that match the originals
        message = "After " + testCase + " the Parameters objects were incorrectly read from file";
        params1.writeParamsFile();
        assertTrue(message, params1.equals(Parameters.getParametersFromFile()));
        params2.writeParamsFile();
        assertTrue(message, params2.equals(Parameters.getParametersFromFile()));
    }
    
}
