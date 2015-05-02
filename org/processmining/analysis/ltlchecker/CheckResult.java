package org.processmining.analysis.ltlchecker;

import org.processmining.framework.log.*;

/**
 * CheckResult is a link to a process instance which is checked on a ltl
 * formula. It contains the number of the pi in the log, the name and the
 * process.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class CheckResult {

// FIELDS 

    /** The number of this pi in the log. */
    private int numberInLog;

    /** The name of this process instance. */
    private String name;

// CONSTRUCTORS
    
    public CheckResult( int nr, ProcessInstance pi) {
	this.numberInLog = nr;
	this.name = pi.getName();
    }

// METHODS

    /** 
     * Get the number of this process instance in the log.
     *
     * @return The number of this process in the log.
     */
    public int getNumberInLog() {
	return this.numberInLog;
    }

    /**
     * Get the process instance, given a log.
     *
     * @param log The log to found this process instance.
     *
     * @return The process instance corresponding to the number of this in
     * the log.
     */
    public ProcessInstance getProcessInstance( LogReader log ) {
	int currentNr = 0;
	ProcessInstance result = null;
	
	log.reset();

	while ( log.hasNext() && ( currentNr <= this.numberInLog ) ) {
	    // Go through the log till this pi is reached
	    
	    result = log.next();
	    currentNr++;
	};

	return result;
    }

    /**
     * To string this item.
     *
     * @return The string representation of this.
     */
    public String toString() {
	return this.name + " (" + this.numberInLog + ")";
    }
}
