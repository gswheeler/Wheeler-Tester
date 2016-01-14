/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.structs.istringlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import wheeler.generic.data.FileHandler;
import wheeler.generic.data.LogicHandler;
import wheeler.generic.data.StringHandler;
import wheeler.generic.structs.IStringList;
import wheeler.generic.structs.StringLinkNode;
import wheeler.generic.structs.StringSortedList;
import wheeler.generic.structs.TestStringList;
import wheeler.generic.structs.TestStringSortedList;
import wheelertester.data.DataFactory;
import wheelertester.data.TestFileHandler;

/**
 *
 * @author Greg
 */
public class AddContainsRemoveTest {
    
    @Before
    public void setup(){
        // The default case sensitivity
        StringHandler.stringSorter.caseSensitive = false;
    }
    
    @Test
    public void testAddingCheckingAndRemovingItems() throws Exception{
        testAddingCheckingAndRemovingItemsWorker(false);
        testAddingCheckingAndRemovingItemsWorker(true);
    }
    private void testAddingCheckingAndRemovingItemsWorker(boolean caseSensitive) throws Exception{
        // Set the current StringSorter to handle case sensitivity as appropriate
        StringHandler.stringSorter.caseSensitive = caseSensitive;
        
        // Get some test data (if we failed earlier, use the data that caused us to fail)
        String testFolder = TestFileHandler.callGetTestFolder(caseSensitive ? "withCase" : "noCase", 0);
        String[][] data = generateOrRetrieveTestData(testFolder, DataFactory.generateFiftyStrings(caseSensitive ? 2 : 1));
        
        // Get an array of test lists, run the test for each list using the same data
        IStringList[] lists = TestStringList.getStringLists();
        for (IStringList list : lists) testAddingCheckingAndRemovingItemsWorker(list, data, caseSensitive);
        
        // The tests all passed, delete the stored data so we start with a fresh set next time
        FileHandler.deleteFolder(testFolder);
    }
    
    @Test
    public void testLargeList() throws Exception{
        testLargeListWorker(false);
        testLargeListWorker(true);
    }
    private void testLargeListWorker(boolean caseSensitive) throws Exception{
        // Set the current StringSorter to handle case sensitivity as appropriate
        StringHandler.stringSorter.caseSensitive = caseSensitive;
        
        // Get some test data (if we failed earlier, use the data that caused us to fail)
        String testFolder = TestFileHandler.callGetTestFolder(caseSensitive ? "withCase" : "noCase", 0);
        String[][] data = generateOrRetrieveTestData(testFolder, DataFactory.generateTwoHundredStrings(caseSensitive ? 2 : 1));
        
        // Get an array of test lists, run the test for each list using the same data
        IStringList[] lists = TestStringList.getStringLists();
        for (IStringList list : lists) testAddingCheckingAndRemovingItemsWorker(list, data, caseSensitive);
        
        // The tests all passed, delete the stored data so we start with a fresh set next time
        FileHandler.deleteFolder(testFolder);
    }
    
    @Test
    public void testMassiveSortedList() throws Exception{
        // Get some test data (if we failed earlier, use the data that caused us to fail)
        String testFolder = TestFileHandler.callGetTestFolder(0);
        String[][] data = generateOrRetrieveTestData(testFolder, DataFactory.generateFiveThousandStrings(1));
        
        // StringSortedList is specially designed for handling enormous lists like these, since it has to do so while keeping the list sorted
        // Just use the base StringSortedList; calling isValid 10,000 times on a list with an average of 2,500 items is EXPENSIVE
        testAddingCheckingAndRemovingItemsWorker(new StringSortedList(), data, false);
        
        // The tests all passed, delete the stored data so we start with a fresh set next time
        FileHandler.deleteFolder(testFolder);
    }
    
    // Get test data from the test folder if it exists
    private String[][] generateOrRetrieveTestData(String testFolder, String[][] newData) throws Exception{
        // Make sure the folder exists, generate datafile paths
        FileHandler.ensureFolderExists(testFolder);
        String datafile1 = FileHandler.composeFilepath(testFolder, "data1.txt");
        String datafile2 = FileHandler.composeFilepath(testFolder, "data2.txt");
        String datafile3 = FileHandler.composeFilepath(testFolder, "data3.txt");
        String[][] data;
        
        // Check if data files exist from the last time the test was run
        if(FileHandler.fileExists(datafile1) && FileHandler.fileExists(datafile2) && FileHandler.fileExists(datafile3)){
            // If so, collect the data
            String[] data0 = FileHandler.readFile(datafile1, true, true).toArray();
            String[] data1 = FileHandler.readFile(datafile2, true, true).toArray();
            String[] data2 = FileHandler.readFile(datafile3, true, true).toArray();
            data = new String[3][]; data[0] = data0; data[1] = data1; data[2] = data2;
        }else{
            // Otherwise, use the new data, saving it to file in case the next test run needs it
            data = new String[3][];
            data[0] = newData[0]; data[1] = newData[1]; data[2] = LogicHandler.shuffleArray(data[0]);
            FileHandler.writeFile(data[0], true, datafile1);
            FileHandler.writeFile(data[1], true, datafile2);
            FileHandler.writeFile(data[2], true, datafile3);
        }
        
        // Return the generated or retrieved data
        return data;
    }
    
