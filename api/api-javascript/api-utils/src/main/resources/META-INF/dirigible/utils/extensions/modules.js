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

var reqPlatform = require('platform/v4/registry')

exports.getContent = function () {
    var rawFile = reqPlatform.getContent("utils/extensions/modules.json");
    var file = JSON.stringify(rawFile);
    var module = JSON.parse(file);
    console.log("MODULE       "+module);
    for (var i = 0; i < module.length; i++) {
        if(module[i].isPackageDescription === true){
            console.log("MODULE MODULE MODULE " + module[i].name);
        }
    }
    return module;

        // [{
    //     "name": "@dirigible/utils",
    //     "description": "Dirigible Utils module",
    //     "isPackageDescription": true
    // }, {
    //     "require_suggestion": "utils/v4/alphanumeric",
    //     "description": "Alphanumeric API",
    //     "api": "alphanumeric",
    //     "versionedPaths": ["utils/v3/alphanumeric", "utils/v4/alphanumeric"],
    //     "pathDefault": "utils/v4/alphanumeric"
    // }, {
    //     "require_suggestion": "utils/v4/base64",
    //     "description": "Base64 API",
    //     "api": "base64",
    //     "versionedPaths": ["utils/v3/base64", "utils/v4/base64"],
    //     "pathDefault": "utils/v4/base64"
    // }, {
    //     "require_suggestion": "utils/v4/digest",
    //     "description": "Digest API",
    //     "api": "digest",
    //     "versionedPaths": ["utils/v3/digest", "utils/v4/digest"],
    //     "pathDefault": "utils/v4/digest"
    // }, {
    //     "require_suggestion": "utils/v4/escape",
    //     "description": "Escape API",
    //     "api": "escape",
    //     "versionedPaths": ["utils/v3/escape", "utils/v4/escape"],
    //     "pathDefault": "utils/v4/escape"
    // }, {
    //     "require_suggestion": "utils/v4/hex",
    //     "description": "Hex API",
    //     "api": "hex",
    //     "versionedPaths": ["utils/v3/hex", "utils/v4/hex"],
    //     "pathDefault": "utils/v4/hex"
    // }, {
    //     "require_suggestion": "utils/v4/jsonpath",
    //     "description": "JsonPath API",
    //     "api": "jsonpath",
    //     "versionedPaths": ["utils/v3/jsonpath", "utils/v4/jsonpath"],
    //     "pathDefault": "utils/v4/jsonpath"
    // }, {
    //     "require_suggestion": "utils/v4/url",
    //     "description": "URL API",
    //     "api": "url",
    //     "versionedPaths": ["utils/v3/url", "utils/v4/url"],
    //     "pathDefault": "utils/v4/url"
    // }, {
    //     "require_suggestion": "utils/v4/uuid",
    //     "description": "UUID API",
    //     "api": "uuid",
    //     "versionedPaths": ["utils/v3/uuid", "utils/v4/uuid"],
    //     "pathDefault": "utils/v4/uuid"
    // }, {
    //     "require_suggestion": "utils/v4/xml",
    //     "description": "XML API",
    //     "api": "xml",
    //     "versionedPaths": ["utils/v3/xml", "utils/v4/xml"],
    //     "pathDefault": "utils/v4/xml"
    // }]
};
// return [{
//           "name": "@dirigible/utils",
//           "description": "Dirigible Utils module",
//           "isPackageDescription": true
//         },
// {
// 	name: "utils/v4/alphanumeric",
// 	description: "Alphanumeric API"
// }, {
// 	name: "utils/v4/base64",
// 	description: "Base64 API"
// }, {
// 	name: "utils/v4/digest",
// 	description: "Digest API"
// }, {
// 	name: "utils/v4/escape",
// 	description: "Escape API"
// }, {
// 	name: "utils/v4/hex",
// 	description: "Hex API"
// }, {
// 	name: "utils/v4/jsonpath",
// 	description: "JsonPath API"
// }, {
// 	name: "utils/v4/url",
// 	description: "URL API"
// }, {
// 	name: "utils/v4/uuid",
// 	description: "UUID API"
// }, {
// 	name: "utils/v4/xml",
// 	description: "XML API"
// }];


