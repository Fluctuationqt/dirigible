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
const rs = require("http/v4/rs");
const registry = require("platform/v4/registry");
const extensions = require("core/v4/extensions");

// Finds all api's and calls getContent() for each of them, thus obtaining
// the json data for each api, from that json data if there exists an isPackageDescription=true
// item it gets it's dtsPath value and stores it in an array of strings which is returned.


exports.getDtsPaths = function () {
    let dtsPaths = []
    let apiModulesExtensions = extensions.getExtensions("api-modules");
    apiModulesExtensions.forEach(function (apiModule) {
        let module = require(apiModule);
        let content = module.getContent();

        for (let [property, value] of Object.entries(content)) {
            let isPackageDescription = value["isPackageDescription"];
            if(typeof isPackageDescription === 'boolean' && isPackageDescription === true)
            {
                dtsPaths.push(value["dtsPath"])
            }
        }
    });


       let content =  getDtsFileContents(dtsPaths);

    return content;
}

// From the input array of path strings to dts files
// this method reads them and returns their contents concatinated
function getDtsFileContents(paths){
    let result = ""
    for(const path of paths) {
        result = result.concat(registry.getText(path) + "\n")
    }
    return result
}


// const dtsContents = getDtsFileContents(dtsPaths)

// RS service which returns the concatinated dts file contents
// rs.service()
//     .resource("")
//     .get(function(ctx, request, response){
//         response.println(dtsContents);
//     })
//     .execute();