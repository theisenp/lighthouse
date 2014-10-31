package com.theisenp.lighthouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.theisenp.harbor.Peer;

/**
 * A {@link TableModel} for {@link Peer} data
 * 
 * @author patrick.theisen
 */
public class LighthouseTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNS = { "ID", "Type", "Status", "Description", "Protocols" };

	private final Map<String, Integer> ids = new HashMap<>();
	private final List<Peer> peers = new ArrayList<>();

	/**
	 * Adds a new {@link Peer} to the table
	 * 
	 * @param peer
	 */
	public synchronized void add(Peer peer) {
		// Check for an existing peer with this ID
		String id = peer.getId();
		if(ids.containsKey(id)) {
			String message = "There is an existing peer with the given ID";
			throw new IllegalArgumentException(message);
		}

		// Add the new peer
		int row = peers.size();
		peers.add(peer);
		ids.put(id, row);
		fireTableRowsInserted(row, row);
	}

	/**
	 * Updates an existing {@link Peer} in the table
	 * 
	 * @param peer
	 */
	public synchronized void update(Peer peer) {
		// Check for an existing peer with this ID
		String id = peer.getId();
		if(!ids.containsKey(id)) {
			String message = "There is no existing peer with the given ID";
			throw new IllegalArgumentException(message);
		}

		// Update the existing peer
		int row = ids.get(id);
		peers.remove(row);
		peers.add(row, peer);
		fireTableRowsUpdated(row, row);
	}

	/**
	 * Removes an existing {@link Peer} from the table
	 * 
	 * @param peer
	 */
	public synchronized void remove(Peer peer) {
		// Check for an existing peer with this ID
		String id = peer.getId();
		if(!ids.containsKey(id)) {
			String message = "There is no existing peer with the given ID";
			throw new IllegalArgumentException(message);
		}

		// Remove the existing peer
		int row = ids.get(id);
		peers.remove(row);
		fireTableRowsDeleted(row, row);

		// Update the remaining peer IDs
		ids.remove(id);
		for(int i = row; i < peers.size(); i++) {
			String remaining = peers.get(i).getId();
			ids.put(remaining, ids.get(remaining) - 1);
		}
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	@Override
	public int getRowCount() {
		synchronized(this) {
			return peers.size();
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		synchronized(this) {
			return getPeerValue(peers.get(rowIndex), columnIndex);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * @param peer
	 * @param index
	 * @return The attribute of the given {@link Peer} that corresponds to the
	 * given index
	 */
	private static Object getPeerValue(Peer peer, int index) {
		switch(index) {
			case 0:
				return peer.getId();
			case 1:
				return peer.getType();
			case 2:
				return peer.getStatus();
			case 3:
				return peer.getDescription();
			case 4:
				return peer.getProtocols();
			default:
				String message = "Unexpected peer value index: " + index;
				throw new RuntimeException(message);
		}
	}
}
