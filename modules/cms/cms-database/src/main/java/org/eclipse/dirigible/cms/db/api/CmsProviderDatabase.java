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
package org.eclipse.dirigible.cms.db.api;

import java.util.ServiceLoader;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dirigible.cms.api.ICmsProvider;
import org.eclipse.dirigible.cms.db.CmsDatabaseRepository;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.api.IDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmsProviderDatabase implements ICmsProvider {

	private static final Logger logger = LoggerFactory.getLogger(CmsProviderDatabase.class);

	/** The Constant NAME. */
	public static final String NAME = "repository"; //$NON-NLS-1$

	/** The Constant TYPE. */
	public static final String TYPE = "database"; //$NON-NLS-1$

	private CmsDatabaseRepository cmsDatabaseRepository;
	
	private CmisRepository cmisRepository;

	public CmsProviderDatabase() {
		Configuration.loadModuleConfig("/dirigible-cms-database.properties");
		
		String repositoryProvider = Configuration.get(ICmsProvider.DIRIGIBLE_CMS_PROVIDER, ICmsProvider.DIRIGIBLE_CMS_PROVIDER_DATABASE);
		String dataSourceType = Configuration.get(DIRIGIBLE_CMS_DATABASE_DATASOURCE_TYPE, IDatabase.DIRIGIBLE_DATABASE_PROVIDER_MANAGED);
		String dataSourceName = Configuration.get(DIRIGIBLE_CMS_DATABASE_DATASOURCE_NAME, IDatabase.DIRIGIBLE_DATABASE_DATASOURCE_DEFAULT);
		
		if (CmsDatabaseRepository.TYPE.equals(repositoryProvider)) {
			cmsDatabaseRepository = createInstance(dataSourceType, dataSourceName);
			logger.info("Bound CMS Database Repository as the CMS Repository for this instance.");
		}

		if (cmsDatabaseRepository == null) {
			throw new IllegalArgumentException("CMS Database Repository initialization failed.");
		}
		
		this.cmisRepository = CmisRepositoryFactory.createCmisRepository(cmsDatabaseRepository);
	}
	
	/**
	 * Creates the instance.
	 * @param dataSourceName 
	 * @param dataSourceType2 
	 *
	 * @return the repository
	 */
	private CmsDatabaseRepository createInstance(String dataSourceType, String dataSourceName) {
		logger.info("Creating CMS Database Repository...");
		logger.info("Data source name [{}]", dataSourceName);
		CmsDatabaseRepository databaseRepository = null;
		ServiceLoader<IDatabase> DATABASES = ServiceLoader.load(IDatabase.class);
		DataSource dataSource = null;
		for (IDatabase next : DATABASES) {
			if (dataSourceType.equals(next.getType())) {
				if (!StringUtils.isEmpty(dataSourceName)) {
					dataSource = next.getDataSource(dataSourceName);
					databaseRepository = new CmsDatabaseRepository(dataSource);
				} else {
					dataSource = next.getDataSource();
					databaseRepository = new CmsDatabaseRepository(dataSource);
				}
				break;
			}
		}
		logger.info("CMS Database Repository created.");
		return databaseRepository;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public Object getSession() {
		CmisSession cmisSession = this.cmisRepository.getSession();
		return cmisSession;
	}

}
