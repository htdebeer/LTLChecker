package org.processmining.analysis.ltlchecker;

import java.util.*;
import org.processmining.analysis.ltlchecker.parser.*;
import org.processmining.framework.log.*;
import org.processmining.framework.ui.*;
import org.processmining.framework.plugin.*;
import org.processmining.analysis.*;


public class SetsSet {
    
/**
 * SetsSet is a container of the attributes of type set. There are actually
 * two sets of sets: one for the process instance attributes and one for the
 * audit trail entries.
 *
 * @version 0.2
 * @author HT de Beer
 */

// FIELDS
    
    /**
     * The set containing the sets of the process instance sets.
     */
    TreeMap piSets;

    /**
     * The names of the sets in piSets.
     */
    ArrayList piNames;

    /**
     * The set containing the sets of the audit trail entries.
     */
    TreeMap ateSets;

    /**
     * The names of the sets in ateSets.
     */
    ArrayList ateNames;    

    /**
     *	If only standard sets are used, flag it, to save time :-).
     */
    boolean standardSetsOnly;

// CONSTRUCTORS

    public SetsSet( LTLParser parser, LogSummary log ) {
    
	Attribute attr;
	String name;

	// If no sets are created at all, only standard ones are used.
	standardSetsOnly = true;

	piSets = new TreeMap();
	piNames = new ArrayList();

	ateSets = new TreeMap();
	ateNames = new ArrayList();
	
	ArrayList as = parser.getAttributes();
	Iterator i = as.iterator();

	while ( i.hasNext() ) {
	
	    attr = ( Attribute ) i.next();
	    
	    if ( attr.getType() == Attribute.SET ) {
		// Only creating sets for those attributes which have type
		// set.
		
		if ( attr.getScope() == Attribute.PI ) {
		    // Scope is the process instance
		    
		    name = attr.getAttributeId().substring(3);// cut `pi.'
		    piSets.put( name, new HashSet() );
		    piNames.add( name );
		    standardSetsOnly = false;
		    // Sets created in the scope of process instances are
		    // never standard sets.
		    
		} else {
		    // scope is the audit trail entry
		    
		    name = attr.getAttributeId().substring(4);// cut `ate.'
		    
		    if ( name.equals("WorkflowModelElement") ) {
		    
			String[] ss = log.getModelElements();
			HashSet hs = new HashSet(ss.length);
			
			for( int j = 0; j < ss.length; j++ ) {
			    hs.add( ss[j] );	
			};

			ateSets.put( name, hs );
			ateNames.add( name );		    
			
		    } else if ( name.equals("EventType") ) {
		    
			String[] ss = log.getEventTypes();
			HashSet hs = new HashSet(ss.length);
			
			for( int j = 0; j < ss.length; j++ ) {
			    hs.add( ss[j] );	
			};

			ateSets.put( name, hs );
			ateNames.add( name );		    
			
		    } else if ( name.equals("Originator") ) {
		    
			String[] ss = log.getOriginators();
			HashSet hs = new HashSet(ss.length);
			
			for( int j = 0; j < ss.length; j++) {
			    hs.add( ss[j] );	
			};

			ateSets.put( name, hs );
			ateNames.add( name );		    
			
		    } else {
			// A Data element set attribute:
			
			ateSets.put( name, new HashSet() );
			ateNames.add( name );
			standardSetsOnly = false;
			// there is at least one non standard set to create,
			// so the whole log must be through to create the set.
			
		    };
		    
		};
		
	    };
	    
	};

    }

// METHODS

    /**
     * Are there only standard sets used, that is, WorkFlowModelElement,
     * Originator and EventType?
     *
     * @return If only standard sets are used, return true.
     */
    public boolean standardSetsOnly() {
	return standardSetsOnly;
    }

    /**
     * Get an set given the name and the scope of the set.
     *
     * @param name The name of the set.
     * @param scope The scope of the set.
     * 
     * @return The set specified by name and scope.
     */
    public HashSet getSet( String name, int scope ) {
	String cuttedName;

	if ( scope == Attribute.PI ) {
	
	    cuttedName = name.substring( 3 );// cut `pi.'
	    
	    if ( piNames.contains( cuttedName ) ) {
		return (HashSet) piSets.get( cuttedName );
	    }
	    
	} else {
	    // scope is ATE
	    
	    cuttedName = name.substring(4);// cut `ate.'
	    
	    if ( ateNames.contains( cuttedName ) ) {
		return (HashSet) ateSets.get( cuttedName );
	    }    
	}

	return null;
    }

    /**
     * Fill the sets with information of a logreader.
     *
     * @param log A {@see LogReader} object containing all the
     * processinstances.
     */
    public void fill( final LogReader log, final Progress p ) {

	log.reset();
	
	if (! standardSetsOnly ) {
	    
	    int j = 0;

	    while ( log.hasNext() ) {

		ProcessInstance pi = log.next();
		fillPiSets( pi );

		p.setProgress( j );
		j++;
	    
		AuditTrailEntries ates = pi.getAuditTrailEntries();
		
		while ( ates.hasNext() ) {
		
		    AuditTrailEntry ate = ates.next();
		    fillAteSets( ate );
		    
		};
		
	    };	
	    
	}; // else the sets are already filled.
    }
    
    /**
     * Fill the pi sets with the dataelements of a process instance.
     *
     * @param pi The process instance to fill the pi sets with.
     */
    private void fillPiSets( ProcessInstance pi ) {
	Iterator i = piNames.iterator();
	String name = "";
	HashSet setOfName;

	while ( i.hasNext() ) {
	
	    name = (String) i.next();
	    
	    if (pi.getData().containsKey( name ) ) {
	    
		setOfName = (HashSet) piSets.get( name );
		setOfName.add( (String) pi.getData().get( name ) );
		
	    };
	    
	};
    }

    /**
     * Fill the ate sets with the dataelements of a audit trail entry.
     *
     * @param ate The audit trail entry to fill the ate sets with.
     */
    private void fillAteSets( AuditTrailEntry ate ) {
	
	Iterator i = ateNames.iterator();
	String name = "";
	HashSet setOfName;

	while ( i.hasNext() ) {
	
	    name = (String) i.next();

	    if ( ! isStandard( name ) ) {
	    
		if ( name.equals( "Timestamp" ) ) {
		
		    setOfName = (HashSet) ateSets.get( name );
		    setOfName.add( ate.getTimestamp().toString() );
		    
		} else {
		
		    if (ate.getData().containsKey( name ) ) {
		    
			setOfName = (HashSet) ateSets.get( name );
			setOfName.add( (String) ate.getData().get( name ) );
			
		    };
		    
		}
		
	    }; // else do nothing, sets already created		
	    
	};
    }
	    
    /**
     * Is the name a standard set?
     *
     * @param name The name of an attribute.
     *
     * @return If name is either WorkflowModelElement, EventType or
     * Originator, return true, else false.
     */
    private boolean isStandard( String name ) {
	return ( 
		( name.equals("WorkflowModelElement") ) ||
	    	( name.equals("EventType") )		||
	    	( name.equals("Originator") )
	    );
    }
    
}
