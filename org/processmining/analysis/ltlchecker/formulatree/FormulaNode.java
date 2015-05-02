package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import java.util.*;

/**
 * FormulaNode is the main class of the hierarchy of formula nodes to denote a
 * LTL formula.
 *
 * @version 0.1
 * @author HT de Beer
 */
public abstract class FormulaNode extends TreeNode {

// FIELDS

// CONSTRUCTORS
    public FormulaNode() {
	super( );	
    }

// METHODS

    /**
     * Compute the value of this node, that is te value of this node given the
     * i-th ate of pi, computed by calling the value method of the children.
     *
     * @param pi The current process instance.
     * @param ate The current audit trail entry of pi.
     *
     * @return The value of this node computed by calling the value method of
     * the children applied to the operator of this node.
     */
     public abstract boolean value( ProcessInstance pi, LinkedList ates, int
					ateNr );
}
