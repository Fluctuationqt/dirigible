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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.repository.api.ICollection;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;

public class Folder extends CmisObject {

	private CmisSession session;

	private ICollection internalFolder;

	private IRepository repository;

	private boolean rootFolder = false;

	public Folder(CmisSession session) throws IOException {
		super(session, IRepository.SEPARATOR);
		this.session = session;
		this.repository = (IRepository) session.getCmisRepository().getInternalObject();
		this.internalFolder = repository.getRoot();
		this.rootFolder = true;
	}

	public Folder(CmisSession session, ICollection internalCollection) throws IOException {
		super(session, internalCollection.getPath());
		if (IRepository.SEPARATOR.equals(internalCollection.getPath())) {
			this.rootFolder = true;
		}
		this.session = session;
		this.repository = (IRepository) session.getCmisRepository().getInternalObject();
		this.internalFolder = internalCollection;
	}

	public Folder(CmisSession session, String id) throws IOException {
		super(session, id);
		id = sanitize(id);
		if (IRepository.SEPARATOR.equals(id)) {
			this.rootFolder = true;
		}
		this.session = session;
		this.repository = (IRepository) session.getCmisRepository().getInternalObject();
		this.internalFolder = this.repository.getCollection(id);
	}

	public ICollection getInternalFolder() {
		return internalFolder;
	}

	@Override
	protected boolean isCollection() {
		return true;
	}

	/**
	 * Returns the Path of this Folder
	 *
	 * @return the path
	 */
	public String getPath() {
		return this.getInternalEntity().getPath();
	}

	/**
	 * Creates a new folder under this Folder
	 *
	 * @param properties
	 *            the properties
	 * @return Folder
	 * @throws IOException
	 *             IO Exception
	 */
	public Folder createFolder(Map<String, String> properties) throws IOException {
		String name = properties.get(CmisConstants.NAME);
		return new Folder(this.session, this.internalFolder.createCollection(name));
	}

	/**
	 * Creates a new document under this Folder
	 *
	 * @param properties
	 *            the properties
	 * @param contentStream
	 *            the content stream
	 * @param versioningState
	 *            the version state
	 * @return Document
	 * @throws IOException
	 *             IO Exception
	 */
	public Document createDocument(Map<String, String> properties, ContentStream contentStream,
			VersioningState versioningState) throws IOException {
		String name = properties.get(CmisConstants.NAME);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtils.copy(contentStream.getStream(), out);
		return new Document(this.session,
				this.internalFolder.createResource(name, out.toByteArray(), true, contentStream.getMimeType()));
	}

	public List<CmisObject> getChildren() throws IOException {
		List<CmisObject> children = new ArrayList<CmisObject>();
		List<ICollection> collections = this.internalFolder.getCollections();
		for (ICollection collection : collections) {
			children.add(new Folder(this.session, collection));
		}
		List<IResource> resources = this.internalFolder.getResources();
		for (IResource resource : resources) {
			children.add(new Document(this.session, resource));
		}
		return children;
	}

	/**
	 * Returns true if this Folder is a root folder and false otherwise
	 *
	 * @return whether it is a root folder
	 */
	public boolean isRootFolder() {
		return rootFolder;
	}

	/**
	 * Returns the parent Folder of this Folder
	 *
	 * @return Folder
	 * @throws IOException
	 *             IO Exception
	 */
	public Folder getFolderParent() throws IOException {
		if (this.internalFolder.getParent() != null) {
			return new Folder(this.session, this.internalFolder.getParent());
		}
		return new Folder(this.session);
	}

}
