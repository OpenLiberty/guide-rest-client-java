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
package io.openliberty.guides.consumingrest;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.openliberty.guides.consumingrest.model.Album;
import io.openliberty.guides.consumingrest.model.Artist;

public class Consumer {
    // tag::class[]
    // tag::consumeWithJsonp[]
    public static Artist[] consumeWithJsonp(String targetUrl) {
        Client client = ClientBuilder.newClient();
        
        Response response = client.target(targetUrl).request().get();
        JsonArray arr = response.readEntity(JsonArray.class);
        
        response.close();
        client.close();
        
        return Consumer.collectArtists(arr);
    }
    // end::consumeWithJsonp[]
    
    // tag::consumeWithJackson[]
    public static Artist[] consumeWithJackson(String targetUrl) {
        Client client = ClientBuilder.newClient();
        client.register(JacksonJsonProvider.class);
        
        Response response = client.target(targetUrl).request().get(); 
        Artist[] artists = response.readEntity(Artist[].class);
        
        response.close();
        client.close();
        
        return artists;
    }
    // end::consumeWithJackson[]
    
    // tag::collectArtists[]
    private static Artist[] collectArtists(JsonArray artistArr) {
        List<Artist> artists = artistArr.stream().map(artistJson -> {  
            Artist artist = new Artist();
            artist.setName(((JsonObject) artistJson).getString("name"));
            JsonArray albumArr = ((JsonObject) artistJson).getJsonArray("albums");;
            artist.setAlbums(Consumer.collectAlbums(albumArr));
            return artist;
        }).collect(Collectors.toList());
        
        return artists.toArray(new Artist[artists.size()]);
    }
    
    // end::collectArtists[]
    
    // tag::collectAlbums[]
    private static Album[] collectAlbums(JsonArray albumArr) {
        List<Album> albums = albumArr.stream().map(albumJson -> {
            Album album = new Album();
            album.setTitle(((JsonObject) albumJson).getString("title"));
            album.setArtistName(((JsonObject) albumJson).getString("artist"));
            album.setTotalTracks(((JsonObject) albumJson).getInt("ntracks"));
            return album;
        }).collect(Collectors.toList());
        
        return albums.toArray(new Album[albums.size()]);
    }
    // end::collectAlbums[]
    // end::class[]
}