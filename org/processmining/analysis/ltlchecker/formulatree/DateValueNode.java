package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import org.processmining.analysis.ltlchecker.parser.*;
import java.text.*;
import java.util.*;

/**
 * DateValueNode is a representation of a string literal or attribute.
 *
 * @version 0.2
 * @author HT de Beer
 */
public class DateValueNode extends ValueNode {

// FIELDS

    /** It is an attribute of this ate. */
    public static final int VALUE	= 0;

    /** If this node is an attributevalue, this field contains the attribute.
     */
    private DateAttribute value;

    /** The `type' of this node, either attribute or literal. */
    private int type;
    
// CONSTRUCTORS
    public DateValueNode( int type ) {
	this.type = type;	
    }

// METHODS

    /**
     * Set the value.
     *
     * @param val The value to set.
     */
    public void setValue( DateAttribute val ) {
	this.value = val;
    }

    /**
     * Compute the value of this node, either getting the string
     * representation fo the attribute or giving the literal.
     *
     * @param pi The current process instance.
     * @param ate The current audit trail entry of this pi.
     *
     * @return The date of this node.
     */
    public Date value( ProcessInstance pi, LinkedList ates, int ateNr ) 
		    throws AttributeNoValueException, ParseAttributeException {
	nr = ateNr;
	Date result = null;

	if ( this.type == VALUE ) {
	    try {
	        result = this.value.value( pi, ates, this.getBinder().getNr() );
	    } catch( ParseAttributeException pae ) {
		throw pae;
	    } catch( AttributeNoValueException anve ) {
		throw anve;
	    }
	};

	return result;	    
    }
}
