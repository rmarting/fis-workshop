package org.jboss.fuse.fis.workshop.model;

import java.io.Serializable;

/**
 * Lab 04: Domain Model
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -7222892147054032087L;

	private String content;

	private String status;

	private String method;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
