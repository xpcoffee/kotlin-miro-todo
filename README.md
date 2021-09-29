# Miro Todo

Program that queries against a miro board and outputs tasks that are TODO, DOING and DONE.

## Structure

Using structure convention laid out here https://kotlinlang.org/docs/gradle.html#kotlin-and-java-sources

## Configuration

Create an populate the following file to configure
the script. 

See the [Miro API](#Miro_API) section to 
see how to create an access token and for how to find
a board ID.

```properties
# src/main/resources/miro.properties
board=<Miro board ID>
accessToken=<Miro accessToken>
frames=Comma,Separated,List,Of,Frame,Titles
```

## Miro API

Quick links for reference.

 - Getting started with the Miro REST API: https://developers.miro.com/docs/introduction-to-rest-api
 - List all Widgets API: https://developers.miro.com/reference#get-board-widgets-1

## Tech choice log

### 2021-09-29 Use Ktor for HTTP client

Just following guide here: https://kotlinlang.org/docs/kmm-use-ktor-for-networking.html#connect-ktor