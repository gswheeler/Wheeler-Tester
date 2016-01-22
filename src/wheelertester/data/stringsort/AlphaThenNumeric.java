/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheelertester.data.stringsort;

/**
 * A basic stringsorter that places alphabetic characters before numeric ones
 */
public class AlphaThenNumeric extends wheeler.generic.data.stringsort.StringSorter{
    
    public AlphaThenNumeric(){
        super(false, false);
    }
    
    @Override
    protected int compareStringsAfterCase(String strA, String strB){
        // Loop until we have an answer
        while(true){
            // Check if a string has run out
            if ((strA.length() == 0) && (strB.length() == 0)) return 0;
            if (strA.length() == 0) return -1;
            if (strB.length() == 0) return 1;
            
            // Grab the lexicographic indexes of the two strings
            int indexA = precedence.indexOf(strA.substring(0, 1));
            int indexB = precedence.indexOf(strB.substring(0, 1));
            
            // Compare
            if (indexA < indexB) return -1;
            if (indexA > indexB) return 1;
            
            // Remove first character and loop
            strA = strA.substring(1);
            strB = strB.substring(1);
            
        }
    }
    
    private final String precedence = "abcdefghijklmnopqrstuvwxyz0123456789";
    
}
