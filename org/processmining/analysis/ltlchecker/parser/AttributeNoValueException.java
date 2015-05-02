package org.processmining.analysis.ltlchecker.parser;

import org.processmining.framework.log.*;

/** 
 * AttributeNoValueException is generated when a attribute does not exists in
 * a process instance or audit trail entry.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class AttributeNoValueException extends Exception {

    public AttributeNoValueException( ProcessInstance pi, int ateNr, Attribute attr ) {
	super(
	    "No element " + attr.toString() + " in pi" + pi.getName() + 
	    " - " + pi.getProcess() + " ( " + ateNr + " ).");
    }

}
