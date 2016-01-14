/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheelertester.data;

import wheeler.generic.data.LogicHandler;
import wheeler.generic.data.MathHandler;
import wheeler.generic.structs.StringSimpleList;

/**
 * Contains functions for generating data
 */
public class DataFactory {
    
    /**Generate fifty strings. There will likely be some possible strings that don't get created and there WILL be duplicates
     * @param casingOption How should case be handled? 0 = no case action. 1 = randomize case in generated strings. 2 = randomize case in generated strings and take case into account when generating possibles list.
     * @return A two-item array of arrays of Strings; the first will be the generated strings, the second will be all possible strings created or otherwise
     */
    public static String[][] generateFiftyStrings(int casingOption){
        // The data that will be present in the strings
        // With each position having two, two, three, and four values, there are 48 possibilities
        String[] index0 = {"a", "b"};
        String[] index1 = {"a", "b"};
        String[] index2 = {"a", "b", "c"};
        String[] index3 = {"a", "b", "c", "d"};
        String[][] values = new String[4][];
        values[0] = index0;
        values[1] = index1;
        values[2] = index2;
        values[3] = index3;
        
        return generateStrings(values, 50, casingOption);
    }
    
    /**Generate 200 strings with a pool of 128 possible strings
     * @param casingOption How should case be handled? 0 = no case action. 1 = randomize case in generated strings. 2 = randomize case in generated strings and take case into account when generating possibles list.
     * @return A two-item array of arrays of Strings; the first will be the generated strings, the second will be all possible strings created or otherwise
     */
    public static String[][] generateTwoHundredStrings(int casingOption){
        // The data that will be present in the strings
        String[] index0 = {"a", "b"};           // 2
        String[] index1 = {"a", "b", "c", "d"}; // 8
        String[] index2 = {"a", "b", "c", "d"}; // 32
        String[] index3 = {"a", "b", "c", "d"}; // 128
        String[][] values = new String[4][];
        values[0] = index0;
        values[1] = index1;
        values[2] = index2;
        values[3] = index3;
        
        return generateStrings(values, 200, casingOption);
    }
    
    /**Generate 5000 strings with a pool of 9375 possible strings
     * @param casingOption How should case be handled? 0 = no case action. 1 = randomize case in generated strings. 2 = randomize case in generated strings and take case into account when generating possibles list.
     * @return A two-item array of arrays of Strings; the first will be the generated strings, the second will be all possible strings created or otherwise
     */
    public static String[][] generateFiveThousandStrings(int casingOption){
        // The data that will be present in the strings
        String[] index0 = {"a", "b", "c"};           //    3
        String[] index1 = {"a", "b", "c", "d", "e"}; //   15
        String[] index2 = {"a", "b", "c", "d", "e"}; //   75
        String[] index3 = {"a", "b", "c", "d", "e"}; //  375
        String[] index4 = {"a", "b", "c", "d", "e"}; // 1875
        String[] index5 = {"a", "b", "c", "d", "e"}; // 9375
        String[][] values = new String[6][];
        values[0] = index0;
        values[1] = index1;
        values[2] = index2;
        values[3] = index3;
        values[4] = index4;
        values[5] = index5;
        
        return generateStrings(values, 5000, casingOption);
    }
    
    
    /**Generate strings according to the specified parameters.
     * @param values An array of arrays of strings; each array will have the values present at that index of the string (values[1] at str[1])
     * @param number The number of strings to generate
     * @param casingOption How should case be handled? 0 = no case action. 1 = randomize case in generated strings. 2 = randomize case in generated strings and take case into account when generating possibles list.
     * @return A two-item array of arrays of Strings; the first will be the generated strings, the second will be all possible strings created or otherwise
     */
    public static String[][] generateStrings(String[][] values, int number, int casingOption){
        // Generate all possible values
        String[] possibles = {""};
        // Iterate for each index position
        for(String[] valueSet : values){
            // Expand on the possible values, make these the new possible values
            StringSimpleList newPossibles = new StringSimpleList();
            // For each current possible, add new strings with the current values for the current index
            for(String possible : possibles){
                // For each value for the current index, add a string composed of that value and the current possible
                for(String value : valueSet){
                    if((casingOption > 1) && (value.matches(".*[a-zA-Z].*"))){
                        newPossibles.add(possible + value.toLowerCase());
                        newPossibles.add(possible + value.toUpperCase());
                    }else{
                        newPossibles.add(possible + value);
                    }
                }
            }
            possibles = newPossibles.toArray();
        }
        
        // Generate the strings at random
        String[] generated = new String[number];
        // Compose each string at random
        for(int i = 0; i < number; i++){
            generated[i] = "";
            // For each values array, append one of the values from that array
            for(String[] chars : values){
                String c = chars[MathHandler.getRandomNumber(chars.length)];
                if (casingOption > 0) c = (MathHandler.getRandomNumber(0, 1) > 0)
                        ? c.toUpperCase()
                        : c.toLowerCase();
                generated[i] += c;
            }
        }
        
        // Assign the arrays to an array of arrays and return
        String[][] result = new String[2][];
        result[0] = generated;
        result[1] = possibles;
        return result;
    }
    
    
    /**Generate a range of numbers in random order
     * @param range The number of numbers to generate
     * @return An array of numbers composed of 0 through range-1 in random order
     */
    public static int[] generateRandomOrder(int range){
        return generateRandomOrder(0, range);
    }
    /**Generate a range of numbers in random order
     * @param baseValue The starting value of the range
     * @param numValues The number of values to generate
     * @return An array of integers composed of the numValues numbers starting with baseValue in random order
     */
    public static int[] generateRandomOrder(int baseValue, int numValues){
        // Set up the array, populating each number in-order
        String[] numbers = new String[numValues];
        for(int i = 0; i < numValues; i++){
            numbers[i] = Integer.toString(baseValue + i);
        }
        // Shuffle the array (make sure we grab the result, as it doesn't do so in-place)
        numbers = LogicHandler.shuffleArray(numbers);
        // Cast each number to an integer and return the result
        int[] result = new int[numValues];
        for(int i = 0; i < numValues; i++){
            result[i] = Integer.valueOf(numbers[i]);
        }
        return result;
    }
    
}
