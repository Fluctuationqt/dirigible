/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
const SpringAppContext = __context.springAppContext;
const camelMessage = __context.camelMessage;
const Invoker = Java.type('org.eclipse.dirigible.components.engine.camel.invoke.Invoker');
const invoker = SpringAppContext.getBean(Invoker.class);

exports.toRoute = function (routeId, payload, headers) {
    return invoker.invokeRoute(routeId, payload, headers);
};

exports.fromRoute = function () {
    return camelMessage;
};