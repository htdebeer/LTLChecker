package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;

/**
 * BinaryNode is a node class of the formula tree denoting binary logic
 * operators, like and, or, until, etc.
 *
 * @version 0.1
 * @author HT de Beer
 */
public abstract class BinaryNode extends FormulaNode {

// FIELDS

    /** The left child formula every binary operator has. */
    FormulaNode leftChild;
    
    /** The right child formula every binary operator has. */
    FormulaNode rightChild;

// CONSTRUCTORS

    public BinaryNode( ) {
	super( );
    }

// METHODS
    
    /**
     * Set the left  child node with the formula the child is.
     *
     * @param child The left child node.
     */
    public void setLeftChild( FormulaNode child ) {
	this.leftChild = child;
    }
    
    /**
     * Set the right child node with the formula the child is.
     *
     * @param child The right child node.
     */
    public void setRightChild( FormulaNode child ) {
	this.rightChild = child;
    }

}
