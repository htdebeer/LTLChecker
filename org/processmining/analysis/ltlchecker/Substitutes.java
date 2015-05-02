package org.processmining.analysis.ltlchecker;

import java.util.*;
import org.processmining.analysis.ltlchecker.formulatree.*;
import org.processmining.analysis.ltlchecker.parser.*;

/**
 * Substitutes is a wrapper class for a treemap containing substitutes in local
 * context. That is local variables are defined and later on they get an value
 * which is used by comparisons and formula calls.
 *
 * These substitutes are also used to attach user-added values for formulae.
 * The checker therefor does not see any difference between a formula call in
 * the language and one via the GUI.
 *
 * @version 0.1
 * @author HT de Beer
 */

public class Substitutes {

// FIELDS

    /**
     * The map containing the substitutes: that is an id with an corresponding
     * value.
     */
    TreeMap substs;

// CONSTRUCTORS

    public Substitutes( ) {
	substs = new TreeMap( );
    }

    public Substitutes( TreeMap substs ) {
	this.substs = substs;
    }

// METHODS

    /**
     * Set the binding node for user added values so that these parameters
     * are bounded on the highest level, ther where they are used. Actually
     * this binding is not needed because the values the user add are
     * literals. Binding is only needed by attribute values. But every value
     * node should have a binder.
     *
     * @param binder The node to bind the values in this substituesset.
     */
    public void setBinder( RootNode binder ) {
    
	Iterator i = substs.values().iterator();

	while ( i.hasNext() ) {
	    ( (ValueNode) i.next() ).setBinder( binder );
	};
    }

    /**
     * Add the value of an id, by calling of an function or in an comparison
     * expression.
     *
     * @param id The id for which the value is to be set.
     * @param val The value for the id.
     */
    public void add( String id, ValueNode val ) {
	substs.put( id, val );
    }
     
    /**
     * Get the value of an id.
     *
     * @param id The id the value is to be get.
     *
     * @return The value of id.
     */
    public ValueNode get( String id ) {
	return (ValueNode) substs.get( id );
    }

    public boolean hasId( String id ) {
	return ( substs.containsKey( id ) );
    }

    /**
     * Create a swallow copy of this Substitutes object, that is, the
     * substitutes themselves are not copied.
     */
    public Object clone() {
	return ((Object)  new Substitutes( (TreeMap) this.substs.clone() ));
    }

    public String toString() {
	String result = "";
	Iterator i = substs.values().iterator();
	while (i.hasNext() ) {
	    ValueNode vn = (ValueNode) i.next();
	    if ( vn instanceof SetValueNode ) {
		Attribute attr = ((SetValueNode) vn).getValue();
		result = result + ":::" + attr + " - " + attr.getValue() + 
		">" + attr.getKind() +  "-\n";
	    };
	};
	return result;
    }

}
