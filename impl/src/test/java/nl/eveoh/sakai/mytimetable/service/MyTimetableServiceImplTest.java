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

import nl.eveoh.sakai.mytimetable.exception.LocalizableException;
import nl.eveoh.sakai.mytimetable.model.Configuration;
import nl.eveoh.sakai.mytimetable.model.Event;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Marco Krikke
 */
@RunWith(MockitoJUnitRunner.class)
public class MyTimetableServiceImplTest {
    public static final String USER_ID = "ldaptest30";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private HttpClient httpClient = Mockito.mock(HttpClient.class);

    @InjectMocks
    private MyTimetableServiceImpl service = new MyTimetableServiceImpl();

    private Configuration configuration = new Configuration();

    @Before
    public void setUp() throws Exception {
        configuration.setApiEndpointUri("https://timetable.institution.ac.uk/api/v0/");
        configuration.setApiKey("token");
        configuration.setApplicationUri("https://timetable.institution.ac.uk/");
        configuration.setApplicationTarget("_blank");
        configuration.setNumberOfEvents(5);

        Resource resource = new ClassPathResource("response.json");
        HttpResponse httpResponse = prepareResponse(200, IOUtils.toString(resource.getInputStream()));
        Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponse);
    }

    @Test
    public void testInvalidEndpointUrl() throws IOException {
        exception.expect(LocalizableException.class);

        configuration.setApiEndpointUri("htt:\\dfgdfg");

        service.getEvents(USER_ID, configuration);
    }

    @Test
    public void testMissingApiKey() {
        exception.expect(LocalizableException.class);

        configuration.setApiKey(null);
        service.getEvents(USER_ID, configuration);

        configuration.setApiKey("   ");
        service.getEvents(USER_ID, configuration);
    }

    @Test
    public void testWrongApiUrl() throws IOException {
        exception.expect(LocalizableException.class);
        Mockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(prepareResponse(500, ""));

        service.getEvents(USER_ID, configuration);
    }

    @Test
    public void testGetEvents() throws Exception {
        List<Event> events = service.getEvents(USER_ID, configuration);

        Assert.assertEquals(5, events.size());

        Event e = events.get(2);

        Assert.assertEquals(2, e.getLocations().size());

        Date startDate = new Date(1381905000000L);
        Date endDate = new Date(1381914000000L);

        Assert.assertEquals(startDate, e.getStartDate());
        Assert.assertEquals(endDate, e.getEndDate());

        Assert.assertEquals("23", e.getActivityDescription());
    }

    private HttpResponse prepareResponse(int expectedResponseStatus, String expectedResponseBody) {
        HttpResponse response = new BasicHttpResponse(
                new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), expectedResponseStatus, ""));
        response.setStatusCode(expectedResponseStatus);
        response.setEntity(new StringEntity(expectedResponseBody, ContentType.APPLICATION_JSON));

        return response;
    }
}
