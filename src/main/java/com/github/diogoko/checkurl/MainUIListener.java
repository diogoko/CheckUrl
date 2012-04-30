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
 * Listener of events of the main user interface of the application.
 *
 * @see MainUI
 */
public interface MainUIListener {
	/**
	 * Dispatched when the user activates the option to check the URL immediately.
	 */
	void checkNow();

	/**
	 * Dispatched when the user activates the option to exit the application.
	 */
	void exit();

}
