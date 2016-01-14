/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.data.mathhandler;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import wheeler.generic.data.FileHandler;
import wheeler.generic.data.MathHandler;
import wheeler.generic.data.StringHandler;
import wheeler.generic.data.TestFileHandler;
import wheeler.generic.data.TestMathHandler;

/**
 * Test our function for getting prime numbers
 */
public class GetPrimeTest {
    
    @Test
    public void testGetPrime() throws Exception{
        // We use a cache file when finding primes, thus we need to set a test folder
        String testFolder = TestFileHandler.getTestFolder(0);
        FileHandler.deleteFolder(testFolder);
        
        // Easy stuff; ask for primes in the list of primes we use to initialize the list (2 through 11)
        assertEquals("Failed to get the expected prime when asking for a prime 6 or greater",
                7, MathHandler.getPrime(6));
        assertEquals("Failed to get the expected prime when asking for a prime 7 or greater",
                7, MathHandler.getPrime(7));
        assertEquals("Failed to get the expected prime when asking for a prime 11 or greater",
                11, MathHandler.getPrime(11));
        
        // Harder stuff; ask for primes beyond the initialization list (13+)
        assertEquals("Failed to get the expected prime when asking for a prime 15 or greater",
                17, MathHandler.getPrime(15));
        assertEquals("Failed to get the expected prime when asking for a prime 17 or greater",
                17, MathHandler.getPrime(17));
        assertEquals("Failed to get the expected prime when asking for a prime 19 or greater",
                19, MathHandler.getPrime(19));
        
        // A check on our cache file; should have every prime before and including the greatest one we've asked for
        assertEquals("Failed to get the expected prime when asking for a prime 24 or greater",
                29, MathHandler.getPrime(24));
        assertEquals("Failed to get the expected prime when asking for a prime 12 or greater",
                13, MathHandler.getPrime(12));
        String[] expected = {"2", "3", "5", "7", "11", "13", "17", "19", "23", "29"};
        String[] actual = FileHandler.readFile(TestMathHandler.primeNumbersCache(), true, true).toArray();
        assertEquals("The cache file did not have the contents we were expecting",
                StringHandler.concatArray(expected, "\t"),
                StringHandler.concatArray(actual, "\t")
            );
        
        // Delete the test folder as cleanup
        FileHandler.deleteFolder(testFolder);
    }
    
}
