// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.consumingrest.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonArray;

public class Reader {

    public static JsonArray getArtists() {
        final String jsonFile = "./../../../../../../src/resources/artists.json";
        try {
            InputStream fis;
            fis = new FileInputStream(jsonFile);
            return Json.createReader(fis).readArray();
        } catch (FileNotFoundException e) {
            System.err.println("File does not exist: " + jsonFile);
            return null;
        }
    }

}
