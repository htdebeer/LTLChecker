package org.processmining.importing.ltltemplate;

import org.processmining.analysis.ltlchecker.parser.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import org.processmining.framework.ui.Message;
import org.processmining.framework.plugin.*;
import org.processmining.framework.log.*;
import org.processmining.importing.*;
import org.processmining.mining.*;


public class LTLTemplateFileResult implements MiningResult, Provider {

	    LTLParser parser;
	    InputStream input;

	    JScrollPane sp;

	    public LTLTemplateFileResult( LTLParser parser, String text ) {
		this.parser = parser;
		this.input = input;
		    
		JEditorPane textContent = new JEditorPane( );
		textContent.setText( text );
		textContent.setMinimumSize( new Dimension( 200, 100) );
		textContent.setPreferredSize( new Dimension( 200, 100) );
		textContent.setEditable( false );
		sp = new JScrollPane( textContent );
		
	    }

	    public LogReader getLogReader() {
		return null;
	    }

	    public JComponent getVisualization() {
		return this.sp;
	    }
   

    public ProvidedObject[] getProvidedObjects() {
	return new ProvidedObject[] {
	    new ProvidedObject( "LTL Parser", new Object[] {this.parser} )
	};
    }
}
