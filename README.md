# MyTimetable for Sakai CLE

[MyTimetable] for Sakai CLE is a [Sakai] tool that displays the upcoming activities for the user currently logged in.


## Deploying to Sakai

### Supported versions of Sakai

This tool is tested with Sakai 2.8.0 (Kernel 1.2.1).

### Build from source

It is assumed that Sakai on the server is [installed from source](https://confluence.sakaiproject.org/pages/viewpage.action?pageId=75106836).
This means that Maven has been installed on the server and has been properly configured.

1. Download the [source] from GitHub and unzip.

2. Build the MyTimetable tool. All the required files are put in the Tomcat directory. `mvn clean install sakai:deploy`

3. Configure the tool in `sakai.properties`.

4. Restart Tomcat

5. Add the MyTimetable tool to a page.

### Configuration

Properties can be configured in `sakai.properties`, which is usually found in the $CATALINA_HOME/sakai folder.

| Property name      | Description                                                                           | Typical value                                 |
| ------------------ | ------------------------------------------------------------------------------------- | --------------------------------------------- |
| apiKey             | Key used for communicating with the MyTimetable API. Key should have elevated access. |                                               |
| apiEndpointUri     | Endpoint URL of the MyTimetable API.                                                  | https://timetable.institution.ac.uk/api/v0/   |
| applicationUri     | URL to the full MyTimetable application.                                              | https://timetable.institution.ac.uk/          |
| applicationTarget  | Target of the full application link.                                                  | _blank                                        |
| numberOfEvents     | Number of events to show in the tool.                                                 | 5                                             |

## MyTimetable API

Documentation for the MyTimetable API is available at https://wiki.eveoh.nl/display/MYTT/API+Documentation.
Documentation on administring API tokens can be found at https://wiki.eveoh.nl/display/MYTT/API+tokens+and+OAuth+information.

## Issue tracking

Please report issues via the [Github issue tracker].

## Contributing

[Pull requests] are more than welcome.

## License

Copyright (c) 2010 - 2013 Eveoh

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program, see LICENSE.
If not, see <http://www.gnu.org/licenses/>.

[MyTimetable]: http://mytimetable.net
[Sakai]: http://www.sakaiproject.org/sakai-cle
[GitHub issue tracker]: https://github.com/eveoh/sakai-mytimetable/issues
[Pull requests]: https://github.com/eveoh/sakai-mytimetable/pulls
[source]: https://github.com/eveoh/sakai-mytimetable/archive/master.zip
