// tag::comment[]
/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
 // end::comment[]
package io.openliberty.guides.consumingrest.model;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
// tag::Artist[]
public class Artist {
    // tag::name[]
    public String name;
    // end::name[]
    // tag::album[]
    public Album albums[];
    // end::album[]

    // Object property that does not map to a JSON
    // tag::JsonbTransient[]
    @JsonbTransient
    // end::JsonbTransient[]
    public boolean legendary = true;

    public Artist() {

    }
    // tag::JsonbCreator[]
    @JsonbCreator
    // end::JsonbCreator[]
    public Artist(
      // tag::JsonbProperty[]
      @JsonbProperty("name") String name,
      @JsonbProperty("albums") Album albums[]) {
      // end::JsonbProperty[]

      this.name = name;
      this.albums = albums;
    }

    @Override
    public String toString() {
      return name + " wrote " + albums.length + " albums";
    }
}
// end::Artist[]