package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;

/**
 * UnaryNode is a node class of the formula tree denoting unary logic
 * operators, like not, always, etc.
 *
 * @version 0.1
 * @author HT de Beer
 */
public abstract class UnaryNode extends FormulaNode {

// FIELDS

    /** The child formula every unary operator has. */
    FormulaNode child;
    
// CONSTRUCTORS

    public UnaryNode( ) {
	super( );
    }

// METHODS
    
    /**
     * Set the child node with the formula the child is.
     *
     * @param child The child node.
     */
    public void setChild( FormulaNode child ) {
	this.child = child;
    }

}
