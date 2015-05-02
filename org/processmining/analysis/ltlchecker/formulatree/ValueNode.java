package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;

/**
 * ValueNode is the main class of the hierarchy of value nodes to denote a
 * value, eiter an number expression or an string literal, a set or a date.
 *
 * @version 0.1
 * @author HT de Beer
 */
public abstract class ValueNode extends TreeNode {

// FIELDS
    
    /** The operator this value is binded by. Only for the values used in
     * quantification and formulacalls, this binding is important.*/
    private TreeNode binder;

// CONSTRUCTORS
    public ValueNode() {
	super( );
	binder = null;
    }

// METHODS

    /**
     * Get the binder of this value. The binder is that operator this value is
     * bounded, so to say.
     *
     * @return the binderd of this value.
     */
    public TreeNode getBinder() {
	return this.binder;
    }

    /**
     * Set the binder of this vale. If there is already a binder available,
     * does not change it. That is, a value is binded at the first occorence
     * of it. So by recurred call of it in formulae, it does not change.
     *
     * @param binder The binder operator.
     */
    public void setBinder( TreeNode binder ) {
	if ( this.binder == null ) {
	    this.binder = binder;
	};
    }

}
