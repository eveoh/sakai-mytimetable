# MyTimetable tool for Sakai CLE

[MyTimetable] for Sakai CLE is a [Sakai] tool that displays the upcoming activities for the user currently logged in.

## Deploying to Sakai

### Supported versions of Sakai

This tool is tested with Sakai 2.8.0 (Kernel 1.2.1).

### Extract Tomcat overlay

1. Download the [Tomcat overlay], or build it yourself (see below).

2. Unzip de archive in the Tomcat home directory `$CATALINA_HOME`.

3. Configure the tool in `$CATALINA_HOME/sakai/sakai-configuration.xml`.

4. Restart Tomcat

5. Add the MyTimetable tool to a page.

### Configuration

Settings can be configured in `sakai-configuration.xml`, which is usually found in the $CATALINA_HOME/sakai folder. A
full configuration looks like:

````
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-2.0.xsd">

    <bean id="mytimetable.sakai.model.Configuration" class="nl.eveoh.mytimetable.apiclient.configuration.Configuration">
        <!-- A MyTimetable API key, as included in the api_tokens table. The key needs to have 'elevated' permissions
        (is_elevated should be True) -->
        <property name="apiKey" value="1d30c1e9-cfcb-4893-9659-7618101d7ac9"/>

        <!-- URL to your MyTimetable 2.3+ API. Needs to include the /api/v0/ part. Multiple URLs can be specified to
                support failover in the case of issues with one of the application servers. -->
        <property name="apiEndpointUris">
            <list>
                <value>https://timetable.institution.ac.uk/api/v0/</value>
                <value>https://timetable-server-2.institution.ac.uk/api/v0/</value>
            </list>
        </property>

        <!-- Set this value to false to disable the strict hostname checks and allow any hostname in the certificate.
        Useful when the connection is made using an internal hostname. The SSL certificate still has to be valid. -->
        <property name="apiSslCnCheck" value="true"/>

        <!-- Timeout for connecting to the MyTimetable API, in milliseconds -->
        <property name="apiConnectTimeout" value="1000"/>

        <!-- Timeout for the socket waiting for data from the MyTimetable API -->
        <property name="apiSocketTimeout" value="1000"/>

        <!-- Maximum number of concurrent connections in the MyTimetable API connection pool -->
        <property name="apiMaxConnections" value="20"/>

        <!-- Number of upcoming events shown in the tool -->
        <property name="numberOfEvents" value="5"/>

        <!-- URL to your MyTimetable installation, used in the interface to link to the full timetable. Add
        ?requireLogin=true to automatically trigger authentication. -->
        <property name="applicationUri" value="https://timetable.institution.ac.uk/"/>

        <!-- Defines if the full MyTimetable interface is loaded in a new window (_blank) or in the current window (_top) -->
        <property name="applicationTarget" value="_blank"/>

        <!-- Domain to prefix usernames with. Configure this setting if your MyTimetable usernames include a Windows
        domain name (DOMAIN\username) whereas your Sakai usernames do not have the domain part (username). -->
        <property name="usernameDomainPrefix" value=""/>
    </bean>
</beans>
````

## Building Tomcat overlay

Run `./gradlew clean distZip` (*nix / Mac OS X) or `gradlew.bat clean distZip` (Windows). The 'assembly' module now
contains the Tomcat overlay in ZIP format (in folder `assembly/build/distributions/`).

## MyTimetable API

Documentation for the MyTimetable API is available at https://wiki.eveoh.nl/display/MYTT/API+Documentation.
Documentation on administring API tokens can be found at https://wiki.eveoh.nl/display/MYTT/API+tokens+and+OAuth+information.

## Source code

[Source code] is available at Github.

## Issue tracking

Please report issues via the [Github issue tracker].

## Contributing

[Pull requests] are more than welcome.

## License

    Copyright 2013 - 2014 Eveoh
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[MyTimetable]: http://mytimetable.net
[Sakai]: http://www.sakaiproject.org/sakai-cle
[Source code]: https://github.com/eveoh/sakai-mytimetable
[GitHub issue tracker]: https://github.com/eveoh/sakai-mytimetable/issues
[Pull requests]: https://github.com/eveoh/sakai-mytimetable/pulls
[source]: https://github.com/eveoh/sakai-mytimetable/archive/master.zip
[Tomcat overlay]: https://github.com/eveoh/sakai-mytimetable/releases
