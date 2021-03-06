package org.colomoto.biolqm.tool.simulation.deterministic;

import org.colomoto.biolqm.LogicalModel;
import org.colomoto.biolqm.tool.simulation.BaseUpdater;

/**
 * Updater for the sequential scheme: all components are updated one after the other in a single successor.
 * Unlike in the synchronous method, the next updated component takes into account the new value of the previous ones.
 * 
 * @author Aurelien Naldi
 */
public class SequentialUpdater extends BaseUpdater implements DeterministicUpdater {

	private final int[] order;
	
	/**
	 * Create a new sequential random, using the default order
	 * 
	 * @param model the model for which the random is constructed
	 */
	public SequentialUpdater(LogicalModel model) {
		super(model);
		this.order = new int[size];
		for (int i=0 ; i<size ; i++) {
			order[i] = i;
		}
	}
	
	/**
	 * Create a new sequential updater, using a custom order
	 * 
	 * @param model the model for which the random is constructed
	 * @param order the ordering for sequential updates
	 */
	public SequentialUpdater(LogicalModel model, int[] order) {
		super(model);
		this.order = order;
	}

	@Override
    public byte[] getSuccessor(byte[] state) {
		// create the sequential successor
        byte[] refstate = state;
		byte[] nextstate = null;
		for (int idx: order) {
            int change = nodeChange(refstate, idx);
            if (change != 0) {
                nextstate = update(refstate, idx, change, nextstate);
                refstate = nextstate;
			}
		}
		
        return nextstate;
	}

}
