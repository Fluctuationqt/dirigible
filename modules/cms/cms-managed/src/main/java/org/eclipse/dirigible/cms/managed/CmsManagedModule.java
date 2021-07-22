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
package org.eclipse.dirigible.cms.managed;

import static java.text.MessageFormat.format;

import org.eclipse.dirigible.cms.api.ICmsProvider;
import org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Module for managing CMS Providers instantiation and binding.
 */
public class CmsManagedModule extends AbstractDirigibleModule {

	private static final Logger logger = LoggerFactory.getLogger(CmsManagedModule.class);

	private static final String MODULE_NAME = "CMS Managed Module";

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule#configure()
	 */
	@Override
	protected void configure() {
		Configuration.loadModuleConfig("/dirigible-cms-managed.properties");
		String cmsProvider = Configuration.get(ICmsProvider.DIRIGIBLE_CMS_PROVIDER);

		if (CmsProviderManaged.TYPE.equals(cmsProvider)) {
			logger.trace(format("Installing CMS Provider [{0}:{1}] ...", CmsProviderManaged.TYPE, CmsProviderManaged.NAME));
			CmsProviderManaged instance = new CmsProviderManaged();
			StaticObjects.set(StaticObjects.CMS_PROVIDER, instance);
			logger.trace(format("Done installing CMS Provider [{0}:{1}].", CmsProviderManaged.TYPE, CmsProviderManaged.NAME));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule#getName(
	 * )
	 */
	@Override
	public String getName() {
		return MODULE_NAME;
	}

}
