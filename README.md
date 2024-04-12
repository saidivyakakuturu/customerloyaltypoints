# Demo Awarding Points

# Pre-requisites
- [java >= 17] (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [maven >= 3.3] (https://maven.apache.org/download.cgi)

# How to develop locally on my laptop?
----------------------

Reference your home directory as HOME_DIR below since Java is platform independent and does not recognize Unix tilde *~* shortcut.
 

### Setup Your Java IDE
This section will describe how to setup your Java IDE so you can debug and develop the code. There are two options for IDE. Eclipse or IntelliJ

#### IntelliJ IDE
##### Run and Import Code
1. Click on Import Project and select your HOME_DIR/customerLoyaltyPoints folder
2. Click on Import project from external model and choose Maven
3. Click Next->Finish and wait for import to complete.

##### Run the WebAPI Server
1. Click on Search
2. In *Search String* box type *App* and hit the *Search* button
3. In the *Search* tab at the bottom right click *main(String[])*
4. In the pop up-menu select Run as—>Run Configuration—>Java Applications
5. Click *Run* and the web server will start
6. Confirm that is running by hitting http://localhost:8080/actuator/health


#### Eclipse IDE
##### Run and Import Code
1. Click on File->Import->Existing Maven Projects->Next
2. Click on Browse and select your HOME_DIR/customerLoyaltyPoints folder
3. Click on Finish and wait for import to complete.

##### Run the WebAPI Server
1. Click on Search->Java
2. In *Search String* box type *App* and hit the *Search* button
3. In the *Search* tab at the bottom right click *main(String[])*
4. In the pop up-menu select Run as—>Run Configuration—>Java Applications
5. Click the tab “Arguments”
6. Click *Run* and the web server will start
7. Confirm that is running by hitting http://localhost:8080/actuator/health

# How to run from docker container?
----------------------
### Build the jar file
1. From HOME_DIR/customerLoyaltyPoints folder run
```
mvn clean install
```
2. You should be logged into your docker hub account to build the image with the next command
```
docker build -t myorg/myapp .
```
3. then you can run the image with the next command
```
docker run -p 8080:8080 myorg/myapp
```
4. Confirm that is running by hitting http://localhost:8080/actuator/health

# How to run unit test?
---------------------
### mvn test
In the root project folder, run "mvn clean test". This will run all the unit tests.

### You can run all test in a single class with this command:
for AwardingPointsControllerTest:
```
 mvn test -Dtest="AwardingPointsControllerTest"
```

or for  SpendingsServiceTest:
```
mvn test -Dtest="SpendingsServiceTest"
```

### You can run a single test method in a class
#### AwardingPointsControllerTest

getAwardingPointsTest (test general 200 response):
```
mvn test -Dtest="AwardingPointsControllerTest#getAwardingPointsTest"
...
[INFO] Running org.retailer.demo.controller.AwardingPointsControllerTest
16:23:35.004 [main] INFO org.retailer.demo.controller.AwardingPointsControllerTest -- Response object: <200 OK OK,[],[]>
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.66 s - in org.retailer.demo.controller.AwardingPointsControllerTest

```

getAwardingPointsInvalidaDateTest (exception when the date parameter is invalid):
```
mvn test -Dtest="AwardingPointsControllerTest#getAwardingPointsInvalidaDateTest"
...
[INFO] Running org.retailer.demo.controller.AwardingPointsControllerTest
16:31:02.836 [main] INFO org.retailer.demo.controller.AwardingPointsControllerTest -- Exception message: Required request parameter 'fromDate' for method parameter type LocalDate is not present
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.647 s - in org.retailer.demo.controller.AwardingPointsControllerTest

```

getAwardingPointsExceptionDateInFutureTest (exception when the date to search is in future):
```
mvn test -Dtest="AwardingPointsControllerTest#getAwardingPointsExceptionDateInFutureTest"
...
[INFO] Running org.retailer.demo.controller.AwardingPointsControllerTest
16:31:02.836 [main] INFO org.retailer.demo.controller.AwardingPointsControllerTest -- Exception message: fromDate cannot be in the future
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.647 s - in org.retailer.demo.controller.AwardingPointsControllerTest

```

exceptionHandlerDateTest (test exception handler 400 response)
```
mvn test -Dtest="AwardingPointsControllerTest#exceptionHandlerDateTest"
...
[INFO] Running org.retailer.demo.controller.AwardingPointsControllerTest
16:31:02.836 [main] INFO org.retailer.demo.controller.AwardingPointsControllerTest -- Exception message: Required request parameter 'fromDate' for method parameter type LocalDate is not present
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.647 s - in org.retailer.demo.controller.AwardingPointsControllerTest

```

#### SpendingsServiceTest

getAwardingPointsTest (test sum of monthly and three months total):
```
mvn test -Dtest="SpendingsServiceTest#getAwardingPointsTest"
...
[INFO] Running org.retailer.demo.service.SpendingsServiceTest
16:33:42.926 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- customer name: John Doe
16:33:42.930 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- number of months searched (3): 3
16:33:42.930 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- Earned points fisrt month: 30
16:33:42.930 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- Earned points second month: 100
16:33:42.931 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- Earned points third month: 280
16:33:42.931 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- Earned points total: 410
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.731 s - in org.retailer.demo.service.SpendingsServiceTest

```

getAwardingPointsTest (exception when no customer in database):
```
mvn test -Dtest="SpendingsServiceTest#getAwardingPointsNoCustomersTest"
...
[INFO] Running org.retailer.demo.service.SpendingsServiceTest
16:35:24.985 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- Exception message: No customers found
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.737 s - in org.retailer.demo.service.SpendingsServiceTest

```

getAwardingPointsTest (exception months to search env value is less than 1):
```
mvn test -Dtest="SpendingsServiceTest#getAwardingPointsMonthsExceptionTest"
...
[INFO] Running org.retailer.demo.service.SpendingsServiceTest
16:36:04.900 [main] INFO org.retailer.demo.service.SpendingsServiceTest -- Exception message: monthsToSearch env value must be greater than 0
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.747 s - in org.retailer.demo.service.SpendingsServiceTest

```

### Surefire Report
You can execute the next command to generate the surefire report.
```
mvn surefire-report:report
```
You can find the surefire report in the target folder ${basedir}/target/site/surefire-report.html. The report is in HTML format and can be viewed in any browser:

![img.png](src/main/resources/images/surefire_report.png)

# How to monitor the application?
-------------------------------
All CORE Services using the standard urls below for monitoring.

| Service        |                  Url                   |  Purpose |
| ------------- |:--------------------------------------:| :------------: |
| Health Check | http://localhost:8080/actuator/health  | Performs a real time health check of the server |
| Performance | http://localhost:8080/actuator/metrics | Get real time performance metrics of DB, APIs, etc.. |
| Stack Trace |   http://localhost:8080/actuator/threaddump    | Gets the stack trace of all threads on the server |

# Sample API Calls
---------------------

### Request
```
http://localhost:8080/rewardingPoints/v1?fromDate=2023-01-01
curl --location 'http://localhost:8080/rewardingPoints/v1?fromDate=2023-01-01'
```
### Response
```
[
  {
    "customerName": "John Doe",
    "awardedPoints": [
      {
        "date": "2023-01-01 / 2023-01-31",
        "points": 30
      },
      {
        "date": "2023-02-01 / 2023-02-28",
        "points": 100
      },
      {
        "date": "2023-03-01 / 2023-03-31",
        "points": 280
      }
    ],
    "totalPoints": 410
  },
  {
    "customerName": "Jane Doe",
    "awardedPoints": [
      {
        "date": "2023-01-01 / 2023-01-31",
        "points": 70
      },
      {
        "date": "2023-02-01 / 2023-02-28",
        "points": 960
      },
      {
        "date": "2023-03-01 / 2023-03-31",
        "points": 1260
      }
    ],
    "totalPoints": 2290
  }
]
```
### You can access the Swagger UI to try the API at the following URL
```
http://localhost:8080/swagger-ui/index.html
```
Request:
![swagger_request.png](src/main/resources/images/swagger_request.png)
Response:
![swagger_response.png](src/main/resources/images/swagger_response.png)

### The OpenAPI description will be available at the following URL in JSON format
```
http://localhost:8080/v3/api-docs
```

![api_docs_json.png](src/main/resources/images/api_docs_json.png)

### Or in YAML format
```
http://localhost:8080/v3/api-docs.yaml
```

# Example dataset
---------------------
### Customers:

| Id  |   Name   |
|-----|:--------:|
| 1   | John Doe |
| 2   | Jane Doe |

### Transactions:

|Customer |	Spending |	Amount |	Date|
|---------|:---------:|:-------:|:---:|
|John Doe |	magazines |	$60 |	2023-01-01|
|John Doe |	candy |	$70 |	2023-01-15|
|John Doe |	shoes |	$80 |	2023-02-01|
|John Doe |	suit |	$110 |	2023-02-15|
|John Doe |	door |	$80 |	2023-03-01|
|John Doe |	play station |	$200 |	2023-03-15|
|Jane Doe |	shoes |	$80 |	2023-01-01|
|Jane Doe |	dinner |	$90 |	2023-01-15|
|Jane Doe |	dress |	$130 |	2023-02-01|
|Jane Doe |	phone |	$500 |	2023-02-15|
|Jane Doe |	tv |	$600 |	2023-03-01|
|Jane Doe |	make up |	$180 |	2023-03-15|

### Monthly total:

| Customer |	Month |	Total Points|
|----------|:-----:|:-----------:|
| John Doe |	Jan |	30|
| John Doe |	Feb |	100|
| John Doe |	Mar |	280|
|          |total |	410|
| Jane Doe |	Jan |	70|
| Jane Doe |	Mar |	960|
| Jane Doe |	Mar |	1260|
|          | total    |	2290|