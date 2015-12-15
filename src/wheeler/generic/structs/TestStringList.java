/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.structs;

/**
 * An extension of the StringList class with additional methods for testing
 */
public class TestStringList extends wheeler.generic.structs.StringList {
    
    /**Provide a set of IStringList instances over which StringList tests can iterate
     * @return An array containing an instance of the test extension for each type of StringList
     */
    public static IStringList[] getStringLists(){
        IStringList[] lists = new IStringList[3];
        lists[0] = new TestStringList();
        lists[1] = new TestStringSimpleList();
        lists[2] = new TestStringSortedList();
        return lists;
    }
    
}
