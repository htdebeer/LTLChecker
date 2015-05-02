package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import org.processmining.analysis.ltlchecker.parser.*;
import java.util.*;

/**
 * SetValueNode is a representation of a set literal, attribute or set.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class SetValueNode extends ValueNode {

// FIELDS

    /** It is an attribute of this ate. */
    public static final int VALUE	= 0;

    /** It is a set */
    public static final int SET		= 2;

    /** If this node is an attributevalue or a literal, this field contains the attribute.
     */
    private SetAttribute value;

    /** If this node is an set itself. */
    private TreeSet set;

    /** The `type' of this node, either attribute or literal. */
    private int type;
    
// CONSTRUCTORS
    public SetValueNode( int type) {
	this.type = type;	
    }

// METHODS

    /**
     * Set the value.
     *
     * @param val The value to set.
     */
    public void setValue( SetAttribute val ) {
	this.value = val;
    }

    public SetAttribute getValue() { return value; }

    /** 
     * Set the set.
     *
     * @param set The set to set.
     */
    public void setSet( TreeSet set ) {
	this.set = set;
    }

    /**
     * Is a string in this set?
     *
     * @param name String to test.
     *
     * @return If name in set return true, else false.
     */
    public boolean in( String name ) {
	return this.set.contains( name );
    }

    /**
     * Compute the value of this node, either getting the string
     * representation fo the attribute or giving the literal.
     *
     * @param pi The current process instance.
     * @param ate The current audit trail entry of this pi.
     *
     * @return The string of this node.
     */
    public String value( ProcessInstance pi, LinkedList ates, int ateNr )
		    throws AttributeNoValueException, ParseAttributeException {
	try {
	    nr = ateNr;
	    String result = null;

	    if ( this.type == VALUE ) {
		    result = this.value.value( pi, ates, this.getBinder().getNr() );
	    };
	    return result;	    
	    
	} catch( ParseAttributeException aep ) {
	    throw aep;
	} catch( AttributeNoValueException anve ) {
	    throw anve;
	}
    }
}
