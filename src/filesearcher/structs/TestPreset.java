/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.structs;

import wheeler.generic.data.StringHandler;

/**
 * Test class that provides special methods for testing
 */
public class TestPreset extends filesearcher.structs.Preset{
    
    public TestPreset(){}
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Preset){
            // Cast the object
            Preset preset = (Preset)o;
            
            // Make comparisons
            if (!StringHandler.areEqual(searchRoot, preset.searchRoot, true)) return false;
            if (!StringHandler.areEqual(files, preset.files, true)) return false;
            if (!StringHandler.areEqual(paths, preset.paths, true)) return false;
            if (!StringHandler.areEqual(types, preset.types, true)) return false;
            if (searchLines != preset.searchLines) return false;
            if (!StringHandler.areEqual(line, preset.line, true)) return false;
            if (excludeLines != preset.excludeLines) return false;
            if (!StringHandler.areEqual(exclude, preset.exclude, true)) return false;
            if (hideLines != preset.hideLines) return false;
            if (checkCase != preset.checkCase) return false;
            if (useRegex != preset.useRegex) return false;
            
            // Everything matched
            return true;
        }
        return false;
    }
    
}
