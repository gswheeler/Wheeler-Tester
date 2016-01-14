/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.structs.stringtostringdictionary;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import wheeler.generic.data.FileHandler;
import wheeler.generic.data.StringHandler;
import wheeler.generic.structs.StringToStringDictionary;
import wheelertester.data.DataFactory;
import wheelertester.data.TestFileHandler;

/**
 * Test StringToStringDictionary's get and set functionalities
 */
public class SetGetTest {
    
    private static boolean caseSensitivity;
    
    @BeforeClass
    public static void setupClass(){
        caseSensitivity = StringHandler.stringSorter.caseSensitive;
    }
    
    @AfterClass
    public static void teardownClass(){
        StringHandler.stringSorter.caseSensitive = caseSensitivity;
    }
    
    @Test
    public void testSetAndGet() throws Exception{
        // Data
        String specialKey1 = "d\rd"; // A special ASCII character
        String specialKey2 = "h" + String.valueOf((char)252) + "h"; // An extended-ASCII character
        String specialKey3 = "j" + String.valueOf((char)22797) + "j"; // A unicode character
        String[] keys = {
                "aaa",
                "b\tb",
                "ccc",
                specialKey1,
                StringHandler.getPrintable(specialKey1, false, false), // Special-character removed
                StringHandler.getPrintable(specialKey1, false, true),  // The "readable" version of the character ("\\r")
                StringHandler.escape(specialKey1), // Put through escape
                "eee",
                "f\nf",
                "ggg",
                specialKey2,
                StringHandler.getPrintable(specialKey2, false, false), // Special character removed
                StringHandler.getPrintable(specialKey2, false, true),  // Made printable (%fc)
                StringHandler.escape(specialKey2), // Put through escape (%252)
                "iii",
                specialKey3,
                StringHandler.getPrintable(specialKey3, false, false), // Special character removed
                StringHandler.getPrintable(specialKey3, false, true),  // Made printable (x590d)
                StringHandler.escape(specialKey3), // Put through escape (&22797)
                "kkk"
            };
        String[] data1 = new String[keys.length];
        String[] data2 = new String[keys.length];
        for(int i = 0; i < keys.length; i++){
            String padding = StringHandler.toAlphanumeric(10 + i);
            data1[i] = padding + "1" + keys[i] + padding;
            data2[i] = padding + "2" + keys[i] + padding;
        }
        
        // Randomized data; store this in the event that the test fails
        String testFolder = TestFileHandler.callGetTestFolder(0);
        FileHandler.ensureFolderExists(testFolder);
        String datafile = FileHandler.composeFilepath(testFolder, "insertOrder.txt");
        String[] insertOrderStrings;
        if(FileHandler.fileExists(datafile)){
            // Grab the order from the datafile
            insertOrderStrings = FileHandler.readFile(datafile, true, true).toArray();
        }else{
            // Generate the order, convert to strings, store in datafile
            int[] order = DataFactory.generateRandomOrder(keys.length);
            insertOrderStrings = new String[order.length];
            for (int i = 0; i < order.length; i++) insertOrderStrings[i] = Integer.toString(order[i]);
            FileHandler.writeFile(insertOrderStrings, true, datafile);
        }
        // Get the numbers as integers
        int[] insertOrder = new int[insertOrderStrings.length];
        for (int i = 0; i < insertOrderStrings.length; i++)
            insertOrder[i] = Integer.valueOf(insertOrderStrings[i]);
        
        // The star of the show
        StringToStringDictionary dict = new StringToStringDictionary(3);
        
        // Insert the data into the dictionary
        // After each insertion, make sure each key returns the expected value
        for(int i = 0; i < insertOrder.length + 1; i++){
            // Check each key; if it's been inserted, expect the value. Otherwise, expect null
            for(int j = 0; j < insertOrder.length; j++){
                String value = dict.get(keys[insertOrder[j]]);
                if(j < i){
                    assertNotNull("Failed to get a value for key "
                                + StringHandler.getPrintable(keys[j], false, true) + ".",
                            value
                        );
                    assertEquals("Did not get the expected value for key "
                                + StringHandler.getPrintable(keys[j], false, true) + ".",
                            data1[insertOrder[j]],
                            value
                        );
                }else{
                    assertNull("Found a value for the key "
                                + StringHandler.getPrintable(keys[j], true, true)
                                + ", which hasn't had a value inserted yet",
                            value
                        );
                }
            }
            
            // While we're at it, check the count() function
            assertEquals("The dictionary does not contain the right number of entries",
                    i,
                    dict.count()
                );
            
            // If there are values left to insert, do so
            if(i < keys.length){
                assertNull("Setting the value of a key that did not have one did not return a null",
                        dict.set(keys[insertOrder[i]], data1[insertOrder[i]])
                    );
            }
        }
        
        // Remove each value in turn, checking that the right entries get removed
        for(int i = 0; i < keys.length; i++){
            // Remove the key's value
            assertTrue("Clear did not indicate that there was an entry for the key that was then removed",
                    dict.clear(keys[i])
                );
            
            // Make sure we have the right number of entries left in the dictionary
            assertEquals("The dictionary did not contain the right number of entries after removing one",
                    keys.length - 1 - i,
                    dict.count()
                );
            
            // Make sure querying the keys returns values as expected
            for(int j = 0; j < keys.length; j++){
                String value = dict.get(keys[j]);
                if(j > i){
                    assertNotNull("Failed to get a value for a key that had not yet been removed", value);
                    assertEquals("Did not get the expected value for a key", data1[j], value);
                }else{
                    assertNull("Found a value for a key that had been removed", value);
                }
            }
            
            // Call the clear function again
            assertFalse("Clear did not indicate that there was no entry for the key to be removed",
                    dict.clear(keys[i])
                );
            
            // Make sure the number of entries has not changed
            assertEquals("The dictionary did not contain the right number of entries after clearing one that was no longer there",
                    keys.length - 1 - i,
                    dict.count()
                );
        }
        
        // Assert that the dictionary is now empty
        assertEquals("The dictionary was not emptied", 0, dict.count());
        
        // Put the key/value pairs back into the dictionary
        for(int i = 0; i < insertOrder.length; i++){
            assertNull("Re-setting a value after it had been cleared did not return null",
                    dict.set(keys[insertOrder[i]], data1[insertOrder[i]])
                );
        }
        assertEquals("Failed to properly populate the dictionary", keys.length, dict.count());
        
        // Re-set the key values, make sure we replace rather than just re-insert
        for(int i = 0; i < keys.length; i++){
            assertEquals("Replacing a key's value did not return what should have been the old value",
                    data1[i],
                    dict.set(keys[i], data2[i])
                );
            assertEquals("Re-setting a key/value pair was not supposed to change the number of entries",
                    keys.length,
                    dict.count()
                );
            for(int j = 0; j < keys.length; j++){
                String value = dict.get(keys[j]);
                if(j > i){
                    assertNotNull("Failed to get a value for a key that had not yet been re-set", value);
                    assertEquals("Did not get the right value for a key that had not been re-set", data1[j], value);
                }else{
                    assertNotNull("Failed to get a value for a key that was re-set", value);
                    assertEquals("Did not get the right value for a key that was re-set", data2[j], value);
                }
            }
        }
        
        // Clear again, make sure nothing gets left behind
        for(int i = 0; i < keys.length; i++){
            assertTrue("Clearing a set value did not return true", dict.clear(keys[keys.length - 1 - i]));
            assertEquals("The dictionary did not contain the right number of entries after removing one",
                    keys.length - 1 - i,
                    dict.count()
                );
            for(int j = 0; j < keys.length; j++){
                String value = dict.get(keys[keys.length - 1 - j]);
                if(j > i){
                    assertNotNull("Failed to get a value for a key that had not yet been removed", value);
                    assertEquals("Did not get the expected value for a key", data2[keys.length - 1 - j], value);
                }else{
                    assertNull("Found a value for a key that had been removed", value);
                }
            }
        }
        assertEquals("The dictionary was not emptied", 0, dict.count());
        
        // That's it for basic behavior. Delete any test files
        FileHandler.deleteFolder(testFolder);
    }
    
