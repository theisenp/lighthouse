package com.theisenp.lighthouse;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import org.junit.Test;

import com.theisenp.harbor.Peer;
import com.theisenp.harbor.Peer.Status;
import com.theisenp.lighthouse.LighthouseTableModel;

/**
 * Unit tests for {@link LighthouseTableModel}
 * 
 * @author patrick.theisen
 */
public class LighthouseTableModelTest {

	@Test
	public void testConstruct() {
		LighthouseTableModel model = new LighthouseTableModel();
		assertThat(model.getColumnCount()).isEqualTo(5);
		assertThat(model.isCellEditable(0, 0)).isFalse();
	}

	@Test
	public void testAddSinglePeer() {
		LighthouseTableModel model = new LighthouseTableModel();

		Peer peer = mockPeer(0);
		model.add(peer);

		assertThat(model.getRowCount()).isEqualTo(1);
		validate(model, 0, peer);
	}

	@Test
	public void testAddMultiplePeers() {
		LighthouseTableModel model = new LighthouseTableModel();

		List<Peer> peers = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Peer peer = mockPeer(i);
			model.add(peer);
			peers.add(peer);
		}

		assertThat(model.getRowCount()).isEqualTo(peers.size());
		for(int i = 0; i < peers.size(); i++) {
			validate(model, i, peers.get(i));
		}
	}

	@Test
	public void testUpdateSinglePeer() {
		LighthouseTableModel model = new LighthouseTableModel();

		Peer peer = mockPeer(0, 0);
		Peer update = mockPeer(0, 1);
		model.add(peer);
		model.update(update);

		assertThat(model.getRowCount()).isEqualTo(1);
		validate(model, 0, update);
	}

	@Test
	public void testUpdateMultiplePeers() {
		LighthouseTableModel model = new LighthouseTableModel();

		for(int i = 0; i < 10; i++) {
			Peer peer = mockPeer(i, 0);
			model.add(peer);
		}

		List<Peer> updates = new ArrayList<>();
		for(int i = 0; i < model.getRowCount(); i++) {
			Peer update = mockPeer(i, 1);
			model.update(update);
			updates.add(update);
		}

		assertThat(model.getRowCount()).isEqualTo(updates.size());
		for(int i = 0; i < updates.size(); i++) {
			validate(model, i, updates.get(i));
		}
	}

	@Test
	public void testRemoveSinglePeer() {
		LighthouseTableModel model = new LighthouseTableModel();

		Peer peer = mockPeer(0, 0);
		model.add(peer);
		model.remove(peer);

		assertThat(model.getRowCount()).isEqualTo(0);
	}

	@Test
	public void testRemoveMultiplePeers() {
		LighthouseTableModel model = new LighthouseTableModel();

		List<Peer> peers = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Peer peer = mockPeer(i, 0);
			model.add(peer);
			peers.add(peer);
		}

		for(Peer peer : peers) {
			model.remove(peer);
		}

		assertThat(model.getRowCount()).isEqualTo(0);
	}

	/**
	 * @param seed
	 * @return A peer generated from the given ID and the default seed of 0
	 */
	private static Peer mockPeer(int id) {
		return mockPeer(id, 0);
	}

	/**
	 * @param id
	 * @param seed
	 * @return A peer generated from the given ID
	 */
	private static Peer mockPeer(int id, int seed) {
		Peer.Builder builder = new Peer.Builder();
		builder.id(String.valueOf(id));
		builder.type(id + "-" + seed);
		builder.status(Status.values()[(id + seed) % Status.values().length]);
		return builder.build();
	}

	/**
	 * Verifies that the data for the given row in the given {@link TableModel}
	 * matches the given {@link Peer}
	 * 
	 * @param model
	 * @param row
	 * @param peer
	 */
	private static void validate(TableModel model, int row, Peer peer) {
		assertThat(model.getValueAt(row, 0)).isEqualTo(peer.getId());
		assertThat(model.getValueAt(row, 1)).isEqualTo(peer.getType());
		assertThat(model.getValueAt(row, 2)).isEqualTo(peer.getStatus());
		assertThat(model.getValueAt(row, 3)).isEqualTo(peer.getDescription());
		assertThat(model.getValueAt(row, 4)).isEqualTo(peer.getProtocols());
	}
}
