
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
 * CheckResultModel is a tablemodel to store the checkresults of a check. For
 * the correct and incorrect instances of the check, such a model is needed.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class CheckResultModel extends AbstractTableModel {

    private String[] columnNames;
    private ArrayList checkResults;

    public CheckResultModel( ArrayList checkResults, String kind ) {
    
	columnNames = new String[1];
	columnNames[0] = kind;
	this.checkResults = checkResults;
	
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return checkResults.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public Object getValueAt(int row, int col) {
        return checkResults.get(row);
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
	return false;
    }

}
