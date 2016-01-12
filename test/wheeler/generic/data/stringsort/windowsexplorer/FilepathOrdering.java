/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.data.stringsort.windowsexplorer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import wheeler.generic.data.stringsort.StringSorter;
import wheeler.generic.data.stringsort.WindowsExplorer;
import wheeler.generic.structs.StringSimpleList;

/**
 * Tests the WindowsExplorer StringSorter to make sure it sorts strings similar to how Windows sorts filepaths
 */
public class FilepathOrdering {
    
    // Taken from a folder in which we created a set of subfolders with the names shown below
    private final String[] observed = {
        "0",
        "A",
        "A B",
        "A!B",
        "A#B",
        "A$B",
        "A%B",
        "A&B",
        "A(B",
        "A)B",
        "A,B",
        "A.B",
        "A;B",
        "A@B",
        "A[B",
        "A]B",
        "A^B",
        "A_B",
        "A`B",
        "A{B",
        "A}B",
        "A~B",
        "A+B",
        "A=B",
        "A00",
        "A0",
        "A01",
        "A1",
        "A02",
        "A2",
        "A11B",
        "A101B",
        "AA",
        "AB",
        "A'B",
        "A''B",
        "A'-B",
        "A-B",
        "A-'B",
        "A--B",
        "ABC",
        "A'B'C",
        "A'B-C",
        "A-B'C",
        "A-B-C",
        "AC",
        "B"
    };
    
    @Test
    public void testWindowsOrdering(){
        // Easy test: just compare each subfolder name against the others
        testCompareOrderedList(observed, new WindowsExplorer(false));
    }
    
    @Test
    public void testDataOrdering(){
        // Test a few things pertaining to how folders are stored in a datafile when the datafile is sorted
        // 1. Subfolders are followed by subfolders that come after them
        // 2. A subfolder is followed by that subfolder plus a tab and data
        // 3. A subfolder plus tab and data is followed by that subfolder plus a divider character
        // 4. A subfolder plus a divider character is followed by that subfolder plus a divider character and more path segments
        
        // Some subfolder names for us to test with (already in order, of course)
        String[] datafileEntries = {
            "A",
            "A!",
            "A0",
            "AA",
            "AAA",
            "AZ",
            "A?",
            "A" + String.valueOf((char)252),
            "A" + String.valueOf((char)22797),
            "B"
        };
        
        // Get each subfolder name plus datafile data and subsequent filepaths
        StringSimpleList allEntries = new StringSimpleList();
        for(String entry : datafileEntries){
            allEntries.add(entry);
            allEntries.add(entry + "\tdata");
            allEntries.add(entry + "\\");
            allEntries.add(entry + "\\file");
        }
        
        // Run the test
        testCompareOrderedList(allEntries.toArray(), new WindowsExplorer(false));
    }
    
    @Test
    public void testCaseSensitivity(){
        // Get sorters with and without case sensitivity
        StringSorter sorterWith = new WindowsExplorer(true);
        StringSorter sorterWithout = new WindowsExplorer(false);
        
        // Cook up some data
        String[] caseA = {"1A2", "1b2", "1C2"};
        String[] caseB = {"1a2", "1B2", "1C2"};
        
        // Make the comparisons
        for(int i = 0; i < caseA.length; i++){
            for(int j = 0; j < caseB.length; j++){
                // If the two indexes are not equal, expect the result to reflect their difference
                if(i < j){
                    assertEquals(
                            "Did not get the expected comparison result when comparing " + caseA[i] + " and " + caseB[j] + " with case sensitivity",
                            -1, sorterWith.compareStrings(caseA[i], caseB[j])
                        );
                    assertEquals(
                            "Did not get the expected comparison result when comparing " + caseA[i] + " and " + caseB[j] + " without case sensitivity",
                            -1, sorterWithout.compareStrings(caseA[i], caseB[j])
                        );
                    continue;
                }
                if(i > j){
                    assertEquals(
                            "Did not get the expected comparison result when comparing " + caseA[i] + " and " + caseB[j] + " with case sensitivity",
                            1, sorterWith.compareStrings(caseA[i], caseB[j])
                        );
                    assertEquals(
                            "Did not get the expected comparison result when comparing " + caseA[i] + " and " + caseB[j] + " without case sensitivity",
                            1, sorterWithout.compareStrings(caseA[i], caseB[j])
                        );
                    continue;
                }
                
                // The same strings are being compared (aside from case), expect the results to reflect this
                assertEquals(
                        "Did not get the expected comparison result when comparing " + caseA[i] + " and " + caseB[j] + " without case sensitivity",
                        0, sorterWithout.compareStrings(caseA[i], caseB[j])
                );
                int expect;
                switch(i){
                    case 0:
                        expect = 1; // The first has a capital
                        break;
                    case 1:
                        expect = -1; // The first has a lowercase
                        break;
                    case 2:
                        expect = 0; // The cases match
                        break;
                    default:
                        expect = 505; // Something that will never be returned
                }
                assertEquals(
                        "Did not get the expected comparison result when comparing " + caseA[i] + " and " + caseB[j] + " with case sensitivity",
                        expect, sorterWith.compareStrings(caseA[i], caseB[j])
                );
            }
        }
    }
    
    private void testCompareOrderedList(String[] list, StringSorter sorter){
        for(int i = 0; i < list.length; i++){
            for(int j = 0; j < list.length; j++){
                // Get the comparison result
                int compareResult = sorter.compareStrings(list[i], list[j]);
                
                // Figure out what we're expecting from the comparison
                int expect = 0;
                if(i < j){
                    expect = -1;
                }else if(i > j){
                    expect = 1;
                }
                
                // Verify that the result is what we're expecting
                assertEquals(
                        "Did not get the expected comparison result when comparing "
                            + list[i] + " and " + list[j],
                        expect,
                        compareResult
                    );
            }
        }
    }
    
}
