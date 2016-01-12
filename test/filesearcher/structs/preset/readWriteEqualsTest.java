/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.structs.preset;

import filesearcher.data.TestFileHandler;
import filesearcher.structs.Preset;
import filesearcher.structs.TestPreset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import wheeler.generic.data.FileHandler;

/**
 * Write presets to file and make sure they are the same when read back. Also, make sure our Equals function works
 */
public class ReadWriteEqualsTest {
    
    @Test
    public void testEqualsAndReadWriteFile() throws Exception{
        // We're going to be creating presets files; set the ProgramFiles path
        String testFolder = TestFileHandler.getTestFolder(0);
        FileHandler.clearFolder(testFolder);
        
        // Create the two objects to compare
        TestPreset preset1 = new TestPreset();
        TestPreset preset2 = new TestPreset();
        assertTrue("Two newly-created parameters objects were not equal", preset1.equals(preset2));
        // Skip the file test here: can't do anything while values are still null
        
        // Put together some values and some alternate values
        String searchRootA = "This Is A Search Root";
        String searchRootB = "This Is Another Search Root";
        String filesA = " itemA  - -itemC ";
        String filesB = " itemB  - -itemD ";
        String pathsA = " itemE  - -itemG ";
        String pathsB = " itemF  - -itemH ";
        String typesA = " itemI  - -itemK ";
        String typesB = " itemJ  - -itemL ";
        String lineA = "This Is A Search String";
        String lineB = "This Is Another Search String";
        String excludeA = "This Is An Exclusion String";
        String excludeB = "This Is Another Exclusion String";
        
        // Put in values (the same values)
        preset1.searchRoot = searchRootA;   preset2.searchRoot = searchRootA;
        preset1.files = filesA;             preset2.files = filesA;
        preset1.paths = pathsA;             preset2.paths = pathsA;
        preset1.types = typesA;             preset2.types = typesA;
        preset1.searchLines = false;        preset2.searchLines = false;
        preset1.line = lineA;               preset2.line = lineA;
        preset1.excludeLines = false;       preset2.excludeLines = false;
        preset1.exclude = excludeA;         preset2.exclude = excludeA;
        preset1.hideLines = false;          preset2.hideLines = false;
        preset1.checkCase = false;          preset2.checkCase = false;
        preset1.useRegex = false;           preset2.useRegex = false;
        compare("being populated", preset1, preset2, true);
        
        // Change the search root
        preset1.searchRoot = searchRootB;
        compare("changing the search root", preset1, preset2, false);
        preset1.searchRoot = "";
        compare("clearing a search root", preset1, preset2, false);
        preset2.searchRoot = "";
        compare("clearing both search roots", preset1, preset2, true);
        preset1.searchRoot = searchRootB;
        compare("setting a search root", preset1, preset2, false);
        preset2.searchRoot = searchRootB;
        compare("setting both search roots", preset1, preset2, true);
        
        // Change the files line
        preset1.files = filesB;
        compare("changing the files line", preset1, preset2, false);
        preset1.files = "";
        compare("clearing a files line", preset1, preset2, false);
        preset2.files = "";
        compare("clearing both files lines", preset1, preset2, true);
        preset1.files = filesB;
        compare("setting a files line", preset1, preset2, false);
        preset2.files = filesB;
        compare("setting both files lines", preset1, preset2, true);
        
        // Change the paths line
        preset1.paths = pathsB;
        compare("changing the paths line", preset1, preset2, false);
        preset1.paths = "";
        compare("clearing a paths line", preset1, preset2, false);
        preset2.paths = "";
        compare("clearing both paths lines", preset1, preset2, true);
        preset1.paths = pathsB;
        compare("setting a paths line", preset1, preset2, false);
        preset2.paths = pathsB;
        compare("setting both paths lines", preset1, preset2, true);
        
        // Change the types line
        preset1.types = typesB;
        compare("changing the types line", preset1, preset2, false);
        preset1.types = "";
        compare("clearing a types line", preset1, preset2, false);
        preset2.types = "";
        compare("clearing both types lines", preset1, preset2, true);
        preset1.types = typesB;
        compare("setting a types line", preset1, preset2, false);
        preset2.types = typesB;
        compare("setting both types lines", preset1, preset2, true);
        
        // Change the search line, set search-lines
        preset1.line = lineB;
        compare("changing a search line", preset1, preset2, false);
        preset1.line = "";
        compare("clearing a search line", preset1, preset2, false);
        preset2.line = "";
        compare("clearing both search lines", preset1, preset2, true);
        preset1.searchLines = true;
        compare("setting a search-lines", preset1, preset2, false);
        preset2.searchLines = true;
        compare("setting both search-lines", preset1, preset2, true);
        preset1.line = lineB;
        compare("setting a search line", preset1, preset2, false);
        preset2.line = lineA;
        compare("setting the other search line", preset1, preset2, false);
        preset2.line = lineB;
        compare("setting both search lines", preset1, preset2, true);
        
        // Change the exclusion line, set exclude-lines
        preset1.exclude = lineB;
        compare("changing a exclusion line", preset1, preset2, false);
        preset1.exclude = "";
        compare("clearing an exclusion line", preset1, preset2, false);
        preset2.exclude = "";
        compare("clearing both exclusion lines", preset1, preset2, true);
        preset1.excludeLines = true;
        compare("setting an exclude-lines", preset1, preset2, false);
        preset2.excludeLines = true;
        compare("setting both exclude-lines", preset1, preset2, true);
        preset1.exclude = lineB;
        compare("setting an exclusion line", preset1, preset2, false);
        preset2.exclude = lineA;
        compare("setting the other exclusion line", preset1, preset2, false);
        preset2.exclude = lineB;
        compare("setting both exclusion lines", preset1, preset2, true);
        
        // Set the hide-lines
        preset1.hideLines = true;
        compare("setting a hide-lines", preset1, preset2, false);
        preset2.hideLines = true;
        compare("setting both hide-lines", preset1, preset2, true);
        
        // Set the check-case
        preset1.checkCase = true;
        compare("setting a check-case", preset1, preset2, false);
        preset2.checkCase = true;
        compare("setting both check-cases", preset1, preset2, true);
        
        // Set the use-regex
        preset1.useRegex = true;
        compare("setting a use-regex", preset1, preset2, false);
        preset2.useRegex = true;
        compare("setting both use-regexes", preset1, preset2, true);
        
        // Clean up after the test
        FileHandler.deleteFolder(testFolder);
    }
    
    private void compare(String testCase, TestPreset preset1, TestPreset preset2, boolean expectEqual) throws Exception{
        // Compare the two Presets objects, make sure they are or are not equal as expected
        String message = "After " + testCase + " the Presets objects were"
                + ((expectEqual) ? " not" : "") + " equal";
        assertEquals(message, expectEqual, preset1.equals(preset2));
        
        // Write the two Presets objects to file, make sure reading said files produces Presets objects that match the originals
        message = "After " + testCase + " the Presets objects were incorrectly read from file";
        String name1 = "session1"; String name2 = "session2";
        preset1.writeToFile(name1, null);
        assertTrue(message, preset1.equals(Preset.fromPresetFile(name1)));
        preset2.writeToFile(name2, null);
        assertTrue(message, preset2.equals(Preset.fromPresetFile(name2)));
    }
    
}
