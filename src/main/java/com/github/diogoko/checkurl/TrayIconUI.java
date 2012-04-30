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

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 * Implementation of the main user interface as a tray icon.
 */
public class TrayIconUI implements MainUI {
	/**
	 * The icon shown when the URL is online.
	 */
	private Image onlineIcon;

	/**
	 * The icon shown when the URL is offline.
	 */
	private Image offlineIcon;

	/**
	 * The trayicon used to show the status of the URL.
	 */
	private TrayIcon icon;

	private MainUIListener listener;

	private boolean online;

	private String message;

	@Override
	public void start() {
		URL onlineIconPath = getClass().getClassLoader().getResource("com/github/diogoko/checkurl/images/online.png");
		onlineIcon = Toolkit.getDefaultToolkit().createImage(onlineIconPath);

		URL offlineIconPath = getClass().getClassLoader().getResource("com/github/diogoko/checkurl/images/offline.png");
		offlineIcon = Toolkit.getDefaultToolkit().createImage(offlineIconPath);

		MenuItem checkNowItem = new MenuItem("Check now");
		checkNowItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (listener != null) {
					listener.checkNow();
				}
			}

		});

		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (listener != null) {
					listener.exit();
				}
			}

		});

		PopupMenu menu = new PopupMenu();
		menu.add(checkNowItem);
		menu.add(new MenuItem("-"));
		menu.add(exitItem);

		icon = new TrayIcon(onlineIcon, "CheckUrl", menu);
		icon.setImageAutoSize(true);

		SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(icon);
		} catch (AWTException e) {
			icon = null;

			throw new RuntimeException("Could not setup tray icon");
		}
	}

	@Override
	public void stop() {
		if (icon != null) {
			SystemTray tray = SystemTray.getSystemTray();
			tray.remove(icon);
		}
	}

	@Override
	public boolean isOnline() {
		return online;
	}

	@Override
	public void setOnline(boolean online) {
		this.online = online;
		icon.setImage(online ? onlineIcon : offlineIcon);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
		icon.setToolTip(message);
	}

	@Override
	public void showWarningBalloon(String message) {
		icon.displayMessage("Warning", message, TrayIcon.MessageType.WARNING);
	}

	@Override
	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "CheckUrl", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public MainUIListener getListener() {
		return listener;
	}

	@Override
	public void setListener(MainUIListener listener) {
		this.listener = listener;
	}

}
