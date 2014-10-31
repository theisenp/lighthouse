package com.theisenp.lighthouse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.theisenp.harbor.Peer;
import com.theisenp.lighthouse.LighthouseListener;
import com.theisenp.lighthouse.LighthouseTableModel;

/**
 * Unit tests for {@link LighthouseListener}
 * 
 * @author patrick.theisen
 */
public class LighthouseListenerTest {
	private Peer peer;
	private LighthouseTableModel model;
	private LighthouseListener listener;

	@Before
	public void setUp() {
		peer = mock(Peer.class);
		model = mock(LighthouseTableModel.class);
		listener = new LighthouseListener(model);
	}

	@Test
	public void testOnConnected() {
		listener.onConnected(peer);
		verify(model, times(1)).add(peer);
	}

	@Test
	public void testOnActive() {
		listener.onActive(peer);
		verify(model, times(1)).update(peer);
	}

	@Test
	public void testOnInactive() {
		listener.onInactive(peer);
		verify(model, times(1)).update(peer);
	}

	@Test
	public void testOnDisconnected() {
		listener.onDisconnected(peer);
		verify(model, times(1)).remove(peer);
	}
}
