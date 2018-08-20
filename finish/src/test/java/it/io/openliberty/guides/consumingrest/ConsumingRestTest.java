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
package it.io.openliberty.guides.consumingrest;

import static org.junit.Assert.assertEquals;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.openliberty.guides.consumingrest.model.Artist;

public class ConsumingRestTest {

    private static String port;
    private static String baseUrl;
    private static String targetUrl;

    private Client client;
    private Response response;

    // tag::setup[]
    @BeforeClass
    public static void oneTimeSetup() {
      port = System.getProperty("liberty.test.port");
      baseUrl = "http://localhost:" + port + "/artists/";
      targetUrl = baseUrl + "total/";
    }

    @Before
    public void setup() {
      client = ClientBuilder.newClient();
    }

    @After
    public void teardown() {
      client.close();
    }
    // end::setup[]

    // tag::tests[]
    // tag::testArtistDeserialization[]
    @Test
    public void testArtistDeserialization() {
      response = client.target(baseUrl + "jsonString").request().get();
      this.assertResponse(baseUrl + "jsonString", response);

      Jsonb jsonb = JsonbBuilder.create();

      String expectedString = "{\"name\":\"foo\",\"albums\":"
        + "[{\"title\":\"album_one\",\"artist\":\"foo\",\"ntracks\":12}]}";
      Artist expected = jsonb.fromJson(expectedString, Artist.class);

      String actualString = response.readEntity(String.class);
		  Artist[] actual = jsonb.fromJson(actualString, Artist[].class);

      assertEquals("Expected names of artists does not match", expected.name, actual[0].name);

      response.close();
    }
    // end::testArtistDeserialization[]

    // tag::testAlbumCount[]
    @Test
    public void testJsonBAlbumCount() {
      String[] artists = {"dj", "bar", "foo"};
      for (int i = 0; i < artists.length; i++) {
        response = client.target(targetUrl + artists[i]).request().get();
        this.assertResponse(targetUrl + artists[i], response);

        int expected = i;
        int actual = response.readEntity(int.class);
        assertEquals("Album count for " + artists[i] + " does not match", expected, actual);

        response.close();
      }
    }
    // end::testAlbumCount[]

    // tag::testAlbumCountForUnknownArtist[]
    @Test
    public void testJsonBAlbumCountForUnknownArtist() {
      response = client.target(targetUrl + "unknown-artist").request().get();

      int expected = -1;
      int actual = response.readEntity(int.class);
      assertEquals("Unknown artist must have -1 albums", expected, actual);

      response.close();
    }
    // end::testAlbumCountForUnknownArtist[]

    // tag::testArtistCount[]
    @Test
    public void testJsonPArtistCount() {
      response = client.target(targetUrl).request().get();
      this.assertResponse(targetUrl, response);

      int expected = 3;
      int actual = response.readEntity(int.class);
      assertEquals("Expected number of artists does not match", expected, actual);

      response.close();
    }
    // end::testArtistCount[]

    /**
     * Asserts that the given URL has the correct (200) response code.
     */
    // tag::assertResponse[]
    private void assertResponse(String url, Response response) {
      assertEquals("Incorrect response code from " + url, 200, response.getStatus());
    }
    // end::assertResponse[]
    // end::tests[]
}
