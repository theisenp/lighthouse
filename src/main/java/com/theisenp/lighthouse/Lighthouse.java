package com.theisenp.lighthouse;

import java.net.UnknownHostException;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.joda.time.Duration;

import com.theisenp.harbor.Harbor;
import com.theisenp.harbor.Peer;
import com.theisenp.harbor.Peer.Status;

/**
 * A display of all the active Harbor clients on the network
 * 
 * @author patrick.theisen
 */
public class Lighthouse extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Lighthouse";
	private static final Peer SELF;
	static {
		Peer.Builder builder = new Peer.Builder();
		builder.id(UUID.randomUUID().toString());
		builder.type("lighthouse");
		builder.status(Status.ACTIVE);
		builder.description("A Lighthouse instance");
		SELF = builder.build();
	}

	/**
	 * @param harbor
	 */
	public Lighthouse(Harbor harbor) {
		// TODO
	}

	/**
	 * @param args
	 * @throws UnknownHostException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		Harbor.Builder builder = new Harbor.Builder();
		builder.self(SELF);

		// Parse the command line options
		Options options = new Options();
		options.addOption("a", "address", true, "The IP component of the LCM address");
		options.addOption("p", "port", true, "The port component of the LCM address");
		options.addOption("ttl", true, "The TTL component of the LCM address");
		options.addOption("period", true, "The publish period length in milliseconds");
		options.addOption("timeout", true, "The timeout length in milliseconds");
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = parser.parse(options, args);

		// Set the address
		if(commandLine.hasOption('a')) {
			builder.address(commandLine.getOptionValue('a'));
		}

		// Set the port
		if(commandLine.hasOption('p')) {
			builder.port(Integer.parseInt(commandLine.getOptionValue('p')));
		}

		// Set the ttl
		if(commandLine.hasOption("ttl")) {
			builder.ttl(Integer.parseInt(commandLine.getOptionValue("ttl")));
		}

		// Set the period
		if(commandLine.hasOption("period")) {
			builder.period(Duration.millis(Integer.parseInt(commandLine.getOptionValue("period"))));
		}

		// Set the timeout
		if(commandLine.hasOption("timeout")) {
			builder.timeout(Duration.millis(Integer.parseInt(commandLine.getOptionValue("timeout"))));
		}

		// Build and display the UI
		JFrame frame = new JFrame();
		frame.setTitle(TITLE);
		frame.add(new Lighthouse(builder.build()));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}
}
