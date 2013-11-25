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

/**
 * Proxy service that calls the Sakai API.
 *
 * @author Marco Krikke
 */
public interface SakaiProxy {

    /**
     * Returns the user id of the current user.
     *
     * @return userId of the current user.
     */
    public String getCurrentUserId();
}
