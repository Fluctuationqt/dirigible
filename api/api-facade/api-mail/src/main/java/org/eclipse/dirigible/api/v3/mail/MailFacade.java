/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2021 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.dirigible.api.v3.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.eclipse.dirigible.commons.api.scripting.IScriptingFacade;
import org.eclipse.dirigible.commons.config.Configuration;

/**
 * The Class MailFacade.
 */
public class MailFacade implements IScriptingFacade {

	// Mail properties
	private static final String MAIL_USER = "mail.user";
	private static final String MAIL_PASSWORD = "mail.password";

	private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

	// SMTPS properties
	private static final String MAIL_SMTPS_HOST = "mail.smtps.host";
	private static final String MAIL_SMTPS_PORT = "mail.smtps.port";
	private static final String MAIL_SMTPS_AUTH = "mail.smtps.auth";

	// SMTP properties
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

	// Dirigible mail properties
	private static final String DIRIGIBLE_MAIL_USERNAME = "DIRIGIBLE_MAIL_USERNAME";
	private static final String DIRIGIBLE_MAIL_PASSWORD = "DIRIGIBLE_MAIL_PASSWORD";

	private static final String DIRIGIBLE_MAIL_TRANSPORT_PROTOCOL = "DIRIGIBLE_MAIL_TRANSPORT_PROTOCOL";

	// SMTP properties
	private static final String DIRIGIBLE_MAIL_SMTPS_HOST = "DIRIGIBLE_MAIL_SMTPS_HOST";
	private static final String DIRIGIBLE_MAIL_SMTPS_PORT = "DIRIGIBLE_MAIL_SMTPS_PORT";
	private static final String DIRIGIBLE_MAIL_SMTPS_AUTH = "DIRIGIBLE_MAIL_SMTPS_AUTH";

	// SMTP properties
	private static final String DIRIGIBLE_MAIL_SMTP_HOST = "DIRIGIBLE_MAIL_SMTP_HOST";
	private static final String DIRIGIBLE_MAIL_SMTP_PORT = "DIRIGIBLE_MAIL_SMTP_PORT";
	private static final String DIRIGIBLE_MAIL_SMTP_AUTH = "DIRIGIBLE_MAIL_SMTP_AUTH";

	// Default values
	private static final String DEFAULT_SUBTYPE = "plain";
	private static final String DEFAULT_MAIL_TRANSPORT_PROTOCOL = "smtps";

	/**
	 * Get MailClient with the default configuration options
	 * 
	 * @return MailClient instance
	 */
	public static MailClient getInstance() {
		return getInstance(getDefaultProperties());
	}

	/**
	 * Get MailClient with custom configuration options
	 * 
	 * @param properties mail client configuration options
	 * @return MailClient instance
	 */
	public static MailClient getInstance(Properties properties) {
		return new MailClient(properties); 
	}

	private static Properties getDefaultProperties() {
		Properties properties = new Properties();

		addValue(properties, MAIL_USER, DIRIGIBLE_MAIL_USERNAME);
		addValue(properties, MAIL_PASSWORD, DIRIGIBLE_MAIL_PASSWORD);
		
		addValue(properties, MAIL_TRANSPORT_PROTOCOL, DIRIGIBLE_MAIL_TRANSPORT_PROTOCOL, DEFAULT_MAIL_TRANSPORT_PROTOCOL);

		addValue(properties, MAIL_SMTPS_HOST, DIRIGIBLE_MAIL_SMTPS_HOST);
		addValue(properties, MAIL_SMTPS_PORT, DIRIGIBLE_MAIL_SMTPS_PORT);
		addValue(properties, MAIL_SMTPS_AUTH, DIRIGIBLE_MAIL_SMTPS_AUTH);
		
		addValue(properties, MAIL_SMTP_HOST, DIRIGIBLE_MAIL_SMTP_HOST);
		addValue(properties, MAIL_SMTP_PORT, DIRIGIBLE_MAIL_SMTP_PORT);
		addValue(properties, MAIL_SMTP_AUTH, DIRIGIBLE_MAIL_SMTP_AUTH);

		return properties;
	}

	private static void addValue(Properties properties, String key, String evnKey) {
		addValue(properties, key, evnKey, null);
	}

	private static void addValue(Properties properties, String key, String evnKey, String defaultValue) {
		String value = Configuration.get(evnKey);
		if (value != null) {
			properties.put(key, value);
		} else if (defaultValue != null) {
			properties.put(key, defaultValue);
		}
	}

	public static class MailClient {
		private Properties properties;

		/**
		 * @param properties mail client configuration options
		 */
		public MailClient(Properties properties) {
			this.properties = properties;
		}

		/**
		 * Send an email
		 *
		 * @param from the sender
		 * @param to the to receiver
		 * @param cc the cc receiver
		 * @param bcc the bcc receiver
		 * @param subject the subject
		 * @param content the content
		 * @param subType the subType
		 * @throws MessagingException 
		 */
		public void send(String from, String[] to, String[] cc, String[] bcc, String subject, String content, String subType) throws MessagingException {
			Session session = getSession(this.properties);
			Transport transport = session.getTransport();
			transport.connect();
			
			MimeMessage mimeMessage = createMimeMessage(session, from, to, cc, bcc, subject, content, subType);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
		}

		private Session getSession(Properties properties) {
			String user = properties.getProperty(MAIL_USER);
			String password = properties.getProperty(MAIL_PASSWORD);
			Authenticator authenticator = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			};
			return Session.getInstance(properties, authenticator);
		}
	}

	private static MimeMessage createMimeMessage(Session smtpSession, String from, String to[], String cc[], String bcc[], String subjectText, String mailText, String subType)
			throws MessagingException {

		MimeMessage mimeMessage = new MimeMessage(smtpSession);
		mimeMessage.setFrom(InternetAddress.parse(from)[0]);
		for(String next : to) {
			mimeMessage.addRecipients(RecipientType.TO, InternetAddress.parse(next));
		}
		for(String next : cc) {
			mimeMessage.addRecipients(RecipientType.CC, InternetAddress.parse(next));
		}
		for(String next : bcc) {
			mimeMessage.addRecipients(RecipientType.BCC, InternetAddress.parse(next));
		}
		mimeMessage.setSubject(subjectText, "UTF-8"); //$NON-NLS-1$

		MimeMultipart multiPart = new MimeMultipart("alternative"); //$NON-NLS-1$
		MimeBodyPart part = new MimeBodyPart();
		part.setText(mailText, "utf-8", getSubType(subType)); //$NON-NLS-1$
		multiPart.addBodyPart(part);
		mimeMessage.setContent(multiPart);

		return mimeMessage;
	}

	private static String getSubType(String subType) {
		return subType != null ? subType : DEFAULT_SUBTYPE;
	}
}
