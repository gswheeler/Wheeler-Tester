/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.structs;

/**
 * Test class that exposes protected methods for testing
 */
public class TestParameters extends filesearcher.structs.Parameters{
    
    public TestParameters(){
        includeFiles = new String[0];
        excludeFiles = new String[0];
        includePaths = new String[0];
        excludePaths = new String[0];
        includeTypes = new String[0];
        excludeTypes = new String[0];
    }
    
}
