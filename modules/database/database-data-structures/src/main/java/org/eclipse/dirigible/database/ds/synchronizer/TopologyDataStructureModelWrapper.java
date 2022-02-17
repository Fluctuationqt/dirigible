/*
 * Copyright (c) 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.dirigible.database.ds.synchronizer;

import static java.text.MessageFormat.format;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.dirigible.commons.api.artefacts.IArtefactDefinition;
import org.eclipse.dirigible.commons.api.topology.ITopologicallyDepletable;
import org.eclipse.dirigible.commons.api.topology.ITopologicallySortable;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizationArtefactType;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.ds.artefacts.AppendSynchronizationArtefactType;
import org.eclipse.dirigible.database.ds.artefacts.DeleteSynchronizationArtefactType;
import org.eclipse.dirigible.database.ds.artefacts.ReplaceSynchronizationArtefactType;
import org.eclipse.dirigible.database.ds.artefacts.SchemaSynchronizationArtefactType;
import org.eclipse.dirigible.database.ds.artefacts.TableSynchronizationArtefactType;
import org.eclipse.dirigible.database.ds.artefacts.UpdateSynchronizationArtefactType;
import org.eclipse.dirigible.database.ds.artefacts.ViewSynchronizationArtefactType;
import org.eclipse.dirigible.database.ds.model.DataStructureDependencyModel;
import org.eclipse.dirigible.database.ds.model.DataStructureModel;
import org.eclipse.dirigible.database.ds.model.DataStructureTableModel;
import org.eclipse.dirigible.database.ds.model.DataStructureViewModel;
import org.eclipse.dirigible.database.ds.model.processors.TableAlterProcessor;
import org.eclipse.dirigible.database.ds.model.processors.TableCreateProcessor;
import org.eclipse.dirigible.database.ds.model.processors.TableDropProcessor;
import org.eclipse.dirigible.database.ds.model.processors.TableForeignKeysCreateProcessor;
import org.eclipse.dirigible.database.ds.model.processors.TableForeignKeysDropProcessor;
import org.eclipse.dirigible.database.ds.model.processors.ViewCreateProcessor;
import org.eclipse.dirigible.database.ds.model.processors.ViewDropProcessor;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopologyDataStructureModelWrapper implements ITopologicallySortable, ITopologicallyDepletable {
	
	private static final Logger logger = LoggerFactory.getLogger(TopologyDataStructureModelWrapper.class);
	
	private static final TableSynchronizationArtefactType TABLE_ARTEFACT = new TableSynchronizationArtefactType();
	
	private DataStructuresSynchronizer synchronizer;
	
	private Connection connection;
	
	private DataStructureModel model;
	
	private Map<String, TopologyDataStructureModelWrapper> wrappers;

	public TopologyDataStructureModelWrapper(DataStructuresSynchronizer synchronizer, Connection connection, DataStructureModel model, Map<String, TopologyDataStructureModelWrapper> wrappers) {
		this.synchronizer = synchronizer;
		this.connection = connection;
		this.model = model;
		this.wrappers = wrappers;
		this.wrappers.put(getId(), this);
	}
	
	public DataStructureModel getModel() {
		return model;
	}
	
	public DataStructuresSynchronizer getSynchronizer() {
		return synchronizer;
	}

	@Override
	public String getId() {
		return this.model.getName();
	}

	@Override
	public List<ITopologicallySortable> getDependencies() {
		List<ITopologicallySortable> dependencies = new ArrayList<ITopologicallySortable>();
		for (DataStructureDependencyModel dependency: this.model.getDependencies()) {
			String dependencyName = dependency.getName();
			if (!wrappers.containsKey(dependencyName)) {
				logger.warn("Dependency is not present in this cycle: " + dependencyName);
			} else {
				dependencies.add(wrappers.get(dependencyName));
			}
		}
		return dependencies;
	}
	
	@Override
	public boolean complete(String flow) {
		try {
			TopologyDataStructureModelEnum flag = TopologyDataStructureModelEnum.valueOf(flow);
			switch (flag) {
			case EXECUTE_TABLE_UPDATE:
				if (model instanceof DataStructureTableModel) {
					executeTableUpdate(connection, (DataStructureTableModel) this.model);
				}
				break;
			case EXECUTE_TABLE_CREATE:
				if (model instanceof DataStructureTableModel) {
					if (!SqlFactory.getNative(connection).exists(connection, this.model.getName())) {
						executeTableCreate(connection, (DataStructureTableModel) model);
						applyArtefactState(this.model, TABLE_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE);
					} else {
						logger.warn(format("Table [{0}] already exists during the update process", this.model.getName()));
						if (SqlFactory.getNative(connection).count(connection, model.getName()) != 0) {
							executeTableAlter(connection, (DataStructureTableModel) model);
							applyArtefactState(this.model, TABLE_ARTEFACT, ArtefactState.SUCCESSFUL_UPDATE);
						}
					}
				}
				break;
			case EXECUTE_TABLE_FOREIGN_KEYS_CREATE:
				if (model instanceof DataStructureTableModel) {
					executeTableForeignKeysCreate(connection, (DataStructureTableModel) this.model);
				}
				break;
			case EXECUTE_TABLE_ALTER:
				if (model instanceof DataStructureTableModel) {
					executeTableAlter(connection, (DataStructureTableModel) this.model);
				}
				break;
			case EXECUTE_TABLE_DROP:
				if (model instanceof DataStructureTableModel) {
					if (SqlFactory.getNative(connection).exists(connection, this.model.getName())) {
						if (SqlFactory.getNative(connection).count(connection, this.model.getName()) == 0) {
							executeTableDrop(connection, (DataStructureTableModel) this.model);
						} else {
							String message = format("Table [{1}] cannot be deleted during the update process, because it is not empty", this.model.getName());
							logger.warn(message);
							applyArtefactState(this.model, TABLE_ARTEFACT, ArtefactState.FAILED, message);
						}
					}
				}
				break;
			case EXECUTE_TABLE_FOREIGN_KEYS_DROP:
				if (model instanceof DataStructureTableModel) {
					if (SqlFactory.getNative(connection).exists(connection, this.model.getName())) {
						executeTableForeignKeysDrop(connection, (DataStructureTableModel) this.model);
					}
				}
				break;
			case EXECUTE_VIEW_CREATE:
				if (model instanceof DataStructureViewModel) {
					executeViewCreate(connection, (DataStructureViewModel) this.model);
				}
				break;
			case EXECUTE_VIEW_DROP:
				if (model instanceof DataStructureViewModel) {
					executeViewDrop(connection, (DataStructureViewModel) this.model);
				}
				break;
				
			default:
				throw new UnsupportedOperationException(flow);
			}
			return true;
		} catch (SQLException e) {
			logger.warn("Failed on trying to complete the artefact: " + e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * Execute table update.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableUpdate(Connection connection, DataStructureTableModel tableModel) throws SQLException {
		this.synchronizer.executeTableUpdate(connection, tableModel);
	}

	/**
	 * Execute table create.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void executeTableCreate(Connection connection, DataStructureTableModel tableModel) throws SQLException {
		this.synchronizer.executeTableCreate(connection, tableModel);
	}
	
	/**
	 * Execute table foreign keys create.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableForeignKeysCreate(Connection connection, DataStructureTableModel tableModel) throws SQLException {
		this.synchronizer.executeTableForeignKeysCreate(connection, tableModel);
	}

	/**
	 * Execute table alter.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException 
	 */
	private void executeTableAlter(Connection connection, DataStructureTableModel tableModel) throws SQLException {
		this.synchronizer.executeTableAlter(connection, tableModel);
	}

	/**
	 * Execute table drop.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableDrop(Connection connection, DataStructureTableModel tableModel) throws SQLException {
		this.synchronizer.executeTableDrop(connection, tableModel);
	}
	
	/**
	 * Execute table foreign keys drop.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void executeTableForeignKeysDrop(Connection connection, DataStructureTableModel tableModel) throws SQLException {
		this.synchronizer.executeTableForeignKeysDrop(connection, tableModel);
	}

	/**
	 * Execute view create.
	 *
	 * @param connection
	 *            the connection
	 * @param viewModel
	 *            the view model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeViewCreate(Connection connection, DataStructureViewModel viewModel) throws SQLException {
		this.synchronizer.executeViewCreate(connection, viewModel);
	}

	/**
	 * Execute view drop.
	 *
	 * @param connection
	 *            the connection
	 * @param viewModel
	 *            the view model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeViewDrop(Connection connection, DataStructureViewModel viewModel) throws SQLException {
		this.synchronizer.executeViewDrop(connection, viewModel);
	}
	
	/**
	 * Apply the state
	 * 
	 * @param artefact the artefact
	 * @param type the type
	 * @param state the state
	 */
	public void applyArtefactState(IArtefactDefinition artefact, ISynchronizerArtefactType type, ISynchronizerArtefactType.ArtefactState state) {
		applyArtefactState(artefact, type, state, null);
	}

	/**
	 * Apply the state
	 * 
	 * @param artefact the artefact
	 * @param type the type
	 * @param state the state
	 * @param message the message
	 */
	public void applyArtefactState(IArtefactDefinition artefact, ISynchronizerArtefactType type, ISynchronizerArtefactType.ArtefactState state, String message) {
		this.synchronizer.applyArtefactState(artefact, type, state);
	}

}
