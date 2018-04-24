/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.fuse.fis.workshop.routes;

import org.apache.camel.builder.RouteBuilder;
import org.jboss.fuse.fis.workshop.beans.MessageServiceBean;
import org.jboss.fuse.fis.workshop.beans.MyTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Lab 03, 04: AMQ Camel Route builder
 */
// @Component
public class MessagingDSLRouteBuilder extends RouteBuilder {
	
	@Autowired
	private MessageServiceBean messageService;

	@Override
	public void configure() throws Exception {
		// Lab 03
		from("timer://amq?period=30s")
			.id("route-amq-put-number")
			.transform(method(MyTransformer.class, "simpleTransform"))
			.log("[AMQ] >>> Putting message ${body} to queue")
			.to("amq:queue:numbers");

		from("amq:queue:numbers")
			.id("route-amq-get-number")
			.log("[AMQ] >>> Getting message ${body} from queue");

		// Lab 04
//		from("amq:queue:messages")
//			.id("route-amq-get-message")
//			.log("[AMQ] >>> Getting message ${body} from queue")
//			.unmarshal("json2pojo")
//			.bean(messageService, "processDone")
//			.marshal("json2pojo")
//			.log("[AMQ] >>> Processed message ${body} from queue");		
	}

}
