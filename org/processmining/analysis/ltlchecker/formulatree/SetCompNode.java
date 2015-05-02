package org.processmining.analysis.ltlchecker.formulatree;

import org.processmining.framework.log.*;
import org.processmining.framework.ui.*;
import org.processmining.analysis.ltlchecker.parser.*;
import java.util.*;

/**
 * SetCompNode is a node class of the formula tree denoting comparator
 * operators for sets: ==, != and in.
 *
 * @version 0.2
 * @author HT de Beer
 */
public class SetCompNode extends CompNode {

// FIELDS

    /** Lefthandside attribute of this comparison. */
    SetAttribute attribute;

    /** The righthandside expressiong of this comparison, that is in this case
     * and StringValue.
     */
    SetValueNode value;

    /** The operator of this node one of the constants defined in {@see
     * CompNode}.*
     */
    int op;

// CONSTRUCTORS

    public SetCompNode( int op) {
	super( );
	this.op = op;
    }

// METHODS

    /** 
     * Set the attribute of this node.
     *
     * @param attr The attribute to set.
     */
    public void setAttribute( SetAttribute attr ) {
	this.attribute = attr;
    }

    /**
     * Set the righthand side value of this node.
     *
     * @param val The value of this node.
     */
    public void setValue( SetValueNode val ) {
	this.value = val;
    }    

    /**
     * Compute the value of this node, that is te value of this node given the
     * i-th ate of pi, computed by calling the value method of the children.
     *
     * @param pi The current process instance.
     * @param ate The current audit trail entry of pi.
     *
     * @return The value of this node computed by calling the value method of
     * the children applied to the operator of this node.
     */
     public boolean value( ProcessInstance pi, LinkedList ates, int ateNr )
	{
	boolean result = false;
	if ( ateNr < ates.size () ) {
	    try {
		nr = ateNr;
		String attrValue = attribute.value( pi, ates, ateNr );
		String exprValue = value.value( pi, ates, ateNr );
	    
		switch ( this.op ) {
		    case CompNode.EQUAL : { 
			result = 
				( attrValue.equals( exprValue) );
			}; break;
		    case CompNode.NOTEQUAL : {
			result = 
			      ( ! attrValue.equals( exprValue ) );
			}; break;
		    case CompNode.LESSEREQUAL : {
			result = 
			    ( attrValue.compareTo( exprValue ) <= 0 );
			}; break;
		    case CompNode.BIGGEREQUAL : {
			result = 
			    ( attrValue.compareTo( exprValue ) >= 0 );
			}; break;
//		    case CompNode.REGEXPEQUAL : {
//			result = 
//			    ( attrValue.matches( exprValue ) );
//			}; break;
		    case CompNode.LESSER : {
			result =
			    ( attrValue.compareTo( exprValue ) < 0 );
			}; break;
		    case CompNode.BIGGER : {
			result = 
			    ( attrValue.compareTo( exprValue ) > 0 );
			}; break;
		    case CompNode.IN : {
			result = ( value.in( attribute.value( pi, ates, ateNr ) ) );
			}; break;
		};

		return result;
		
	    } catch( ParseAttributeException pae ) {
		// Value or attribute van not be parsed, So a comparison
		// always result in false;
		Message.add( pae.getMessage(), Message.ERROR );
		return false;
	    } catch( AttributeNoValueException anve ) {
		// No vaule so the comparison is false.
		Message.add( anve.getMessage(), Message.ERROR );
		return false;
	    }
	} else {
	    // Out of range ...  so the value is false.
	    return false;
	}
    }
}
