package org.dlewandowski.webserver;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dlewandowski.webserver.server.Server;

/**
 * Main class of the application. Parses parameters and runs the server.
 */
public class App {

	private static final Logger LOGGER = Logger.getLogger(Server.class);

	private static final int DEFAULT_PORT = 65535;

	private static final String DEFAULT_SERVER_ROOT = ".";

	private static final int DEFAULT_THREADS_NUMBER = 20;

	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure(App.class.getClassLoader().getResourceAsStream("logging.properties"));
			Options options = getOptions();
			CommandLine commandLine = new DefaultParser().parse(options, args);

			if (commandLine.hasOption('h')) {
				displayUsage(options);
				return;
			}

			int port = getOptionValue(commandLine, "p", DEFAULT_PORT);
			int threadsPoolSize = getOptionValue(commandLine, "t", DEFAULT_THREADS_NUMBER);
			String serverRoot = getOptionValue(commandLine, "d", DEFAULT_SERVER_ROOT);

			Server server = new Server(serverRoot, port, threadsPoolSize);
			server.start();
		} catch (ParseException | IOException e) {
			LOGGER.error("Cannot start the server", e);
		}
	}

	private static Options getOptions() {
		Options options = new Options();
		options.addOption("p", true, "HTTP port number to run the server. Default value: " + DEFAULT_PORT);
		options.addOption("d", true, "server root directory. Default value: " + DEFAULT_SERVER_ROOT);
		options.addOption("t", true, "number of threads used by server incoming requests. Default value: " + DEFAULT_THREADS_NUMBER);
		options.addOption("h", false, "help");
		return options;

	}

	private static void displayUsage(Options options) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("Help", options);
	}

	private static int getOptionValue(CommandLine commandLine, String optionName, int defaultValue) {
		int result = defaultValue;
		String optionValue = commandLine.getOptionValue(optionName);
		if (StringUtils.isNotEmpty(optionValue)) {
			result = Integer.parseInt(optionName);
		}
		return result;
	}

	private static String getOptionValue(CommandLine commandLine, String optionName, String defaultValue) {
		String optionValue = commandLine.getOptionValue(optionName);
		return StringUtils.isNotEmpty(optionValue) ? optionValue : defaultValue;
	}
}