// tag::comment[]
/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Artist {
    
    @JsonProperty("name") private String name;
    @JsonProperty("albums") private Album albums[];
    
    // does not map to anything
    @JsonIgnore private boolean legendary = true;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Album[] getAlbums() {
        return albums;
    }
    
    public void setAlbums(Album[] albums) {
        this.albums = albums;
    }
    
    @Override
    public String toString() {
        return name + " wrote " + albums.length + " albums";
    }

}
