package com.theisenp.lighthouse;

import com.theisenp.harbor.Harbor;
import com.theisenp.harbor.Peer;

/**
 * A {@link Harbor.Listener} that updates a {@link LighthouseTableModel}
 * 
 * @author patrick.theisen
 */
public class LighthouseListener implements Harbor.Listener {
	private final LighthouseTableModel model;

	/**
	 * @param model
	 */
	public LighthouseListener(LighthouseTableModel model) {
		this.model = model;
	}

	@Override
	public void onConnected(Peer peer) {
		model.add(peer);
	}

	@Override
	public void onActive(Peer peer) {
		model.update(peer);
	}

	@Override
	public void onInactive(Peer peer) {
		model.update(peer);
	}

	@Override
	public void onDisconnected(Peer peer) {
		model.remove(peer);
	}
}
