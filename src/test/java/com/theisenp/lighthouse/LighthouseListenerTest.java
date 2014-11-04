package com.theisenp.lighthouse;

import static org.fest.assertions.Fail.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.theisenp.harbor.Peer;

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
	public void testOnConnected() throws InterruptedException {
		CountDownAnswer answer = new CountDownAnswer(1);
		doAnswer(answer).when(model).add(peer);

		listener.onConnected(peer);
		if(!answer.await(1, TimeUnit.SECONDS)) {
			fail("Peer was never added to the model");
		}
	}

	@Test
	public void testOnActive() throws InterruptedException {
		CountDownAnswer answer = new CountDownAnswer(1);
		doAnswer(answer).when(model).update(peer);

		listener.onActive(peer);
		if(!answer.await(1, TimeUnit.SECONDS)) {
			fail("Peer was never updated in the model");
		}
	}

	@Test
	public void testOnInactive() throws InterruptedException {
		CountDownAnswer answer = new CountDownAnswer(1);
		doAnswer(answer).when(model).update(peer);

		listener.onInactive(peer);
		if(!answer.await(1, TimeUnit.SECONDS)) {
			fail("Peer was never updated in the model");
		}
	}

	@Test
	public void testOnDisconnected() throws InterruptedException {
		CountDownAnswer answer = new CountDownAnswer(1);
		doAnswer(answer).when(model).remove(peer);

		listener.onDisconnected(peer);
		if(!answer.await(1, TimeUnit.SECONDS)) {
			fail("Peer was never removed from the model");
		}
	}

	/**
	 * An {@link Answer} that counts down each time it's called
	 * 
	 * @author patrick.theisen
	 */
	private static class CountDownAnswer extends CountDownLatch implements Answer<Void> {

		/**
		 * @param count
		 */
		public CountDownAnswer(int count) {
			super(count);
		}

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			countDown();
			return null;
		}
	}
}
