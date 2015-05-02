package org.processmining.importing.ltltemplate;

import org.processmining.analysis.ltlchecker.parser.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import org.processmining.framework.ui.Message;
import org.processmining.importing.*;
import org.processmining.mining.*;
import org.processmining.framework.ui.filters.*;


public class LTLTemplateImport implements ImportPlugin {

	public LTLTemplateImport() {}

	public String getName() {
		return "LTL template file";
	}

	public javax.swing.filechooser.FileFilter getFileFilter() {
		return new GenericFileFilter("ltl");
	}

	public MiningResult importFile(InputStream input) throws IOException {
		// Do the parsing
	    try {
		// Open and parse the file
		
		BufferedReader buf = new BufferedReader(
			new InputStreamReader( input ) );
		
		String rule = "";
		String text = "";
		while ( (rule = buf.readLine()) != null ) {
		    text += rule + "\n";
		};
		buf.close();	
	    	
		LTLParser parser = new LTLParser( 
			new BufferedReader(
				new StringReader( text ) ) );
		parser.init();
		parser.parse( );
		Message.add("Parsing complete.");
	
		// Put the text into a window:
		return new LTLTemplateFileResult( parser, text );
		
	    } catch( org.processmining.analysis.ltlchecker.parser.ParseException e ) {
		// An error by parsing.
			
		Message.add(
		    "?? " + e.getMessage( ),
		    Message.ERROR
		);
		return null;
	    }
    }

	public String getHtmlDescription() {
		return 
          "<h2>LTL Template File</h2>" +
          "<p>" +
            "A LTL template file exists of attribute definitions" +
            " and formula definitions specifying properties of a" +
            " process log." +
          "</p";
	}
}
