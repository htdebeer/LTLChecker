package org.processmining.analysis.ltlchecker;

import org.processmining.mining.*;
import org.processmining.framework.log.*;
import org.processmining.analysis.ltlchecker.parser.*;
import org.processmining.analysis.ltlchecker.formulatree.*;
import org.processmining.framework.ui.*;
import org.processmining.analysis.*;
import org.processmining.framework.plugin.*;
import java.util.*;
import javax.swing.*;

public class LTLChecker implements AnalysisPlugin {
/**
 * A plugin for the ProM framework to check for properties specified in
 * LTL on workflow logs of processes. It is an analysis plugin, so it can be
 * used after mining has been done on the log. Needed for this plugin are a
 * logReader object and a imported LTL Template file by the import plugin.
 *
 * @version 0.2
 * @author HT de Beer
 */

// FIELDS

// CONSTRUCTORS

    public LTLChecker() {
    }

// METHODS

    /**
     * Gets the name of this plugin. This name will be used in the gui of the
     * framework to select this plugin.
     *
     * @return The name of this plugin.
     */
    public String getName() {
	return "LTL Checker Plugin";
    }

    /**
     * Gets a description of this plugin in HTML, used by the framework to be
     * displayed in the help system.
     *
     * @return A description of this plugin in HTML.
     */
    public String getHtmlDescription() {
	return	"<H1>LTL Checker Plugin</H1>\n"
		+ "\t"	+ "<P>\n"
		+ "\t"	+ "\t"	+ "This plugin checks a workflow log of a " 
				+ "selected process on a property specified "
				+ "by a Linear Temporal Logic expression.\n"
		+ "\t"	+ "</P>\n";				
    }

    /** 
     * Returns the input items needed by this analysis algorithm. The
     * framework uses this information to let the user select appropriate
     * inputs. 
     *
     * @return The input items accepted by this analysis algorithm, that is a
     * logReader and a imported LTL Template File.
     */
    public AnalysisInputItem[] getInputItems() {
    
	AnalysisInputItem[] items = {
	
	    new AnalysisInputItem("Workflow log of a process") {
	    
		public boolean accepts(ProvidedObject object) {
		
		    Object[] o = object.getObjects();
		    boolean hasLogReader = false;
		
		    for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof LogReader) {
			    hasLogReader = true;
			};
		    }; 
		    return hasLogReader;
		}
		
	    },	
	    
	    new AnalysisInputItem("Parsed LTL file") {
	    
		public boolean accepts(ProvidedObject object) {
		
		    Object[] o = object.getObjects();
		    boolean hasLTLParser = false;
		
		    for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof LTLParser ) {
			    hasLTLParser = true;
			};
		    }; 
		    return hasLTLParser;
		}
		
	    }
	    
	};// End of new AnalysisInputItem[] inputs
        
	return items;
    }

    public JComponent analyse(AnalysisInputItem[] inputs) {
    
	LogReader logReader = null;
	LTLParser ltlParser = null;
	
	for (int j = 0; j < inputs.length; j++) {
	
	    if ( inputs[j].getCaption().equals("Workflow log of a process") ) {
	    
		Object[] o = (inputs[j].getProvidedObjects())[0].getObjects();

		for (int i = 0; logReader == null ; i++) {
		    if (o[i] instanceof LogReader) {
			logReader = (LogReader) o[i];
		    };
		    
		}; 
		
	    } else if ( inputs[j].getCaption().equals("Parsed LTL file") ) {
		
		Object[] o = (inputs[j].getProvidedObjects())[0].getObjects();

		for (int i = 0; ltlParser == null ; i++ ) {
		    if ( o[i] instanceof LTLParser ) {
			ltlParser = (LTLParser) o[i];
		    };
		    
		};
		
	    };
	
	};
	
	return new JScrollPane( 
			new TemplateGui( logReader, ltlParser, this, inputs ) );
    }
}
