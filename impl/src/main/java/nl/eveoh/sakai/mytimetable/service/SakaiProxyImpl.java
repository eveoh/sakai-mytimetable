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

package nl.eveoh.sakai.mytimetable.service;

import org.sakaiproject.tool.api.SessionManager;

/**
 * Implementation of the SakaiProxy interface.
 *
 * @author Marco Krikke
 * @see SakaiProxy
 */
public class SakaiProxyImpl implements SakaiProxy {
    private SessionManager sessionManager;

    /**
     * {@inheritDoc}
     */
    public String getCurrentUserId() {
        return sessionManager.getCurrentSessionUserId();
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