    // Do the test work
    private void testAddingCheckingAndRemovingItemsWorker(IStringList list, String[][] data, boolean caseSensitive) throws Exception{
        // De-reference the data in the data array
        String[] strings = data[0]; // The strings to be added to the list in turn
        String[] pool = data[1];    // All possible strings
        String[] remove = data[2];  // The strings we added, in the order in which to remove them
        
        // If appropriate, have a reference to the list cast as a TestStringSortedList
        TestStringSortedList sortedList = (list instanceof TestStringSortedList) ? ((TestStringSortedList)list) : null;
        
        // Make sure the list is empty
        assertTrue("The list reported not being empty before anything was added", list.isEmpty());
        assertFalse("The list reported having items before any were added", list.any());
        assertEquals("The list is empty but reports having items", 0, list.length());
        if (sortedList != null) assertTrue(sortedList.isValid(null));
        
        // Make sure each string does not appear in the list yet
        for(String item : pool){
            assertEquals("The string " + item + " is apparently already in the list", 0, list.count(item));
            assertFalse("The string " + item + " is apparently already to be found in the list", list.contains(item));
        }
        
        // Add each string to the list. Count the instances of each string and check that the new count matches
        int[] counts = new int[pool.length];
        for(int i = 0; i < strings.length; i++){
            list.add(strings[i]);
            if (sortedList != null) sortedList.isValid(null);
            for(int j = 0; j < pool.length; j++){
                if(StringHandler.areEqual(strings[i], pool[j], caseSensitive)){
                    counts[j]++;
                    assertEquals(
                            "The number of times " + strings[i] + " appears in the list failed to increase correctly",
                            counts[j],
                            list.count(strings[i])
                        );
                    assertTrue("The string " + strings[i] + " could not be found in the list", list.contains(strings[i]));
                    break;
                }
            }
            assertEquals("The wrong length was found", i+1, list.length());
            assertFalse("Failed to realize the list is no longer empty", list.isEmpty());
            assertTrue("Failed to realize the list now has values", list.any());
        }
        
        // For each string, make sure that it is found the correct number of times
        for(int i = 0; i < pool.length; i++){
            assertEquals("Count returned the wrong value", counts[i], list.count(pool[i]));
            assertEquals("Contains returned the wrong value", counts[i] > 0, list.contains(pool[i]));
        }
        
        // Remove the strings
        int count = remove.length;
        assertEquals("The number of items to remove did not match the total number of items", count, list.length());
        // If this is a sorted list, grab references to the nodes so we can verify they all get removed
        StringLinkNode[] sortedNodes = (sortedList != null) ? new StringLinkNode[count + 1] : null;
        if((sortedList != null) && (sortedNodes != null)){
            StringLinkNode node = sortedList.getHeader();
            for(int i = 0; i < sortedNodes.length; i++){
                sortedNodes[i] = node;
                node = node.next;
            }
        }
        for(String item : remove){
            for(int i = 0; i < pool.length; i++){
                if(StringHandler.areEqual(item, pool[i], caseSensitive)){
                    assertEquals("While removing " + item + ", found it the wrong number of times", counts[i], list.remove(item));
                    if (sortedList != null) sortedList.isValid(null);
                    assertEquals("Found a string in the list after removing it", 0, list.count(item));
                    assertFalse("Remove left a string in the list", list.contains(item));
                    count -= counts[i]; counts[i] = 0;
                    assertEquals("Failed to decrease the length of the list correctly", count, list.length());
                    assertEquals("isEmpty returned the wrong result", count == 0, list.isEmpty());
                    assertEquals("any returned the wrong result", count > 0, list.any());
                    break;
                }
            }
        }
        
        // Make sure the list is now empty
        assertEquals("The list still had items", 0, list.length());
        assertTrue("The emptied list said it wasn't empty", list.isEmpty());
        assertFalse("The emptied list still reported having items", list.any());
        for(String item : pool){
            assertEquals("A string was still found in the emptied list", 0, list.count(item));
            assertFalse("The emptied list claimed to still find a string", list.contains(item));
        }
        if(sortedNodes != null){
            for (int i = 0; i < sortedNodes.length; i++)
                assertEquals("Failed to mark nodes as removed as appropriate", i != 0, sortedNodes[i].wasRemoved());
        }
        
    }
    
}
