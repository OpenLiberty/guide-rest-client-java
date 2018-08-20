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

public class Album {
    public String title;

    @JsonbProperty("artist")
    public String artistName;

    @JsonbProperty("ntracks")
    public int totalTracks;

    //default constructor can be defined
    public Album() {
    }

    @JsonbCreator
    //or custom constructor can be used
    public Album(
      @JsonbProperty("title") String title,
      @JsonbProperty("artist") String artistName,
      @JsonbProperty("ntracks") int totalTracks) {

      this.title = title;
      this.artistName = artistName;
      this.totalTracks = totalTracks;
    }

    @Override
    public String toString() {
      return "Album titled " + title + " by " + artistName +
        " contains " + totalTracks + " tracks";
    }
}
