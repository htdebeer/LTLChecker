package org.processmining.analysis.ltlchecker.parser;

import org.processmining.framework.log.*;

/** 
 * ParseAttributeException is generated when a string value of an attribute is
 * parsed to the type of the attribute and there is going something wrong with
 * the parsing.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class ParseAttributeException extends Exception {

    public ParseAttributeException( String stringValue, Attribute attr ) {
	super(
	    "Error by parsing '" + stringValue + "' as a " + 
	    attr.toString() + ".");
    }

}
