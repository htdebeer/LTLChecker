package org.processmining.analysis.ltlchecker.parser;
/* Generated By:JJTree: Do not edit this line. ASTQuantification.java */

public class ASTQuantification extends SimpleNode {

    private Attribute dummy;

    public void setDummy( Attribute dummy ) {
	this.dummy= dummy;
    }

    public Attribute getDummy() {
	return this.dummy;
    }

  public ASTQuantification(int id) {
    super(id);
  }

  public ASTQuantification(LTLParser p, int id) {
    super(p, id);
  }

}
