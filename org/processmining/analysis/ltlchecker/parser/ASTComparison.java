package org.processmining.analysis.ltlchecker.parser;
/* Generated By:JJTree: Do not edit this line. ASTComparison.java */

import java.util.*;

public class ASTComparison extends SimpleNode {
    /** Strings is a container of all the strings is the set. */
    TreeSet strings;

    /** The lefthand side attribute:*/
    Attribute attr;

    public void setAttribute( Attribute attr ) {
	this.attr = attr;
    }

    public Attribute getAttribute() {
	return this.attr;
    }
    

    public void setStrings(TreeSet strings) {
	this.strings = strings;
    }

    public TreeSet getStrings() {
	return this.strings;
    }
  public ASTComparison(int id) {
    super(id);
  }

  public ASTComparison(LTLParser p, int id) {
    super(p, id);
  }

}
