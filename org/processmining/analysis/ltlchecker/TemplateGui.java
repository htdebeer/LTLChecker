package org.processmining.analysis.ltlchecker;

import org.processmining.analysis.ltlchecker.parser.*;
import org.processmining.analysis.ltlchecker.formulatree.*;
import org.processmining.framework.ui.*;
import org.processmining.framework.plugin.*;
import org.processmining.analysis.*;

import org.processmining.framework.log.*;

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
 * TemplateGui specifies a gui for selecting an template formula to check, see
 * te description of such formula and to valuate the parameters of the
 * formula. Furthermore some checkoptions can be set. After valuating all
 * parameters, the check can be performed.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class TemplateGui extends JPanel implements ActionListener {

// FIELDS

    /** List with formula templates to select from. */
    JComboBox formulaList;

    /** A field for the description. */
    JEditorPane descriptionPane;

    /** A table for valuate the parameters. */
    JTable paramTable;

    /** Check option: till first success. */
    boolean firstSuccess;

    /** Check option: till first failure. */
    boolean firstFailure;

    /** Checkbutton. */
    JButton checkButton;

    /** Exitbutton. */
    JButton exitButton;

    /** HelpButton. */
    JButton helpButton;

    /** Parser with parsedata. */
    LTLParser parser;

    /** Sets with the sets. */
    SetsSet sets;

    /** The log to check. */
    LogReader log;
	
	ArrayList goodResults;
	ArrayList badResults;

    LTLChecker checker;
    AnalysisInputItem[] inputs;

// CONSTRUCTORS
	
    public TemplateGui( LogReader logreader, LTLParser parser, 
			    LTLChecker checker, AnalysisInputItem[] inputs ) {

	this.checker = checker;
	this.inputs = inputs;
	
	this.parser = parser;
	this.log = logreader;
	
	// Build the setsset
	sets = null;	
	
	// Checkoption is `whole process':
	this.firstSuccess = false;
	this.firstFailure = false;

	createGui();

	// Finalize the gui:
	
	//this.pack();
	this.setVisible( true );
    }
    
// METHODS
    
    /**
     * Get the option `till first success'
     *
     * @return Till first success?
     */
    public boolean getTillFirstSuccess() {
	return this.firstSuccess;
    }
    
    /**
     * Get the option `till first failure'
     *
     * @return Till first fialure?
     */
    public boolean getTillFirstFailure() {
	return this.firstFailure;
    }
    
    /**
     * Get the option `the whole process', that is, not till first failure and not
     * till first success implies whole process.
     *
     * @return The whole process?
     */
    public boolean getWholeProcess() {
	return ( ( ! this.firstSuccess ) && ( ! this.firstFailure ) );
    }

    private String makeHTMLPage( String text ) {
	return
	    "<html>\n"	+
	    "\t"    +	"<head></head>\n"   +
	    "\t"    +	"<body>\n"  +
	    "\t"    +	"\t" + text +
	    "\t"    +	"</body>\n" +
	    "</html>";
    }

private void createGui() {
    
    // Create GUI
    // Layout
    //Container this = this.getContentPane();
    
    this.setLayout( new GridBagLayout() );
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // FormulsSelection
    // Add the selection of a formula:
    JLabel selectFormulaLabel = new JLabel(
	"<html>Select formula : </html>");
    
    formulaList = new JComboBox( );
    //formulaList.setEnabled( false );
    formulaList.setActionCommand( "formula" );
	
    // Fill with the formula names.
    
    Iterator i = parser.getVisibleFormulaNames().iterator();
    SimpleNode f;

    while ( i.hasNext() ) {
	formulaList.addItem( (String) i.next() );    	
    };	

    if ( formulaList.getModel().getSize() <= 0 ) {
	formulaList.addItem( "no formula" );
    };
    
    descriptionPane = new JEditorPane();
    descriptionPane.setEditable( false );
    descriptionPane.setContentType( "text/html" );
    descriptionPane.setText( makeHTMLPage(
	"<H1>No description available.</H1>") );
    JScrollPane descPane = new JScrollPane( descriptionPane );
    descPane.setPreferredSize(new Dimension(400, 140));
    descPane.setMinimumSize(new Dimension(400, 140));
    
    paramTable = new JTable();
    paramTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    JScrollPane paramPane = new JScrollPane( paramTable );
    paramPane.setPreferredSize(new Dimension(400, 70));
    paramPane.setMinimumSize(new Dimension(400, 70));
    ParamData pd = new ParamData( new ArrayList() );
    paramTable.setModel( pd );
    
    JPanel checkOptionsPanel = createCheckOptionsPanel();
    JPanel buttonPanel = createLowButtonPanel();    
    
    formulaList.addActionListener( this );
    formulaList.setSelectedItem( formulaList.getItemAt( 0 ) );
    
    selectFormulaLabel.setLabelFor( formulaList );
    selectFormulaLabel.setDisplayedMnemonic( KeyEvent.VK_F );
   
   // Formula label, list and checkbutton.
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets( 10, 5, 0, 0);
    this.add( selectFormulaLabel, gbc );
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.insets = new Insets( 0, 5, 0, 0);
    this.add( formulaList, gbc );
    
    // Add the other buttons

    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new Insets( 0, 5, 0, 0 );
    this.add( buttonPanel, gbc );
    
       
    // descriptionPane
    JLabel descriptionLabel = new JLabel(
	"<html>Description :</html>");
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.insets = new Insets( 0, 5, 0, 0);
    this.add( descriptionLabel, gbc );
    
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.weighty = 1.0;
    gbc.weightx = 1.0;
    gbc.insets = new Insets( 0,5,0,5);
    this.add( descPane, gbc );

    // Add the parameter Panel
    JLabel paramLabel = new JLabel(
	"<html>Valuate the parameters :</html>");
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.insets = new Insets( 0, 5, 0, 0 );
    this.add( paramLabel, gbc );

    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    gbc.weighty = 0.5;
    gbc.insets = new Insets( 5, 5, 5, 5);
    this.add( paramPane, gbc );

    // Add the checkOptions: right of the formula and button:
    
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridheight = 3;
    gbc.insets = new Insets( 10, 5, 0, 5 );
    this.add( checkOptionsPanel, gbc );

    
    }

private JPanel createLowButtonPanel() {
    JPanel buttonPanel = new JPanel();

    /*helpButton = new JButton( "Help" );
    helpButton.setActionCommand( "help" );
    helpButton.setMnemonic( KeyEvent.VK_H );
    helpButton.addActionListener( this );
    helpButton.setToolTipText(
	"View the help files for the ltlchecker");
    buttonPanel.add( helpButton );*/

    checkButton = new JButton(
	"Check" );
    checkButton.setEnabled( false );
    checkButton.setActionCommand("check");
    checkButton.setMnemonic( KeyEvent.VK_C );
    checkButton.addActionListener( this );
    checkButton.setToolTipText(
	"Check the selected property, results in a new tab.");
    buttonPanel.add( checkButton );
    
    return buttonPanel;
}

private JPanel createCheckOptionsPanel() {

    JPanel checkOptionsPanel = new JPanel( );
    checkOptionsPanel.setLayout( 
	    new BoxLayout( checkOptionsPanel, BoxLayout.Y_AXIS ) );

    TitledBorder tb = BorderFactory.createTitledBorder( 
	    "Check options" );
    checkOptionsPanel.setBorder( tb );

    JRadioButton wholeRB = new JRadioButton("Check whole process.");
    wholeRB.setMnemonic( KeyEvent.VK_W );
    wholeRB.setActionCommand("whole");
    wholeRB.addActionListener( this );
    wholeRB.setSelected( true );

    JRadioButton failureRB = new JRadioButton("Check till first failure.");
    failureRB.setMnemonic( KeyEvent.VK_F );
    failureRB.setActionCommand("failure");
    failureRB.addActionListener( this );
    failureRB.setSelected( false );
    
    JRadioButton successRB = new JRadioButton("Check till first success.");
    successRB.setMnemonic( KeyEvent.VK_S );
    successRB.setActionCommand("success");
    successRB.addActionListener( this );
    successRB.setSelected( false );

    ButtonGroup bg = new ButtonGroup();
    bg.add(wholeRB);
    bg.add(failureRB);
    bg.add(successRB);

    checkOptionsPanel.add(wholeRB);
    checkOptionsPanel.add(failureRB);
    checkOptionsPanel.add(successRB);

    return checkOptionsPanel;
    }

    public void actionPerformed( ActionEvent ae ) {
	String action = ae.getActionCommand();

	// Per actioncommand do an action:
	if ( action.equals( "help" ) ) {
		System.out.println("Help");
	} else if ( action.equals( "whole" ) ) {
		firstFailure = false;
		firstSuccess = false;
	} else if ( action.equals( "failure" ) ) {
		firstFailure = true;
		firstSuccess = false;
	} else if ( action.equals( "success" ) ) {
		firstFailure = false;
		firstSuccess = true;
	} else if ( action.equals( "formula" ) ) {
	    // Get the selected formula
	    String selectedFormula = (String) formulaList.getSelectedItem();

	    if ( ! selectedFormula.equals("no formula") ) {
		SimpleNode formula = parser.getFormula(
			selectedFormula );
		
		// Update description this
		ASTformulaDefinition form = 
			(ASTformulaDefinition) formula;
		String description = form.getDescription();
		descriptionPane.setText( 
			makeHTMLPage( description ) );
		
		// Update param table

		ParamData pd = new ParamData(
		    parser.getParameters( selectedFormula ) );
		paramTable.setModel( pd );

		// enable checking
		checkButton.setEnabled( true );

	    } else {
		// no formula, so there is nothing to do.
		// Maybe emty the handel
	    };
	} else if ( action.equals( "check" ) ) {
		// Do the check, that is, build the formulatree and check
		// it afterwards. If it is not final, supply the tree with
		// the substitutions.

		checkButton.setEnabled( false );

		final String formulaName = (String) formulaList.getSelectedItem();
		
		if ( 
		    ! ( 
			( formulaName.equals("") ) 
			|| 
			( formulaName == null     )
		    ) 
		    ) {
		    // There is something to build, so do it.		
		   
		   SwingWorker w = new SwingWorker() {
		      public Object construct() {

			Progress p;
			int teller = 0;
	
			if ( sets == null ) {
			    p  = new Progress("Checking the property", 0,
			    2 * log.getLogSummary().getNumberOfProcessInstances() );
			    p.setNote( "Creating the attribute sets");

			    sets = new SetsSet( parser, log.getLogSummary() );
			    sets.fill( log, p );
			    teller =  log.getLogSummary().getNumberOfProcessInstances();
			} else {	
			    p  = new Progress("Checking the property", 0,
				    log.getLogSummary().getNumberOfProcessInstances() );
			};

			p.setNote(
			    "Building formula tree");
		   
			SimpleNode formula = parser.getFormula(
				formulaName );

			TreeBuilder tb = new TreeBuilder( parser, formulaName, sets );
			
			// Create the set of substitutes if any. THat is, if
			// it is an template, it can have already substitution
			// now, so add them. Otherwise, an empty substitutes
			// set is enough.
						
			RootNode root = new RootNode();
			Substitutes sss = 
			    ( ( ParamData) paramTable.getModel()).getSubstitutes(
			    parser );
			sss.setBinder( root );
			
			root.setFormula(
				(FormulaNode) tb.build(
				    parser.getFormula( formulaName ) ,
				    sss,
				    root 
				)
			);
	
			// Do the verification
	
			goodResults = new ArrayList();
			badResults = new ArrayList();

			int piNumber = 0;
			
			boolean run = true;
			boolean fullfill = false;

			log.reset();

			while ( log.hasNext() && run && !p.isCanceled() ) {
	    
			    ProcessInstance pi = log.next();
			    AuditTrailEntries ates = pi.getAuditTrailEntries();

			    p.setNote( pi.getName() );
			    p.setProgress( teller );
			    teller++;

			    ates.reset();

			    // Because the pi must be walked through in reverse order, and the
			    // framework does not support this, the ates of this pi are first
			    // readed in into a list.
			    
			    LinkedList atesList = new LinkedList();

			    while ( ates.hasNext() ) {
				AuditTrailEntry at = ates.next();
				atesList.add( at );
			    };

			    // But first initialize the formula, that is , create the initial
			    // values, or the values of the n+1th ate of pi, given that there
			    // are just n ates in pi. This can also be seen as computing te
			    // value of he formula for an empty ateslist.
			    //fullfill = formulaTree.init( );
			    fullfill = false;
			    
			    for (int j = atesList.size(); j >= 0; j -- ) {
				// start with n + 1 ...
				fullfill = root.value( pi, atesList, j );
			    };	
			
			    // The computed fullfill of the last ate of the pi is the value of
			    // the whole pi, because by dynamic programming the other ates are
			    // already computed and used.
			    if ( !fullfill && firstFailure ) {
				// stop at first failure
				run = false;
			    } else if ( fullfill && firstSuccess ) {
				// stop at first success
				run = false;
			    };
				    
			    if ( fullfill ) {
				goodResults.add( new CheckResult( piNumber, pi ) );
			    } else {
				badResults.add( new CheckResult( piNumber, pi ) );
			    };

			    piNumber++;
			};
			Boolean b = new Boolean(p.isCanceled());
			p.close();
			return b;
		    };

		    public void finished() {
			if ( ! ((Boolean) get()).booleanValue() ) {
			    MainUI.getInstance().createAnalysisResultFrame(
				checker,
				inputs,
				new LTLVerificationResult( formulaName, log, goodResults, badResults )
			    );
			};
			checkButton.setEnabled( true );
		    };
		};
		w.start();
		
		};

	    };
	
    }

}


