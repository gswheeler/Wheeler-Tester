/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheelertester.data.datafactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import wheeler.generic.data.StringHandler;
import wheeler.generic.structs.StringList;
import wheelertester.data.DataFactory;

/**
 * Test our function for producing strings
 */
public class GenerateStringsTest {
    
    @Test
    public void basicTest(){
        // The strings we expect to be generated
        String[] expectedPossible = {
                "aa",
                "ab",
                "ac",
                "ba",
                "bb",
                "bc"
            };
        
        // Get strings
        String[][] result = DataFactory.generateStrings(getTwoAndThreeArray(), 15, true);
        String[] generated = result[0];
        String[] possible = result[1];
        
        // Check the list of possible strings
        assertEquals(
                "The expected and generated lists of possible strings were of different lengths",
                expectedPossible.length,
                possible.length
            );
        for(int i = 0; i < expectedPossible.length; i++){
            assertTrue("Found a mis-match between expected possibles and generated possibles",
                    StringHandler.areEqual(possible[i], expectedPossible[i], true)
                );
        }
        
        // Make sure each generated string is in the list of possible strings
        for(String str : generated){
            boolean found = false;
            for(String str2 : possible){
                if(StringHandler.areEqual(str, str2, false)){
                    found = true;
                    break;
                }
            }
            assertTrue("The generated string " + str + " was not in the list of possible strings", found);
        }
        
    }
    
    @Test
    public void checkRepeatedAndMissing(){
        int count = 5;
        while(true){
            // Get the data
            String[][] values = getTwoAndThreeArray();
            int number = 1; for (String[] valuesSet : values) number = number * valuesSet.length;
            String[][] data = DataFactory.generateStrings(values, number, false);
            assertEquals("This test requires there to be the chance of an absense of repetition/omission", data[0].length, data[1].length);
            StringList strings = new StringList(data[0]);
            
            // Look for a repeated string and a missing string
            boolean repeatedFound = false;
            boolean missingFound = false;
            for(String item : data[1]){
                int itemCount = strings.count(item);
                if (itemCount == 0) missingFound = true;
                if (itemCount > 1) repeatedFound = true;
                if (missingFound && repeatedFound) break;
            }
            if (missingFound && repeatedFound) break;
            
            // Don't fail on one bad run
            if (count-- < 0) fail("Failed to get a dataset with repeated/missing strings");
        }
    }
    
    @Test
    public void testCaseRandomization(){
        // Get a set of items with no randomization, make sure the case is unchanged
        String[] strings = DataFactory.generateStrings(getTwoAndTwoArray(), 20, false)[0];
        String[] noCasePool = {"aA", "ab", "BA", "Bb"};
        // For every generated string, make sure it shows up in the list of possible strings
        for(String str : strings){
            boolean found = false;
            for(String check : noCasePool){
                if(StringHandler.areEqual(str, check, true)){
                    found = true;
                    break;
                }
            }
            assertTrue("Generated " + str + " in spite of having case randomization off", found);
        }
        
        // Get a VERY large list with case randomization, make sure all permutations show up
        String[] casePool = {"ab", "aB", "Ab", "AB"};
        int count = 3;
        while(true){
            // Generate the strings
            strings = DataFactory.generateStrings(getOneAndOneArray(), 100, true)[0];
            
            // Make sure each case permutation shows up in the data set
            boolean allFound = true;
            for(String str : casePool){
                boolean found = false;
                for(String check : strings){
                    if(StringHandler.areEqual(str, check, true)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    allFound = false;
                    break;
                }
            }
            
            // If we found them all, we passed
            if (allFound) break;
            
            // Three strikes, you're out
            if (count-- < 0) fail("Failed to have sufficient randomization");
        }
        
    }
    
    
    // Return source arrays for generating strings
    protected String[][] getOneAndOneArray(){
        String[] index0 = {"a"};
        String[] index1 = {"B"};
        String[][] values = new String[2][];
        values[0] = index0;
        values[1] = index1;
        return values;
    }
    protected String[][] getTwoAndTwoArray(){
        String[] index0 = {"a", "B"};
        String[] index1 = {"A", "b"};
        String[][] values = new String[2][];
        values[0] = index0;
        values[1] = index1;
        return values;
    }
    protected String[][] getTwoAndThreeArray(){
        String[] index0 = {"a", "b"};
        String[] index1 = {"a", "b", "c"};
        String[][] values = new String[2][];
        values[0] = index0;
        values[1] = index1;
        return values;
    }
    
}
