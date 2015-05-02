package org.processmining.analysis.ltlchecker;

import org.processmining.framework.log.*;
import org.processmining.framework.ui.*;
import org.processmining.framework.plugin.*;
import org.processmining.analysis.*;
import att.grappa.*;


import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * LTLVerificationResult is a JPanel representing two lists, one with good
 * process instances, and one with the bad ones. Between those lists, a
 * visualisation of an ProcessInstance can be made.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class LTLVerificationResult extends JPanel implements Provider,
							ActionListener {

//FIELDS
    /** The analysis algorithm. */
    LTLChecker checker;

    /** The log checked. */
    LogReader log;

    /** The list with the good ones. */
    ArrayList correctOnes;
    /** The intlist with ids of the good ones. */
    int[] correctIdList;
    
    /** The list with the bad ones. */
    ArrayList badOnes;
    /** The intlist with ids of the bas ones. */
    int[] badIdList;

    /** View the good ones: */
    JTable correctView;

    /** View the bad ones: */
    JTable badView;

    /** Current visualised good process Instance. */
    ProcessInstance piCorrect;

    /** Current visualised bad process instance. */
    ProcessInstance piBad;

    /** viewpane for good visjes. */
    JScrollPane correctViewPane;

    /** viewpane for bad visjes. */
    JScrollPane badViewPane;

    /** Formula name. */
    String formulaName;

//CONSTRUCTORS
    public LTLVerificationResult( String formulaName, LogReader log, 
		ArrayList correctOnes, ArrayList badOnes ) {

	this.formulaName = formulaName;
	this.checker = checker;
	this.log = log;
	this.correctOnes = correctOnes;
	this.badOnes = badOnes;
	
	// Fill the list with good ids.
	Iterator i = correctOnes.iterator();
	correctIdList = new int[ correctOnes.size() ];
	int j = 0;

	while ( i.hasNext() ) {
	
	    correctIdList[j] = ((CheckResult) i.next()).getNumberInLog();
	    j++;
	    
	};
	
	// Fill the list with bad ids.
	i = badOnes.iterator();
	badIdList = new int[ badOnes.size() ];
	j = 0;

	while ( i.hasNext() ) {
	
	    badIdList[j] = ((CheckResult) i.next()).getNumberInLog();
	    j++;
	    
	};
	
	// create gui
	createGui();
    }
    
//METHODS
    
    public ProvidedObject[] getProvidedObjects() {
	ArrayList a = new ArrayList();
	
	if ( this.piCorrect != null ) {
	
	   a.add(new ProvidedObject("Visualised correct process instance", new
	   Object[] {piCorrect}));
	   
	};
	
	if ( this.piBad != null ) {
	
	   a.add(new ProvidedObject("Visualised incorrect process instance", new
	   Object[] {piBad}));
	   
	};
	
	if (correctIdList.length>0) {
	
	    a.add(new ProvidedObject( 
		    "Correct instances (" + correctIdList.length + ")" , 
		    new Object[] {new LogReader(log, correctIdList)} ));
		    
	};
	
	if (badIdList.length>0) {
	
	    a.add(new ProvidedObject(
		    "Incorrect instances (" + badIdList.length + ")",
		    new Object[] {new LogReader(log, badIdList)} ));
		    
	};
	
	ProvidedObject[] o = new ProvidedObject[a.size()];
	
	for (int i=0; i<a.size() ;i++) {
	
	    o[i] = (ProvidedObject) a.get(i);
	    
	};
	
	return o;
    }

    public void actionPerformed( ActionEvent ae ) {
	String action = ae.getActionCommand();
	
	if ( action.equals("badvis") ){
	
	    // visualise selected in bad list
	    int row = badView.getSelectedRow();
	    
	    if ( row >= 0 ) {
	     
		createVisualisation( 
		    ((CheckResult) badOnes.get( 
			row )).getProcessInstance( log ), false );
		
	    };// else do nothing because there is nothing selected.
	    
	} else if ( action.equals("goodvis") ) {
	
	    // visualise selected in good list
	    int row = correctView.getSelectedRow();
	    
	    if ( row >= 0 ) {
	    
		createVisualisation( 
		    ((CheckResult) correctOnes.get( 
			row )).getProcessInstance( log ), true );
		
	    };// else do nothing because there is nothing selected.
	    
	};
	
    }

    private void createGui() {

	this.setLayout( new GridBagLayout() );
	GridBagConstraints c = new GridBagConstraints();
	
	JLabel formulaLabel = new JLabel(
	"<html>Checked formula :  <b>" + formulaName
	+ "</b></html>");
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	c.insets = new Insets( 10, 10, 10, 10);
	this.add( formulaLabel, c );
	
	// Show two lists with CheckResults
	JTabbedPane tp = new JTabbedPane();

	tp.add( "<html>Correct process instances (" + 
		  this.correctOnes.size() + ")</html>",
		  createCorrectTab() );
		  
	tp.add( "<html>Incorrect process instances (" +
		  this.badOnes.size() + ")</html>",
		  createBadTab() );

	c.fill = GridBagConstraints.BOTH;
	c.gridx = 0;
	c.gridy = 1;
	c.weightx = 1.0;
	c.weighty = 1.0;
	this.add( tp, c );
    }

    private JSplitPane createCorrectTab( ) {
	JSplitPane sp = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
	sp.setDividerLocation( 200 );
	sp.setOneTouchExpandable( true );

	// Create visualisationpane:
	correctViewPane = new JScrollPane(  );
	correctViewPane.setMinimumSize( new Dimension( 300,300 ) );
	correctViewPane.setPreferredSize( new Dimension( 300,300 ) );
	sp.setRightComponent( correctViewPane );

	// Table with vis knob
	JPanel pan = new JPanel();
	pan.setLayout( new BorderLayout() );

	correctView = new JTable(new CheckResultModel(
		    this.correctOnes, 
		    "name (id)") );
	correctView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	correctView.setColumnSelectionAllowed( false );
	correctView.setRowSelectionAllowed( true );
	correctView.setCellSelectionEnabled( true );
	MouseListener correctMouse = new MouseAdapter() {
	
	    public void mouseClicked( MouseEvent me ) {
	
		if ( 
		    ( me.getClickCount() == 2 ) && 
		    ( me.getButton() == MouseEvent.BUTTON1 ) ) {
		    // There is a double click with left mouse button 
		    // check if the component clicked on is the JTable
		    
		    if ( me.getComponent() == correctView ) {
			// it is the table which is selected, so visualise the
			// selected item, if any.
			// Visualise selected row in good list
			int row = correctView.getSelectedRow();
			
			if ( row >= 0 ) {
			
			    createVisualisation( 
				((CheckResult) correctOnes.get( 
				    row )).getProcessInstance( log ), true );
			    
			};// else do nothing because there is nothing selected.
		    };
		};
	    }
	};
	correctView.addMouseListener( correctMouse );
	JScrollPane correctSP = new JScrollPane( correctView );
	correctSP.setMinimumSize( new Dimension( 200,250 ) );
	correctSP.setPreferredSize( new Dimension( 200,250 ) );
	pan.add( correctSP, BorderLayout.CENTER);

	JButton goodVisButton = new JButton(
	    "Visualize selected");
	goodVisButton.setActionCommand( "goodvis" );
	goodVisButton.setMnemonic( KeyEvent.VK_V );
	goodVisButton.addActionListener( this );
	JPanel bpb = new JPanel();
	bpb.add( goodVisButton );
	pan.add( bpb, BorderLayout.PAGE_END );

	sp.setLeftComponent( pan );

	return sp;	
    }

    private JSplitPane createBadTab( ) {
	JSplitPane sp = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
	sp.setDividerLocation( 200 );
	sp.setOneTouchExpandable( true );

	// Create visualisationpane:
	badViewPane = new JScrollPane(  );
	badViewPane.setMinimumSize( new Dimension( 300,300 ) );
	badViewPane.setPreferredSize( new Dimension( 300,300 ) );
	sp.setRightComponent( badViewPane );

	// Table with vis knob
	JPanel pan = new JPanel();
	pan.setLayout( new BorderLayout() );

	badView = new JTable(new CheckResultModel(
		    this.badOnes, 
		    "name (id)") );
	badView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	badView.setColumnSelectionAllowed( false );
	badView.setRowSelectionAllowed( true );
	badView.setCellSelectionEnabled( true );
	MouseListener badMouse = new MouseAdapter() {
	
	    public void mouseClicked( MouseEvent me ) {
	
		if ( 
		    ( me.getClickCount() == 2 ) && 
		    ( me.getButton() == MouseEvent.BUTTON1 ) ) {
		    // There is a double click with left mouse button 
		    // check if the component clicked on is the JTable
		    
		    if ( me.getComponent() == badView ) {
			// it is the table which is selected, so visualise the
			// selected item, if any.
			// Visualise selected row in good list
			int row = badView.getSelectedRow();
			
			if ( row >= 0 ) {
			
			    createVisualisation( 
				((CheckResult) badOnes.get( 
				    row )).getProcessInstance( log ), false );
			};// else do nothing because there is nothing selected.
		    };
		};
	    }
	};
	badView.addMouseListener( badMouse );
	JScrollPane badSP = new JScrollPane( badView );
	badSP.setMinimumSize( new Dimension( 200,250 ) );
	badSP.setPreferredSize( new Dimension( 200,250 ) );
	pan.add( badSP, BorderLayout.CENTER );

	JButton badVisButton = new JButton(
	    "Visualize selected");
	badVisButton.setActionCommand( "badvis" );
	badVisButton.setMnemonic( KeyEvent.VK_V );
	badVisButton.addActionListener( this );
	JPanel bpb = new JPanel();
	bpb.add(badVisButton);
	pan.add( bpb, BorderLayout.PAGE_END );

	sp.setLeftComponent( pan );

	return sp;	
    }

    private void createVisualisation( ProcessInstance pi, boolean correct ) {
    
	if ( correct ) {
	
	    this.piCorrect = pi;
	    correctViewPane.getViewport().add( pi.getGrappaVisualization(), null);
	    
	} else { 
	
	    this.piBad = pi;
	    badViewPane.getViewport().add( pi.getGrappaVisualization(), null);
	    
	}
    }



}
