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
package org.eclipse.dirigible.database.sql.test.hana;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class DropViewTest.
 */
public class DropViewTest {

	/**
	 * Drop view.
	 */
	@Test
	public void dropView() {
		String sql = SqlFactory.getNative(new HanaSqlDialect()).drop().view("CUSTOMERS_VIEW").build();

		assertNotNull(sql);
		assertEquals("DROP VIEW CUSTOMERS_VIEW", sql);
	}

}
