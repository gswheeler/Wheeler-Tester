/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.data.stringhandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import wheeler.generic.data.StringHandler;
import wheelertester.data.stringsort.AlphaThenNumeric;
import wheelertester.data.stringsort.NumericThenAlpha;

/**
 * Makes sure that sortstrings sorts the array as we expect
 */
public class SortStringsTest {
    
    private final String[] dataset = {
        // a/b and 1/2, then again in a different order (make case different)
        "a1",
        "B1",
        "a2",
        "B2",
        "1a",
        "1B",
        "2a",
        "2B",
        "A1",
        "A2",
        "b1",
        "b2",
        "1A",
        "2A",
        "1b",
        "2b"
    };
    private final String[] numbersFirst = {
        "1a",
        "1A",
        "1B",
        "1b",
        "2a",
        "2A",
        "2B",
        "2b",
        "a1",
        "A1",
        "a2",
        "A2",
        "B1",
        "b1",
        "B2",
        "b2"
    };
    private final String[] lettersFirst = {
        "a1",
        "A1",
        "a2",
        "A2",
        "B1",
        "b1",
        "B2",
        "b2",
        "1a",
        "1A",
        "1B",
        "1b",
        "2a",
        "2A",
        "2B",
        "2b"
    };
    
    
    @Test
    public void testSortStrings(){
        // The data array
        int index = 0;
        String[] input = new String[dataset.length];
        for (String item : dataset) input[index++] = item;
        
        // Test first with "numbers before letters"
        String[] output = StringHandler.sortStrings(input, new NumericThenAlpha());
        
        // Make sure the input hasn't changed and the output is what we expect
        assertEquals("The input array changed in size", input.length, dataset.length);
        assertEquals("The output array is not the same size as the input array", input.length, output.length);
        for(int i = 0; i < dataset.length; i++){
            assertEquals("An item in the input array was changed", dataset[i], input[i]);
            assertEquals("The output array had an item we weren't expecting", numbersFirst[i], output[i]);
        }
        
        // Now test with "letters before numbers"
        output = StringHandler.sortStrings(input, new AlphaThenNumeric());
        
        // Make sure the input hasn't changed and the output is what we expect
        assertEquals("The input array changed in size", input.length, dataset.length);
        assertEquals("The output array is not the same size as the input array", input.length, output.length);
        for(int i = 0; i < dataset.length; i++){
            assertEquals("An item in the input array was changed", dataset[i], input[i]);
            assertEquals("The output array had an item we weren't expecting", lettersFirst[i], output[i]);
        }
        
        // Now pass in a sorted array and make sure we aren't just handed back the array's instance
        output = StringHandler.sortStrings(numbersFirst, new NumericThenAlpha());
        
        // Change an item in the output array, make sure the same item in the input array did not change
        output[0] = "33CD44";
        assertFalse("The input and output arrays are the same instance", StringHandler.areEqual(numbersFirst[0], output[0]));
    }
    
}