    @Test
    public void testCase(){
        // Two keys, different only by case
        String key1 = "abc";
        String key2 = "AbC";
        String value1 = "String1";
        String value2 = "2ndValue";
        StringToStringDictionary dict = new StringToStringDictionary(1);
        
        // Remove case sensitivity, verify both keys set, get, and clear the same values
        StringHandler.stringSorter.caseSensitive = false;
        // Set the value using the first case
        assertNull("First set did not get a null", dict.set(key1, value1));
        // Call get using both cases
        assertNotNull("The key's value was not set", dict.get(key1));
        assertEquals("The key's value was not set correctly", value1, dict.get(key1));
        assertNotNull("Getting using changed-case failed to find the old value", dict.get(key2));
        assertEquals("Getting using changed-case found the wrong value", value1, dict.get(key2));
        // Set the value using the second case
        String value = dict.set(key2, value2);
        assertNotNull("Setting using changed-case failed to find the old value", value);
        assertEquals("Setting using changed-case found the wrong value", value1, value);
        // Call get again using both cases
        assertNotNull("The key's value was not set", dict.get(key1));
        assertEquals("The key's value was not set correctly", value2, dict.get(key1));
        assertNotNull("Getting using changed-case failed to find the new value", dict.get(key2));
        assertEquals("Getting using changed-case found the wrong value", value2, dict.get(key2));
        // Call clear using the first case
        assertTrue("Calling clear using the first case failed to find the value", dict.clear(key1));
        assertFalse("Calling clear again using the second case found something", dict.clear(key2));
        // Go through the "set" motions again, call clear using the second case
        assertNull("First set after clear did not get a null", dict.set(key1, value1));
        value = dict.set(key2, value2);
        assertNotNull("Setting using changed-case failed to find the old value", value);
        assertEquals("Setting using changed-case found the wrong value", value1, value);
        assertTrue("Calling clear using the second case failed to find the value", dict.clear(key2));
        assertFalse("Calling clear again using the first case found something", dict.clear(key1));
        
        // Set case sensitivity, verify the two keys set, get, and clear different values
        dict = new StringToStringDictionary(1);
        StringHandler.stringSorter.caseSensitive = true;
        // Set the two values
        assertNull("First set with the first case did not return null", dict.set(key1, value1));
        assertNull("Getting using different-case before it was given a value still found something", dict.get(key2));
        assertNull("First set with the second case did not return null", dict.set(key2, value2));
        // Get the two values
        assertNotNull("Getting using the first case failed to find a value", dict.get(key1));
        assertEquals("Getting using the first case found the wrong value", value1, dict.get(key1));
        assertNotNull("Getting using different-case failed to find a value", dict.get(key2));
        assertEquals("Getting using different-case found the wrong value", value2, dict.get(key2));
        // Clear the two values
        assertTrue("Calling clear using the first case failed to find the value", dict.clear(key1));
        assertNull("Calling get using the first case after clear still found a value", dict.get(key1));
        assertNotNull("Getting using different-case after clear failed to find a value", dict.get(key2));
        assertEquals("Getting using different-case after clear found the wrong value", value2, dict.get(key2));
        assertTrue("Calling clear using different-case failed to find the value", dict.clear(key2));
        assertNull("Calling get using the first case after clear still found a value", dict.get(key1));
        assertNull("Calling get using different-case after clear still found a value", dict.get(key2));
        // Go through the "set" motions again, clear the two values in the other order
        assertNull("First set with the first case after clear did not return null", dict.set(key1, value1));
        assertNull("First set with the second case after clear did not return null", dict.set(key2, value2));
        assertTrue("Calling clear using the second case failed to find the value", dict.clear(key2));
        assertNull("Calling get using the second case after clear still found a value", dict.get(key2));
        assertNotNull("Getting using different-case after clear failed to find a value", dict.get(key1));
        assertEquals("Getting using different-case after clear found the wrong value", value1, dict.get(key1));
        assertTrue("Calling clear using different-case failed to find the value", dict.clear(key1));
        assertNull("Calling get using different-case after clear still found a value", dict.get(key1));
        assertNull("Calling get using the second case after both clears still found a value", dict.get(key2));
        
        
    }
    
}
