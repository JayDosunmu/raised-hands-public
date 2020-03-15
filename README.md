# Raised-Hands
## About

Raised-Hands is a platform that increases classroom engagement, and shortens the feedback loop for teachers and students knowing how the class is progressing.

## How to Use

TBD

---
## How to set up locally

This project has 3 main components: a web server, a web client, and a mobile app. Below, steps are given for setting them up on your own machine.

---
#### Dependencies:
* Java 8 (JDK + JRE)
* Android Studio
* Node >= 12
* Docker

---

### Stand up the server and web-client together using Docker Compose
* Using this method also starts the server in debug mode, allowing you to [attach a remote debugger (using intelliJ for instance)](https://www.baeldung.com/spring-debugging) on port `5005`
1. First create a new build for the server. From a terminal `cd` into the `raised-hands-server` directory, and use Gradle to create a build:
    ```
    $ cd /path/to/raised-hands/raised-hands-server

    $ ./gradlew build
    ```
1. Create a copy of `.env.template` named `.env` in the top-level `raised-hands` project directory, and fill in the appropriate values for the environment variables:
    ```
    $ cd /path/to/raised-hands

    $ cp .env.template .env

    $ vim .env
    # Database

    POSTGRES_USER=<provide your value>
    POSTGRES_PASSWORD=<provide your value>

    ... # other env variables

    ```
1. [`docker-compose`](https://docs.docker.com/compose/) can be used to stand up both the server and web-client by executing the following from the top-level `raised-hands` project directory:
    ```
    $ cd /path/to/raised-hands

    $ docker-compose up --build -t
    ```
1. Communicate with the server from `localhost:8080`
1. Attach a remote debugger to the server from `localhost:5005`
1. Access the web-client from `localhost:3000`

### Raised-Hands server
**Using Docker**  
1. Create a build: from CLI in the `raised-hands-server` directory, execute:
    ```
    $ ./gradlew build 
    ```
2. Containerize the build:
    ```
    $ docker build --build-arg JAR_FILE=build/libs/*.jar -t raised-hands/raised-hands-server .
    ```
3. Run the containerized server:
    ```
    $ docker run -p 8080:8080 -t raised-hands/raised-hands-server
    ```
4. You can now communicate with the server at `localhost:8080`
  
**Using Gradle**
1. Create a build: from CLI in the `raised-hands-server` directory, execute:
    ```
    $ ./gradlew build 
    ```
2. After the build is completed, from the same folder, execute:
    ```
    $ java -jar build/libs/raised-hands-server-{project version}.jar
    ```
    replace {project version} with the value found in `build.gradle`

3. You can now communicate with the server at `localhost:8080`

### Raised-Hands web client
1. Install dependencies for web-client: from `raised-hands-web-client` directory, execute:
    ```
    $ npm i
    ```
2. Run the client:
    ```
    $ npm start
    ```
3. You can now view the web-client at `localhost:3000`

### Raised-Hands Android app

1. Open the `raised-hands-android` project in `Android Studio`

2. Run the app using the `run app` button or `^R`

---
## How to test

### Raised-Hands server

From  `raised-hands-server` directory, execute:
```
$ ./gradlew test
```

### Raised-Hands web client

From `raised-hands-web-client` directory, execute:
```
$ npm test
```

### Raised-Hands Android app

From  `raised-hands-android` directory, execute:
```
$ ./gradlew test
```

---
## How to deploy

### Raised-Hands server

`TODO`

### Raised-Hands web client

`TODO`

### Raised-Hands Android app

`TODO`

## Team Members
- Jenisha Adhikari
- Taslim Dosunmu
- Faizon Williams
- Chengyong Zhao
