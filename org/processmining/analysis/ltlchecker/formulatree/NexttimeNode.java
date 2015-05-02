package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import java.util.*;

/**
 * NexttimeNode is a node class of the formula tree denoting the nexttime
 * operator.
 *
 * @version 0.2
 * @author HT de Beer
 */
public class NexttimeNode extends UnaryNode {

// FIELDS
    /** The next value, that is the previous computed value of the child node.
     * Needed to compute
     * the value of the current (pi, ate) combination. Because the nexttime
     * operator is about the next time.
     */
    private boolean next;

// CONSTRUCTORS

    public NexttimeNode( ) {
	super( );
	// initialisation: in the empty sequence, there is no next time ...
	this.next = false;
    }

// METHODS
    
    /**
     * Compute the value of this node, that is te value of this node given the
     * i-th ate of pi, computed by calling the value method of the child.
     *
     * @param pi The current process instance.
     * @param ate The current audit trail entry of pi.
     *
     * @return The value of this node computed by calling the value method of
     * the child applied to the operator of this node.
     */
     public boolean value( ProcessInstance pi, LinkedList ates, int ateNr )
	{
	nr = ateNr + 1;
	
	boolean result = false;
	if ( ateNr >= ates.size() ) {
	    result = false;
	} else {
	    boolean c = child.value( pi, ates, ateNr + 1);
	    result = c;
	};
	return result;
    }
}
