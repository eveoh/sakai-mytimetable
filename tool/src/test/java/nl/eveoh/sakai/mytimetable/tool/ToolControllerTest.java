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
