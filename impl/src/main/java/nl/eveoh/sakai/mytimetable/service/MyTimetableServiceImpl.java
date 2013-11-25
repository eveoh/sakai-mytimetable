/*
 * MyTimetable tool for Sakai CLE.
 *
 * Copyright (c) 2010 - 2013 Eveoh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program, see src/main/webapp/license/gpl-3.0.txt.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package nl.eveoh.sakai.mytimetable.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.eveoh.sakai.mytimetable.exception.LocalizableException;
import nl.eveoh.sakai.mytimetable.model.Configuration;
import nl.eveoh.sakai.mytimetable.model.Event;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the MyTimetableService interface.
 *
 * @author Marco Krikke
 * @see MyTimetableService
 */
public class MyTimetableServiceImpl implements MyTimetableService {
    private static final Logger log = Logger.getLogger(MyTimetableServiceImpl.class);

    private ObjectMapper mapper = new ObjectMapper();

    private HttpClient client;

    public MyTimetableServiceImpl() {
        // Make sure the Jackson ObjectMapper does not fail on other properties in the JSON response.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        HttpClientBuilder builder = HttpClientBuilder.create();
        this.client = builder.build();
    }

    /**
     * {@inheritDoc}
     */
    public List<Event> getEvents(String userId, Configuration configuration) {
        List<Event> events = new ArrayList<Event>();

        try {
            HttpUriRequest request = getApiRequest(userId, configuration);

            if (request != null) {
                HttpResponse response = client.execute(request);

                events = mapper.readValue(response.getEntity().getContent(),
                        mapper.getTypeFactory().constructCollectionType(List.class, Event.class));
            }
        } catch (JsonMappingException e) {
            log.error("Could not fetch results from MyTimetable API.", e);

            throw new LocalizableException("Could not fetch results from MyTimetable API.", e);
        } catch (IOException e) {
            log.error("Could not fetch results from MyTimetable API.", e);

            throw new LocalizableException("Could not fetch results from MyTimetable API.", e);
        }

        return events;
    }

    private HttpUriRequest getApiRequest(String userId, Configuration configuration) {
        if (StringUtils.isBlank(userId)) {
            log.error("Username cannot be empty.");

            throw new LocalizableException("Username cannot be empty.", "notLoggedIn");
        }

        if (StringUtils.isBlank(configuration.getApiKey())) {
            log.error("API key cannot be empty.");

            throw new LocalizableException("API key cannot be empty.");
        }

        // build request URI
        Date currentTime = new Date();
        String baseUrl;

        if (configuration.getApiEndpointUri().endsWith("/")) {
            baseUrl = configuration.getApiEndpointUri() + "timetable";
        } else {
            baseUrl = configuration.getApiEndpointUri() + "/timetable";
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            uriBuilder.addParameter("startDate", Long.toString(currentTime.getTime()));
            uriBuilder.addParameter("limit", Integer.toString(configuration.getNumberOfEvents()));

            URI apiUri = uriBuilder.build();

            HttpUriRequest request = new HttpGet(apiUri);
            request.addHeader("apiToken", configuration.getApiKey());
            request.addHeader("requestedAuth", userId);

            return request;
        } catch (URISyntaxException e) {
            log.error("Could not get MyTimetable API url", e);

            throw new LocalizableException("Could not get MyTimetable API url.", e);
        }
    }
}
