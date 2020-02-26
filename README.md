# Raised-Hands
## About

Raised-Hands is a platform that increases classroom engagement, and shortens the feedback loop for teachers and students knowing how the class is progressing.

## How to Use

TBD

---
## How to set up locally

This project has 3 main components: a web server, a web client, and a mobile app. Below, steps are given for setting them up on your own machine.

### Raised-Hands server
**Using Docker**  
1. Build the latest source: from CLI in `raised-hands-server` directory, execute:
    ```
    $ docker build --build-arg JAR_FILE=build/libs/*.jar -t raised-hands/raised-hands-server .
    ```
2. Run the containerized server:
    ```
    $ docker run -p 8080:8080 -t raised-hands/raised-hands-server
    ```
3. You can now communicate with the server at `localhost:8080`
  
**Using Gradle**
1. Create a build: from CLIT in the `raised-hands-server` directory, execute:
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
    npm start
    ```
3. You can now view the web-client at `localhost:3000`

### Raised-Hands Android app

`TODO`

---
## How to test

### Raised-Hands server

From  `raised-hands-server` directory, execute:
```
./gradlew test
```

### Raised-Hands web client

From `raised-hands-web-client` directory, execute:
```
$ npm test
```

### Raised-Hands Android app

`TODO`

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
