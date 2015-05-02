package org.processmining.analysis.ltlchecker;

import javax.swing.table.*;
import java.text.*;
import java.util.*;
import java.awt.Toolkit;
import org.processmining.analysis.ltlchecker.parser.*;
import org.processmining.analysis.ltlchecker.formulatree.*;

/**
 * ParamData is an implementation of the {@see TableModel} class to be used as
 * model for the tabel for filling the parameters of a template ltl formula.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class ParamData extends AbstractTableModel {
    
    /** Column names. */
    private String[] columnNames = { "Name", "Type", "Value" }; 

    /** Parameter data. */
    private Object[][] data;

    /** has params? */
    boolean isFinal;

    ArrayList params;

    public ParamData( ArrayList params) {
	
	super();
	this.params = params;
	isFinal = false;
	data = new Object[ params.size() ][3];
	Attribute var;
	
	Iterator i = params.iterator();
	int j = 0;
	Date currentDate = new Date();

	while ( i.hasNext() ) {
	    // Initialize every row of the table with initial values and
	    // setting the name and type of the parameters.
	
	    var = (Attribute) i.next();
	    
	    switch (var.getType()) {
	    
		case Attribute.NUMBER : {
		    data[j][0] = var.getValue();
		    data[j][1] = "number";
		    data[j][2] = "0.0";
		} break;
		
		case Attribute.STRING : {
		    data[j][0] = var.getValue();
		    data[j][1] = "string";
		    data[j][2] = "type a string";
		} break;
		
		case Attribute.SET : {
		    data[j][0] = var.getValue();
		    data[j][1] = "set";
		    data[j][2] = "type a string";
		} break;
		
		case Attribute.DATE : {
		    SimpleDateFormat sdf = var.getDateParser();
		    data[j][0] = var.getValue();
		    data[j][1] = "date";
		    data[j][2] = sdf.format( currentDate );
		} break;
	    };
	    
	    j++;
	};
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public Object getValueAt(int row, int col) {
    
	if ( data[row][col] == null ) {
	    // When a value is null, that value can not be used for checking
	    // the selected property. Therefor a empty string is returned.
	    // Actually, only if "" can be parsed to a number of date (in case
	    // of such parameter) it can be that there is a parse error
	    // generated while checking. In that case a comparison returns
	    // always false.
	    return "";
	    
	} else {
	
	    return data[row][col];
	    
	}
    }

    public boolean isCellEditable(int row, int col) {
	if (! isFinal ) {
	
	    if (col == 2) {
	    
	        return true;
		
	    } else {
	    
		return false;
		
	    }
	    
	} else {
	
	    return false;
	    
	}
    }

    public void setValueAt(Object value, int row, int col) {
    
	if ( correctValue( (String) value, row, col) ) {
	    // Only parseble strings may be set as value, this is important
	    // for parameters of type number and date.
	    data[row][col] = value;
	    fireTableCellUpdated(row, col);
	    
	} else {
	
	    // beep
	    Toolkit.getDefaultToolkit().beep();
	    
	}
    }

    private boolean correctValue( String value, int row, int col ) {
	// Compute if a string is a correct value for the parameter.
	boolean result = false;
	Attribute param = (Attribute) params.get( row );

	switch ( param.getType() ) {
	
	    case Attribute.NUMBER : 
		try {
		
		    float f = ( new Float( value )).floatValue();
		    result = true;
		    
		} catch( NumberFormatException e ) {
		
		    return false;
		    
		}; break;
		
	    case Attribute.STRING :
		result = true; break;
		
	    case Attribute.SET :
		result = true; break;
		
	    case Attribute.DATE :
		try {    
		
		    SimpleDateFormat sdf = param.getDateParser();
		    Date d = sdf.parse( value );
		    result = true;
		    
		} catch( java.text.ParseException e ) {
		    
		    return false;
		    
		}; break;    
	};
	return result;	
    }

    public void removeData() {
	data = new Object[0][3];
    }

    /**
     * The parameters of this table are used as values for the selected
     * formula. These values must be added as a list of so called {@see
     * Substitutes}, therefor this method, to create such a list.
     *
     * @param parser The LTLParser containing all the information needed to
     * create the substitutes list.
     *
     * @return The substitutes list of parameters of the selected formula.
     */
    public Substitutes getSubstitutes( LTLParser parser ) {
	Substitutes result = new Substitutes();
	
	// add substitutions
	    for ( int i = 0; i < data.length; i++ ) {
		// for each parameter;
		
		ValueNode val = null;
		Attribute par = (Attribute) params.get( i );
		
		switch ( par.getType() ) {
		
		    case Attribute.NUMBER :
			NumberValueNode nval = new NumberValueNode( NumberValueNode.VALUE );
			nval.setValue( new NumberAttribute( 
					(String) getValueAt( i, 2 ),
					Attribute.LITERAL,
					(Attribute) params.get( i ))
			);
			val = nval; break;
			
		    case Attribute.SET :
			SetValueNode setval = new SetValueNode( SetValueNode.VALUE );
			setval.setValue( new SetAttribute( 
				(String) getValueAt( i, 2 ),
				Attribute.LITERAL,
				(Attribute) params.get( i ))
			    );
			val = setval; break;
			
		    case Attribute.STRING :
			StringValueNode sval = new StringValueNode( StringValueNode.VALUE );
			sval.setValue( new StringAttribute( 
				(String) getValueAt( i, 2 ),
				Attribute.LITERAL,
				(Attribute) params.get( i ))
			    );
			val = sval; break;
			
		    case Attribute.DATE :
			DateAttribute var = (DateAttribute) params.get( i );
			DateValueNode dval = new DateValueNode( DateValueNode.VALUE );
			dval.setValue( new DateAttribute( 
				(String) getValueAt( i, 2 ),
				Attribute.LITERAL,
				(Attribute) params.get( i ))
			    );
			val = dval; break;
		};

		result.add( (String) data[i][0], val );
	    };

	return result;
    }
}
