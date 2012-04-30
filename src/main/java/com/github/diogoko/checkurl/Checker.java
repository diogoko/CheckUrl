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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Checks the status of a URL.
 */
public class Checker {
	private int timeout = 30;

	/**
	 * Sends a requrest to a URL and returns details of the response.
	 *
	 * @param urlString the URL to be checked
	 * @return response details
	 */
	public Response check(String urlString) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(getTimeout() * 1000);
			connection.connect();

			int code = connection.getResponseCode();
			ResponseKind kind = (code >= 400) ? ResponseKind.Error : ResponseKind.Success;

			return new Response(kind, code, connection.getResponseMessage());
		} catch (MalformedURLException e) {
			return new Response(ResponseKind.Error, String.format("Invalid URL: %s", e.getMessage()));
		} catch (SocketTimeoutException e) {
			return new Response(ResponseKind.Timeout);
		} catch (UnknownHostException e) {
			return new Response(ResponseKind.Error, String.format("Unknown host: %s", e.getMessage()));
		} catch (IOException e) {
			return new Response(ResponseKind.Error, e.getMessage());
		}
	}

	/**
	 * @return the timeout of the checking requests in seconds
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the new timeout of the checking requests
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
