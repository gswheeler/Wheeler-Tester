/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.data;

import wheeler.generic.structs.IntegerNode;

/**
 * A class with special functions for setting protected fields according to test values
 */
public class TestMathHandler extends wheeler.generic.data.MathHandler{
    
    public static String primeNumbersCache(){
        return wheeler.generic.data.MathHandler.primeNumbersCache();
    }
    
    public static IntegerNode getPrimeNumbersList(){
        return primeNumbersList;
    }
    public static IntegerNode setPrimeNumbersList(IntegerNode newList){
        IntegerNode oldList = primeNumbersList;
        primeNumbersList = newList;
        return oldList;
    }
    
}
