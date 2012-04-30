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
 * The response to a request sent to a URL.
 */
public class Response {
	private ResponseKind kind;

	private int code;

	private String message;

	/**
	 * Creates a new reponse.
	 *
	 * @param kind the kind of response received
	 */
	public Response(ResponseKind kind) {
		this.kind = kind;
	}

	/**
	 * Creates a new reponse.
	 *
	 * @param kind the kind of response received
	 * @param code the HTTP response code
	 */
	public Response(ResponseKind kind, int code) {
		this.kind = kind;
		this.code = code;
	}

	/**
	 * Creates a new reponse.
	 *
	 * @param kind the kind of response received
	 * @param code the HTTP response code
	 * @param message the HTTP response message or an error message
	 */
	public Response(ResponseKind kind, int code, String message) {
		this.kind = kind;
		this.code = code;
		this.message = message;
	}

	/**
	 * Creates a new reponse.
	 *
	 * @param kind the kind of response received
	 * @param message the HTTP response message or an error message
	 */
	public Response(ResponseKind kind, String message) {
		this.kind = kind;
		this.message = message;
	}

	/**
	 * @return the kind of response received
	 */
	public ResponseKind getKind() {
		return kind;
	}

	/**
	 * @param kind the kind of response received
	 */
	public void setKind(ResponseKind kind) {
		this.kind = kind;
	}

	/**
	 * @return the HTTP response code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the HTTP response code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the HTTP response message or an error message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the HTTP response message or an error message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
