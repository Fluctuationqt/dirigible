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
package org.eclipse.dirigible.engine.odata2.sql;

import static org.apache.olingo.odata2.api.commons.ODataHttpMethod.GET;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.junit.Test;

public class ODataNorthwindMetadataTest extends AbstractODataNorthwindTest {

	@Test
	public void testMetadataResponse() throws Exception {
		Response response = OData2RequestBuilder.createRequest(sf) //
				.segments("$metadata") //
				.executeRequest(GET);
		String content = IOUtils.toString((InputStream) response.getEntity());
		System.err.println(content);
//		assertEquals(loadExpectedMetadata(), content);
	}
}