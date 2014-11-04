package com.theisenp.lighthouse;

import javax.swing.SwingUtilities;

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
	public void onConnected(final Peer peer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.add(peer);
			}
		});
	}

	@Override
	public void onActive(final Peer peer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.update(peer);
			}
		});
	}

	@Override
	public void onInactive(final Peer peer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.update(peer);
			}
		});
	}

	@Override
	public void onDisconnected(final Peer peer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.remove(peer);
			}
		});
	}
}
