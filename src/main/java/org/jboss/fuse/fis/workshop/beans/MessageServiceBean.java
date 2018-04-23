package org.jboss.fuse.fis.workshop.beans;

import org.jboss.fuse.fis.workshop.model.Message;
import org.springframework.stereotype.Component;

/**
 * Lab 04: Process Domain Model
 */
@Component(value = "messageService")
public class MessageServiceBean {

	private static final String STATUS_PENDING = "PENDING";
	private static final String STATUS_DONE = "DONE";
	private static final String STATUS_ERROR = "ERROR";

	public Message sync(Message msg) {
		msg.setMethod("SYNC");
		msg.setStatus(STATUS_PENDING);

		return msg;
	}

	public Message async(Message msg) {
		msg.setMethod("ASYNC");
		msg.setStatus(STATUS_PENDING);

		return msg;
	}

	public Message processDone(Message msg) {
		msg.setStatus(STATUS_DONE);

		return msg;
	}

	public Message processError(Message msg) {
		msg.setStatus(STATUS_ERROR);

		return msg;
	}

}
