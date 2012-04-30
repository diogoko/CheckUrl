/*
 * Copyright 2012 Diogo Kollross
 *
 * This file is part of CheckUrl.
 *
 * CheckUrl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CheckUrl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CheckUrl.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.diogoko.checkurl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The main class of the application.
 */
public class Main implements MainUIListener {
	/**
	 * Number of minutes to wait before the first automatic check.
	 */
	private static final int SECONDS_UNTIL_FIRST_CHECK = 2;

	/**
	 * Command line usage of this program.
	 */
	private static final String USAGE = "Usage: CheckUrl.jar [-interval MINUTES_BETWEEN_CHECK] URL\n" +
			"Checks if a URL is online.";

	/**
	 * The URL checker.
	 */
	private Checker checker;

	/**
	 * The main user interface of the application.
	 */
	private MainUI mainUI;

	/**
	 * The URL to be checked.
	 */
	private String url;

	/**
	 * The interval between checks in minutes.
	 *
	 * <p>If zero, no check is started automatically.</p>
	 */
	private int interval;

	/**
	 * The timer used to check the URL automatically.
	 */
	Timer timer;

	/**
	 * Combines usage and error message in one string.
	 *
	 * @param message the error message
	 * @return the program usage combined with the error message
	 */
	private static String buildErrorAndUsage(String message) {
		return String.format("%s\n\n%s", message, USAGE);
	}

	/**
	 * Parses command line arguments and starts application or exits with error
	 * message.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Main main = new Main();

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.startsWith("-")) {
				if (arg.equals("-interval")) {
					i++;
					if (i < args.length) {
						try {
							int interval = Integer.parseInt(args[i]);
							if (interval <= 0) {
								main.showErrorDialog(buildErrorAndUsage("The interval must be an integer greater than zero"));
								return;
							}

							main.setInterval(interval);
						} catch (NumberFormatException e) {
							main.showErrorDialog(buildErrorAndUsage("The interval must be an integer greater than zero"));
							return;
						}
					} else {
						main.showErrorDialog(buildErrorAndUsage("An interval was expected after the -interval argument"));
						return;
					}
				} else {
					main.showErrorDialog(buildErrorAndUsage(String.format("Invalid argument: %s", arg)));
					return;
				}
			} else {
				main.setUrl(arg);
			}
		}

		if (main.getUrl() == null || main.getUrl().isEmpty()) {
			main.showErrorDialog(buildErrorAndUsage("No URL was defined to be checked"));
			return;
		}

		main.run();
	}

	/**
	 * Initializes the application.
	 */
	public Main() {
		checker = new Checker();

		mainUI = new TrayIconUI();
		mainUI.setListener(this);
	}

	/**
	 * Starts the application.
	 */
	public void run() {
		mainUI.start();

		if (interval > 0) {
			timer = new Timer(true);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					checkNow();
				}

			}, SECONDS_UNTIL_FIRST_CHECK * 1000L, interval * 60L * 1000L);
		}
	}

	/**
	 * Shows an error message.
	 *
	 * @param message the message to be shown
	 */
	public void showErrorDialog(String message) {
		mainUI.showErrorDialog(message);
	}

	/**
	 * Checks the specified URL immediately.
	 */
	@Override
	public void checkNow() {
		Response response = checker.check(url);

		boolean online;
		String message;
		switch (response.getKind()) {
			case Success:
				online = true;
				message = "Success";
				break;

			case Error:
				online = false;
				message = response.getMessage();
				break;

			case Timeout:
				online = false;
				message = "Timeout";
				break;

			default:
				throw new RuntimeException(String.format("Invalid response kind: %s", response.getKind()));
		}

		mainUI.setOnline(online);
		mainUI.setMessage(String.format("%s\n%s\nChecked at %s", message, url, getCurrentTime()));
		if (!online) {
			mainUI.showWarningBalloon(message);
		}
	}

	/**
	 * Gets the current date and time formatted using the default locale.
	 *
	 * @return the current date and time
	 */
	private String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat();
		return format.format(new Date());
	}

	/**
	 * Exits the application.
	 */
	@Override
	public void exit() {
		mainUI.stop();

		if (timer != null) {
			timer.cancel();
		}
	}

	/**
	 * @return the url to be checked
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to be checked
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the interval between checks in minutes
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval between checks in minutes
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

}
