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
package org.jboss.fuse.fis.workshop.config;

import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;

/**
 * Lab 05: AMQP
 */
@Configuration
public class AMQPConfig {

	@Value("${amqp.broker.url}")
	String brokerUrl;

	@Value("${amqp.broker.username}")
	String username;

	@Value("${amqp.broker.password}")
	String password;

	@Bean
	@Primary
	public JmsConnectionFactory getConnectionFactory() {
		return new JmsConnectionFactory(username, password, brokerUrl);
	}

	@Bean
	public CachingConnectionFactory getJmsCachingConnectionFactory(JmsConnectionFactory jmsConnectionFactory) {
		return new CachingConnectionFactory(jmsConnectionFactory);
	}

	@Bean
	public JmsConfiguration getJmsConfiguration(CachingConnectionFactory cachingConnectionFactory) {
		JmsConfiguration jmsConfiguration = new JmsConfiguration();

		jmsConfiguration.setConnectionFactory(cachingConnectionFactory);
		jmsConfiguration.setCacheLevelName("CACHE_CONSUMER");

		return jmsConfiguration;
	}

	@Bean
	public AMQPComponent getAmqp(JmsConfiguration jmsConfiguration) {
		AMQPComponent amqpComponent = new AMQPComponent();

		amqpComponent.setConfiguration(jmsConfiguration);

		return amqpComponent;
	}

}