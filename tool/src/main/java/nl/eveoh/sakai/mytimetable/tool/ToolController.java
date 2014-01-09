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
import nl.eveoh.mytimetable.apiclient.service.MyTimetableServiceImpl;
import nl.eveoh.sakai.mytimetable.service.SakaiProxy;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the main view of the tool.
 *
 * @author Marco Krikke
 */
public class ToolController implements Controller {
    /**
     * MyTimetable API endpoint service.
     */
    private MyTimetableServiceImpl service;

    /**
     * Sakai proxy service.
     */
    private SakaiProxy sakaiProxy;

    /**
     * MyTimetable API configuration bean.
     */
    private Configuration configuration;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("events", service.getUpcomingEvents(sakaiProxy.getCurrentUserId()));
        map.put("applicationUri", configuration.getApplicationUri());
        map.put("applicationTarget", configuration.getApplicationTarget());

        return new ModelAndView("index", map);
    }

    public MyTimetableServiceImpl getService() {
        return service;
    }

    public void setService(MyTimetableServiceImpl service) {
        this.service = service;
    }

    public SakaiProxy getSakaiProxy() {
        return sakaiProxy;
    }

    public void setSakaiProxy(SakaiProxy sakaiProxy) {
        this.sakaiProxy = sakaiProxy;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
