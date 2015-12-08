/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.structs;

import wheeler.generic.data.StringHandler;

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
    
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Parameters){
            // Cast the object
            Parameters params = (Parameters)o;
            
            // Check the file-search parameters
            if (!StringHandler.areEqual(searchRoot, params.searchRoot, true)) return false;
            if (includeFiles.length != params.includeFiles.length) return false;
            if (includePaths.length != params.includePaths.length) return false;
            if (includeTypes.length != params.includeTypes.length) return false;
            if (excludeFiles.length != params.excludeFiles.length) return false;
            if (excludePaths.length != params.excludePaths.length) return false;
            if (excludeTypes.length != params.excludeTypes.length) return false;
            for (int i = 0; i < includeFiles.length; i++)
                if (!StringHandler.areEqual(includeFiles[i], params.includeFiles[i], true)) return false;
            for (int i = 0; i < includePaths.length; i++)
                if (!StringHandler.areEqual(includePaths[i], params.includePaths[i], true)) return false;
            for (int i = 0; i < includeTypes.length; i++)
                if (!StringHandler.areEqual(includeTypes[i], params.includeTypes[i], true)) return false;
            for (int i = 0; i < excludeFiles.length; i++)
                if (!StringHandler.areEqual(excludeFiles[i], params.excludeFiles[i], true)) return false;
            for (int i = 0; i < excludePaths.length; i++)
                if (!StringHandler.areEqual(excludePaths[i], params.excludePaths[i], true)) return false;
            for (int i = 0; i < excludeTypes.length; i++)
                if (!StringHandler.areEqual(excludeTypes[i], params.excludeTypes[i], true)) return false;
            
            // Check the search-line parameters
            if (searchLines != params.searchLines) return false;
            if (searchLines && !StringHandler.areEqual(line, params.line, true)) return false;
            if (excludeLines != params.excludeLines) return false;
            if (excludeLines && !StringHandler.areEqual(exclude, params.exclude, true)) return false;
            if (hideLines != params.hideLines) return false;
            if (checkCase != params.checkCase) return false;
            if (useRegex != params.useRegex) return false;
            
            // Everything matched
            return true;
        }
        return false;
    }
    
}
