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

import nl.eveoh.sakai.mytimetable.model.Configuration;
import nl.eveoh.sakai.mytimetable.service.MyTimetableService;
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
    private MyTimetableService service;

    /**
     * Sakai proxy service.
     */
    private SakaiProxy sakaiProxy;

    /**
     * Configuration bean.
     */
    private Configuration configuration;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("events", service.getEvents(sakaiProxy.getCurrentUserId(), configuration));
        map.put("applicationUri", configuration.getApplicationUri());
        map.put("applicationTarget", configuration.getApplicationTarget());

        return new ModelAndView("index", map);
    }

    public void setService(MyTimetableService service) {
        this.service = service;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public SakaiProxy getSakaiProxy() {
        return sakaiProxy;
    }

    public void setSakaiProxy(SakaiProxy sakaiProxy) {
        this.sakaiProxy = sakaiProxy;
    }
}
