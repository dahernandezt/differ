# Differ Service

Compares two base64 encoded json strings.

## Installation

It requires Java 8 and Maven.

On a workspace folder type:

```sh
$ git clone https://github.com/dahernandezt/differ.git
$ cd differ
$ mvn package
$ java -jar target/diff_service-0.0.1-SNAPSHOT.jar 
```

## Testing with swagger:

1) Access to localhost:8080/swagger-ui.html

2) Endpoints will be available at Diff section. 

##Testing with Curl:

Left endpoint:

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d 'eyAiZ3JlZXRpbmciOiAgIkhlbGxvIFdvcmQhIn0=' 'http://localhost:8080/v1/diff/123/left'
```

Right endpoint:

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d 'eyAiZ3JlZXRpbmciOiAgIkhlbGxvIFdvcmQhIn0=' 'http://localhost:8080/v1/diff/123/right'
```

Diff endpoint:

```
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/v1/diff/123'
```


## What can be improved:

* Application was developed with installation and running simplicity in mind, therefore is using an embedded Mongo Database that doesn´t keep 
information when shutting down.  The first improvement would be to add external Mongo Server configuration capabilities using an application.yml file and
spring configuration properties features. 


* Currently it is allowed to call the left and right endpoints several times with the same Id and data will be overrided. Data single entry may be adopted adding a validation and returning an exception handled as a 404 or 409 http code when trying to duplicate calls.


