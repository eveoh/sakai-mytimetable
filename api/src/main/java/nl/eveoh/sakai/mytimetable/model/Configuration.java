/*
 * Eveoh MyTimetable, Web interface for timetables.
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

package nl.eveoh.sakai.mytimetable.model;

/**
 * Configuration bean for the tool.
 * <p/>
 * This bean can be configured in <tt>sakai.properties</tt> using the format 
 * <tt>property@mytimetable.sakai.configuration=value</tt>,
 * for example <tt>apiEndpointUri@mytimetable.sakai.configuration=https://timetable.institution.ac.uk/api/v0/</tt>.
 *
 * @author Marco Krikke
 */
public class Configuration {
    /**
     * Key used for communicating with the MyTimetable API.
     * <p/>
     * Key should have elevated access.
     */
    private String apiKey = "";

    /**
     * Endpoint URL of the MyTimetable API.
     * <p/>
     * Should be something like <tt>https://timetable.institution.ac.uk/api/v0/</tt>.
     */
    private String apiEndpointUri = "";

    /**
     * URL to the full MyTimetable application.
     * <p/>
     * Should be something like <tt>https://timetable.institution.ac.uk/</tt>.
     */
    private String applicationUri = "";

    /**
     * Target of the full application link.
     * <p/>
     * Should be <tt>_self</tt>, <tt>_blank</tt>, <tt>_parent</tt>, or <tt>_top</tt>. Defaults to <tt>_blank</tt>.
     */
    private String applicationTarget = "_blank";

    /**
     * Number of events to show in the tool.
     * <p/>
     * Defaults to 5.
     */
    private int numberOfEvents = 5;

    public Configuration() {
    }

    public Configuration(String apiKey, String apiEndpointUri, String applicationUri, String applicationTarget,
                         int numberOfEvents) {
        this.apiKey = apiKey;
        this.apiEndpointUri = apiEndpointUri;
        this.applicationUri = applicationUri;
        this.applicationTarget = applicationTarget;
        this.numberOfEvents = numberOfEvents;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiEndpointUri() {
        return apiEndpointUri;
    }

    public void setApiEndpointUri(String apiEndpointUri) {
        this.apiEndpointUri = apiEndpointUri;
    }

    public String getApplicationUri() {
        return applicationUri;
    }

    public void setApplicationUri(String applicationUri) {
        this.applicationUri = applicationUri;
    }

    public String getApplicationTarget() {
        return applicationTarget;
    }

    public void setApplicationTarget(String applicationTarget) {
        this.applicationTarget = applicationTarget;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }
}
