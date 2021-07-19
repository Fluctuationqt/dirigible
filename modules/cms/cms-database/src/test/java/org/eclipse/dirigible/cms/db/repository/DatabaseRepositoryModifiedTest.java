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
package org.eclipse.dirigible.cms.db.repository;

import static org.junit.Assert.fail;

import javax.sql.DataSource;

import org.eclipse.dirigible.cms.db.CmsDatabaseRepository;
import org.eclipse.dirigible.repository.generic.RepositoryGenericModifiedTest;
import org.junit.Before;

/**
 * The Class DatabaseRepositoryModifiedTest.
 */
public class DatabaseRepositoryModifiedTest extends RepositoryGenericModifiedTest {

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		try {
			DataSource dataSource = DatabaseTestHelper.createDataSource("target/tests/derby");
			repository1 = new CmsDatabaseRepository(dataSource);
			repository2 = new CmsDatabaseRepository(dataSource);
			repository3 = new CmsDatabaseRepository(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
