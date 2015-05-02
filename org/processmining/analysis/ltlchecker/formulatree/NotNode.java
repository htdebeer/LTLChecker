package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import java.util.*;

/**
 * NotNode is a node class of the formula tree denoting the not operator.
 *
 * @version 0.2
 * @author HT de Beer
 */
public class NotNode extends UnaryNode {

// FIELDS

// CONSTRUCTORS

    public NotNode( ) {
	super( );
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
	nr = ateNr;
	boolean c = child.value( pi, ates, ateNr );
	return (! c );
    }
}
