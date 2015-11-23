/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wheeler.generic.structs;

import javax.swing.JFrame;
import wheeler.generic.data.DialogFactory;
import wheeler.generic.data.StringHandler;
import wheeler.generic.error.LogicException;

/**
 * An extension of the StringSortedList with extra functions for testing.
 */
public class TestStringSortedList extends wheeler.generic.structs.StringSortedList {
    
    // Make sure the list is accurate; report the first out-of-order nodes
    public boolean isValid(JFrame caller) throws Exception{
        // What we are checking for:
        /*Header nodes:
         * must not be null
         * must have a null value
         * must not have a prev node
         * must have a chain value of zero
         */
        /*List nodes
         * Cannot have a null value
         * Must have a prev node
         * Must not come before their "next" node (if they have one)
         * Must be their prev's next and their next's prev
         * Must have a value that triggers the right index
         */
        /*Chain values
         * Must occur in ascending order
         * Must point to an index with the same value as the node with that index
         * Must point to a node whose index is set
         */
        /*Length values
         * Must have a number of nodes in the chain equal to the stored length
         * The nodes must point to the correct indexed node
         */
        
        
        // Test the header node
        if(header == null){
            return invalidMsg(caller, "the header was somehow null");
        }
        if(header.value != null){
            return invalidMsg(caller, "the header's value was not null:\n" + header.value);
        }
        if(header.prev != null){
            return invalidMsg(caller, "the header's prev field was set:\n"
                    + ((header.prev.value != null) ? header.prev.value : "(null))"));
        }
        if(!header.chainIndexSet()){
            return invalidMsg(caller, "the header's chain index field was not set");
        }
        if(header.getChainIndex() != 0){
            return invalidMsg(caller, "the header's chain index was " + header.getChainIndex() + ", not zero like we expected");
        }
        
        // Test each list node
        StringLinkNode node = header.next;
        int count = 1;
        int lastChainIndex = 0;
        int lastIndexCount = 0;
        while(node != null){
            if(node.value == null){
                return invalidMsg(caller, "node " + count + " had a null value");
            }
            if(node.prev == null){
                return invalidMsg(caller, "didn't have a prev value for node " + count + "\n" + node.value);
            }
            if(node.next != null && node.next.value != null
                    && StringHandler.strAafterB(node.value, node.next.value)){
                return invalidMsg(caller, "strings " + count + " and " + (count+1) + " were out of order:\n"
                        + node.value + "\n" + node.next.value);
            }
            if(node.next != null){
                if (node.next.prev == null)
                    return invalidMsg(caller, "the prev field of the node after node " + count + " was unset");
                if (node.getInstanceNumber() != node.next.prev.getInstanceNumber())
                    return invalidMsg(caller, "the prev field of the node after node " + count
                            + " was pointing at node instance\n" + node.next.prev.getInstanceNumber()
                            + "\nwhereas the node in question is node instance\n" + node.getInstanceNumber());
            }
            if(node.prev != null){
                if (node.prev.next == null)
                    return invalidMsg(caller, "the next field of the node before node " + count + " was unset");
                if (node.getInstanceNumber() != node.prev.next.getInstanceNumber())
                    return invalidMsg(caller, "the next field of the node before node " + count
                            + " was pointing at node instance\n" + node.prev.next.getInstanceNumber()
                            + "\nwhereas the node in question is node instance\n" + node.getInstanceNumber());
            }
            if(node.chainIndexSet()){
                if(node.getChainIndex() > lastChainIndex){
                    lastChainIndex = node.getChainIndex();
                    lastIndexCount = count;
                }else{
                    return invalidMsg(caller, "found chain index " + node.getChainIndex() + " in node "
                            + count + "after finding index " + lastChainIndex + " in node " + lastIndexCount
                            + ":\n" + node.value);
                }
                if(index[node.getChainIndex()].getInstanceNumber() != node.getInstanceNumber()){
                    return invalidMsg(caller, "the node with index " + node.getChainIndex()
                            + " did not match the node registered at that index:\n"
                            + node.getInstanceNumber() + "\n" + index[node.getChainIndex()].getInstanceNumber());
                }
            }
            // Always checked: nesting this block to maintian readability
            {
                int indexVal = getIndex(node.value);
                StringLinkNode indexNode = index[indexVal];
                if((indexNode.value != null) && (!StringHandler.strAbeforeB(indexNode.value, node.value)))
                    return invalidMsg(caller, "getIndex, when called with value\n" + node.value
                            + "\non node " + count + " returned index\n" + indexVal + "\n which pointed at a node with value\n"
                            + indexNode.value + "\nwhich is not BEFORE the requested value");
                while(!StringHandler.areEqual(indexNode.value, node.value, true)){
                    indexNode = indexNode.next;
                    if(StringHandler.strAafterB(indexNode.value, node.value))
                        return invalidMsg(caller, "calling getIndex with value\n" + node.value
                                + "\nreturned index " + indexVal + "; working up the list,\n" + indexNode.value
                                + "\n, which comes after the call value, was encountered before the call value.");
                }
            }
            node = node.next; count++;
        }
        
        // Test the chain index
        for(int i = 0; i < index.length; i++){
            node = index[i];
            if(!node.chainIndexSet()){
                return invalidMsg(caller, "the node at index " + i + " did not have its index set");
            }
            if(node.getChainIndex() != i){
                return invalidMsg(caller, "the node at index " + i + " did not have its index set to "
                        + i + "; was set to " + node.getChainIndex());
            }
            if(node.wasRemoved()){
                return invalidMsg(caller, "the node at index " + i + " had been removed");
            }
        }
        
        // Test the lengths index
        if (index.length != lengths.length)
            return invalidMsg(caller, "the chain index had a different number of values than the lengths index ("
                    + index.length + " chains and " + lengths.length + " lengths)");
        for(int i = 0; i < lengths.length; i++){
            node = index[i];
            for(int j = 0; j < lengths[i]; j++){
                if (node == null)
                    return invalidMsg(caller, "hit the end of a list when looking along the length of a chain (the chain at index "
                            + i + " only had " + j + " items when it was supposed to have " + lengths[i] + ")");
                if (node.getChainIndex() != i)
                    return invalidMsg(caller, "chain " + i + " claimed to have " + lengths[i] + " items but item "
                            + j + " claimed to be a part of chain " + node.getChainIndex());
                node = node.next;
            }
            if(i + 1 < lengths.length){
                if (node == null)
                    return invalidMsg(caller, "there were supposed to be items after chain " + i + ", but the end of the list was found instead");
                if (node.getChainIndex() != i + 1)
                    return invalidMsg(caller, "the first item after chain " + i
                            + " was supposed to be part of the next chain, but it claimed to be part of chain "
                            + node.getChainIndex());
            }else{
                if (node != null)
                    return invalidMsg(caller, "items were found after the end of the last chain");
            }
        }
        
        // Everything passed
        return true;
    }
    private boolean invalidMsg(JFrame caller, String detail) throws Exception{
        String message = "There's a bug in our StringSortedList: " + detail;
        if (caller == null) throw new LogicException(message);
        DialogFactory.message(caller, message);
        return false;
    }
    
    @Override
    public TestStringSortedList getNew(){
        return new TestStringSortedList();
    }
    
}
