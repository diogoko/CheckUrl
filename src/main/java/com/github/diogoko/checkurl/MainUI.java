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

/**
 * Represents the main user interface of the application.
 */
public interface MainUI {
	/**
	 * Starts the user interface, initializing any windows or other features.
	 */
	void start();

	/**
	 * Stops the user interface, closing any windows or other features.
	 */
	void stop();

	/**
	 * @return true if the specified URL was online the last time it was checked
	 */
	boolean isOnline();

	/**
	 * @param online true if the specified URL was online the last time it was
	 *               checked
	 */
	void setOnline(boolean online);

	/**
	 * test
	 *
	 * @return the message describing the last status check
	 */
	String getMessage();

	/**
	 *
	 * @param message the message describing the last status check
	 */
	void setMessage(String message);

	/**
	 * Shows a warning message in a balloon when there was a problem checking
	 * the status of the specified URL (or it was not online).
	 *
	 * @param message the message describing the unsuccessful status check
	 */
	void showWarningBalloon(String message);

	/**
	 * Shows an error message.
	 *
	 * @param message the error message
	 */
	void showErrorDialog(String message);

	/**
	 * @return the listener of events of the main user interface
	 */
	MainUIListener getListener();

	/**
	 *
	 * @param listener the listener of events of the main user interface
	 */
	void setListener(MainUIListener listener);

}
