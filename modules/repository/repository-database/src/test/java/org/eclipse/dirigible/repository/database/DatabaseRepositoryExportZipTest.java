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
package org.eclipse.dirigible.repository.database;

import static org.junit.Assert.fail;

import javax.sql.DataSource;

import org.eclipse.dirigible.repository.db.DatabaseRepository;
import org.eclipse.dirigible.repository.generic.RepositoryGenericExportZipTest;
import org.junit.Before;

/**
 * The Class DatabaseRepositoryExportZipTest.
 */
public class DatabaseRepositoryExportZipTest extends RepositoryGenericExportZipTest {

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		try {
			DataSource dataSource = DatabaseTestHelper.createDataSource("target/tests/derby");
			repository = new DatabaseRepository(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
