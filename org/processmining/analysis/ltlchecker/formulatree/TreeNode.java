package org.processmining.analysis.ltlchecker.formulatree;

/**
 * Main class for treenodes, for formula nodes as well as for valuenodes.
 *
 * @version 0.1
 * @author HT de Beer
 */
public class TreeNode {

    /** The number of ate in pi currently working on. This is needed for
     * binding values: which ate to use to get the value?
     */
    protected int nr;

    public TreeNode( ) {
	nr = 0;
    }

    public int getNr() {
	return this.nr;
    }

}
