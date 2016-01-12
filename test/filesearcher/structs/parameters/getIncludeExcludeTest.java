/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearcher.structs.parameters;

import filesearcher.structs.TestParameters;
import javax.swing.JFrame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import wheeler.generic.data.DialogFactory;
import wheelertester.forms.GenericInterface;

/**
 * Test the function in Parameters that splits included and excluded search parameters
 */
public class GetIncludeExcludeTest {
    
    private final JFrame caller = GenericInterface.getJFrame();
    private final String title = "FileSearcher - GetIncludeExclude";
    
    @Test
    public void noDashes(){
        // Tell the user what's up
        DialogFactory.message(caller, "You should not receive a prompt.\nIf you do, cancel.", title);
        
        // Set up the data and split
        String paramString = " itema iTEMb  iTEMa itemb ";
        String[] expectInclude = {"itema", "iTEMb", "iTEMa", "itemb"};
        String[] expectExclude = {};
        String[][] result = TestParameters.getIncludeExclude(paramString, "test", caller);
        assertNotNull("The user canceled during the split", result);
        
        // Check stuff out
        assertFalse("The user reported being prompted", DialogFactory.optionYesNo(caller, "Were you prompted?", title));
        assertEquals("The include array was the wrong size", expectInclude.length, result[0].length);
        assertEquals("The exclude array was the wrong size", expectExclude.length, result[1].length);
        for(int i = 0; i < expectInclude.length; i++){
            assertEquals("The include array had an unexpected value", expectInclude[i], result[0][i]);
        }
        for(int i = 0; i < expectExclude.length; i++){
            assertEquals("The exclude array had an unexpected value", expectExclude[i], result[1][i]);
        }
    }
    
    @Test
    public void excludeDashes(){
        // Tell the user what's up
        DialogFactory.message(caller, "Choose to exclude dashes.\nIf you're asked multiple times, cancel.", title);
        
        // Set up the data and split
        String paramString = " itema -iT-EMb  - iT-EMa -itemb ";
        String[] expectInclude = {"itema", "iT-EMa"};
        String[] expectExclude = {"iT-EMb", "itemb"};
        String[][] result = TestParameters.getIncludeExclude(paramString, "test", caller);
        assertNotNull("The user canceled during the split", result);
        
        // Check stuff out
        assertEquals("The include array was the wrong size", expectInclude.length, result[0].length);
        assertEquals("The exclude array was the wrong size", expectExclude.length, result[1].length);
        for(int i = 0; i < expectInclude.length; i++){
            assertEquals("The include array had an unexpected value", expectInclude[i], result[0][i]);
        }
        for(int i = 0; i < expectExclude.length; i++){
            assertEquals("The exclude array had an unexpected value", expectExclude[i], result[1][i]);
        }
    }
    
    @Test
    public void includeDashes(){
        // Tell the user what's up
        DialogFactory.message(caller, "Choose not to exclude dashes.\nIf you're asked multiple times, cancel.", title);
        
        // Set up the data and split
        String paramString = " itema -iT-EMb  - iT-EMa -itemb ";
        String[] expectInclude = {"itema", "iT-EMa", "-iT-EMb", "-", "-itemb"};
        String[] expectExclude = {};
        String[][] result = TestParameters.getIncludeExclude(paramString, "test", caller);
        assertNotNull("The user canceled during the split", result);
        
        // Check stuff out
        assertEquals("The include array was the wrong size", expectInclude.length, result[0].length);
        assertEquals("The exclude array was the wrong size", expectExclude.length, result[1].length);
        for(int i = 0; i < expectInclude.length; i++){
            assertEquals("The include array had an unexpected value", expectInclude[i], result[0][i]);
        }
        for(int i = 0; i < expectExclude.length; i++){
            assertEquals("The exclude array had an unexpected value", expectExclude[i], result[1][i]);
        }
    }
    
    @Test
    public void testCancel(){
        // Tell the user what's up
        DialogFactory.message(caller, "When you're asked about dashes, cancel.", title);
        
        // Set up the data and split
        String paramString = " itema -iT-EMb  - iT-EMa -itemb ";
        String[][] result = TestParameters.getIncludeExclude(paramString, "test", caller);
        
        // Check with the user, then check the result
        assertTrue("User did not have a chance to cancel", DialogFactory.optionYesNo(caller, "Were you able to cancel?", title));
        assertNull("The canceled function did not return a null", result);
    }
    
}
