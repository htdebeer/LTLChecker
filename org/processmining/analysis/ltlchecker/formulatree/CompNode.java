package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import org.processmining.analysis.ltlchecker.parser.*;

/**
 * CompNode is a node class of the formula tree denoting comparator operators,
 * like ==, ~=, <=, >=, etc.
 *
 * @version 0.1
 * @author HT de Beer
 */
public abstract class CompNode extends FormulaNode {

// FIELDS
    /** == */
    final public static int EQUAL	= 0;
    /** != */
    final public static int NOTEQUAL	= 1;
    /** <= */
    final public static int LESSEREQUAL = 2;
    /** >= */
    final public static int BIGGEREQUAL = 3;
    /** ~= */
    final public static int REGEXPEQUAL = 4;
    /** < */
    final public static int LESSER	= 5;
    /** > */
    final public static int BIGGER	= 6;
    /** in */
    final public static int IN		= 7;

// CONSTRUCTORS

    public CompNode( ) {
	super( );
    }

// METHODS
    
}
