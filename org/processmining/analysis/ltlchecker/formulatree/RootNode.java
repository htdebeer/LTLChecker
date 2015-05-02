package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import java.util.*;

/** 
 * RootNode is used for binding formulae and values to the highest level of a
 * defined formula. It is created only for that purpose and fullfill he role
 * of the root as otherwise a other node has fullfilled.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class RootNode extends TreeNode {

    private FormulaNode formula;

    public RootNode() {
	super();
    }

    public void setFormula( FormulaNode formula ) {
	this.formula = formula;
    }


    /** Just return the formula's value. */
    public boolean value( ProcessInstance pi, LinkedList ates, int ateNr ) {
	this.nr = ateNr;
	return formula.value( pi, ates, ateNr );
    }

}
