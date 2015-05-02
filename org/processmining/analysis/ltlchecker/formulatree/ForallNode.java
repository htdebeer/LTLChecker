package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import java.util.*;

/**
 * ForallNode is a node class of the formula tree denoting the for all quantor.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class ForallNode extends QuantorNode {

// FIELDS

// CONSTRUCTORS

    public ForallNode( ) {
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
	boolean result = true;

	Iterator i = children.iterator();
	FormulaNode fnode;
	
	while ( i.hasNext() ) {
	    fnode = ( FormulaNode ) i.next();
	    if ( ! fnode.value( pi, ates, ateNr ) ) {
		result = false;
	    };
	};

	return result;
    }
}
