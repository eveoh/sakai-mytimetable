/*
 * Copyright 2013 - 2014 Eveoh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.eveoh.sakai.mytimetable.tool;

import nl.eveoh.mytimetable.apiclient.configuration.Configuration;
import nl.eveoh.mytimetable.apiclient.exception.LocalizableException;
import nl.eveoh.mytimetable.apiclient.model.Event;
import nl.eveoh.mytimetable.apiclient.service.MyTimetableServiceImpl;
import nl.eveoh.sakai.mytimetable.service.SakaiProxy;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Krikke
 */
@RunWith(MockitoJUnitRunner.class)
public class ToolControllerTest {
    @Mock
    private MyTimetableServiceImpl myTimetableService = Mockito.mock(MyTimetableServiceImpl.class);

    @Mock
    private SakaiProxy sakaiProxy = Mockito.mock(SakaiProxy.class);

    @Mock
    private Configuration configuration = Mockito.mock(Configuration.class);

    @InjectMocks
    private ToolController toolController = new ToolController();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testIndexPage() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setMethod("GET");

        ModelAndView modelAndView = toolController.handleRequest(request, response);

        Assert.assertEquals("Should get index page.", "index", modelAndView.getViewName());
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testModel() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setMethod("GET");

        // Display two test events
        List<Event> testEvents = new ArrayList<Event>();
        testEvents.add(new Event());
        testEvents.add(new Event());

        Mockito.when(myTimetableService.getUpcomingEvents(Mockito.anyString())).thenReturn(testEvents);
        Mockito.when(configuration.getApplicationUri()).thenReturn("https://timetable.institution.ac.uk/");
        Mockito.when(configuration.getApplicationTarget()).thenReturn("_blank");

        ModelAndView modelAndView = toolController.handleRequest(request, response);

        Assert.assertTrue("Should contain events attribute.", modelAndView.getModelMap().containsAttribute("events"));
        Assert.assertTrue(modelAndView.getModelMap().containsAttribute("applicationUri"));
        Assert.assertTrue(modelAndView.getModelMap().containsAttribute("applicationTarget"));

        Assert.assertEquals("Should display 5 events.", 2, ((List<Event>) modelAndView.getModelMap().get("events")).size());
        Assert.assertEquals("https://timetable.institution.ac.uk/", modelAndView.getModelMap().get("applicationUri"));
        Assert.assertEquals("_blank", modelAndView.getModelMap().get("applicationTarget"));

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testError() throws Exception {
        // Exception should be handled by Sakai
        exception.expect(LocalizableException.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setMethod("GET");

        Mockito.when(myTimetableService.getUpcomingEvents(Mockito.anyString())).thenThrow(LocalizableException.class);

        toolController.handleRequest(request, response);
        Assert.assertEquals(200, response.getStatus());
    }
}
