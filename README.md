# java-heatmap

A simple API that is used to get heatmaps. It's done in Java with Spring Boot.

Flyway migration scripts and global controller exception validation is overkill for this project with only one database table and one controller, but I wanted to show implementation of those techniques.

## Using the project

To run the project, run the following command in your terminal: `./gradlew BootRun`

Each time the project runs, random test data is generated.

### answer-rate endpoint

Once the project runs, you can call GET `/api/heatmap/answer-rate`. The endpoint is protected by Basic Auth. Currently, the configured username is `user` and password `password`.

The following query parameters must be added: `numberOfShades` (can be between 3 and 10), `dateInput` (must be formatted YYYY-MM-DD)

The following query parameters are optional: `startHour` (0 to 23) and `endHour` (0 to 23 and must be equal or greater than start hour). If these aren't provided, they default to 0 and 23 respectively.

Sample query and response:

```
localhost:8080/api/heatmap/answer-rate?numberOfShades=7&dateInput=2025-04-24&startHour=8&endHour=11
```

```
[
    {
        "hour": 8,
        "answeredCalls": 1,
        "totalCalls": 1,
        "rate": 100.0,
        "shade": "Shade7"
    },
    {
        "hour": 9,
        "answeredCalls": 1,
        "totalCalls": 3,
        "rate": 33.333336,
        "shade": "Shade3"
    },
    {
        "hour": 10,
        "answeredCalls": 0,
        "totalCalls": 3,
        "rate": 0.0,
        "shade": "Shade1"
    },
    {
        "hour": 11,
        "answeredCalls": 1,
        "totalCalls": 2,
        "rate": 50.0,
        "shade": "Shade4"
    }
]
```

### call-logs endpoint

To know what test data you have, you can call GET `/api/heatmap/call-logs`. Here too you must provide credentials, which will be the same as for the answer-rate endpoint.