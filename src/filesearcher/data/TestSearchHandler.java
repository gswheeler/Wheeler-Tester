/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.data;

import filesearcher.structs.Parameters;

/**
 * Exposes protected methods for testing
 */
public class TestSearchHandler extends filesearcher.data.SearchHandler{
    
    public TestSearchHandler(Parameters parameters){
        super(parameters);
    }
    
    @Override
    public boolean filepathMatches(String filepath, boolean isFolder){
        return super.filepathMatches(filepath, isFolder);
    }
    
}
