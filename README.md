# Event Processing Application

This is application can process Events from various sources in various formats and aggregate them into targets in various target format.



## What is an Event

Event is consisted of following attributes

- Client Address 
- Client GUID
- Request Timestamp
- Service GUID
- Retries Request Count
- Packets Requested Count
- Packets Serviced Count
- Max Hole Size



## How to run

1. Install Maven

2. Checkout the repository

3. Run the following command from the root folder

   ```bash
   mvn spring-boot:run
   ```

   Then the it will process *reports.csv, reports.xml, reports.json* files in the root folder and output a *out.csv*  file

4. If you want run it with different arguments 

   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments=<output filename>,<input filename1>,<inputfilename2>
   ```



## TODO

- ~~Created a framework to handle arbitrary input sources and formats and output targets, formats~~

- ~~Created generic JSON, CSV, XML format deserialization mappers and CSV serialization writer~~

- ~~Updated the README.md file with instructions~~

- Dockerize the application

- Create Unit Tests

  