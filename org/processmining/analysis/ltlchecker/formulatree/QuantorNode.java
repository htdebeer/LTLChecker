package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import java.util.*;

/**
 * QuantorNode is a node class of the formula tree denoting the quantors for
 * all and exists.
 *
 * @version 0.1
 * @author HT de Beer
 */
public abstract class QuantorNode extends FormulaNode {

// FIELDS

    /** The list with children formulae of this quantor. */
    ArrayList children;
    
// CONSTRUCTORS
    
    public QuantorNode( ) {
	super( );
	children = new ArrayList( );
    }

// METHODS
    
    /**
     * Add a child node with the formula the child is.
     *
     * @param child The child node.
     */
    public void addChild( FormulaNode child ) {
	children.add( child );
    }

}
