package org.dlewandowski.webserver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.dlewandowski.webserver.server.Server;

public class App {

	public static final int DEFAULT_PORT = 65535;

	public static final String DEFAULT_SERVER_ROOT = ".";

	public static final int DEFAULT_THREADS_NUMBER = 10;

	public static void main(String[] args) {
		Options options = getOptions();
		CommandLine commandLine = new DefaultParser().parse(options, args);

		if (commandLine.hasOption('h')) {
			displayUsage(options);
			return;
		}
		commandLine.getOptionValue("p");
		int port = getOptionValue(commandLine, "p", DEFAULT_PORT);
		int threadsPoolSize = getOptionValue(commandLine, "t", DEFAULT_THREADS_NUMBER);
		String serverRoot = getOptionValue(commandLine, "d", DEFAULT_SERVER_ROOT);

		System.out.println(serverRoot);
		Server server = new Server(serverRoot, port, threadsPoolSize);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
		server.start();
	}

	private static Options getOptions() {
		Options options = new Options();
		options.addOption("p", true, "HTTP port number to run the server. Default value: " + DEFAULT_PORT);
		options.addOption("d", true, "directory path which content will be exposed by the server. Default value: " + DEFAULT_SERVER_ROOT);
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
			try {
				result = Integer.parseInt(optionName);
			} catch (NumberFormatException e) {

			}
		}
		return result;
	}

	private static String getOptionValue(CommandLine commandLine, String optionName, String defaultValue) {
		return commandLine.hasOption(optionName) ? commandLine.getOptionValue(optionName) : defaultValue;
	}
}