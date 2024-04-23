# Java library for Mercure

The goal of this library is to provide a quick way to publish messages on Mercure. 
If you don't know what Mercure is, take a look here: (https://github.com/dunglas/mercure).

## Installing

You can install via Gradle or Maven. Whatever floats your boat. :)

Gradle:
```groovy
implementation 'io.github.matts:mercure:0.14.4-1'
```

Maven:
```xml
<dependency>
  <groupId>io.github.matts</groupId>
  <artifactId>mercure</artifactId>
  <version>0.14.4-1</version>
  <type>pom</type>
</dependency>
```

## How to use

This library is really simple to use, you just need to create a new `Publisher` and for each message you
want to publish, just create a new `Message` object.

Here is an example:

```java
package io.github.matts.test;

import exceptions.io.github.matts.mercure.HubNotFoundException;
import exceptions.io.github.matts.mercure.PublishRejectedException;
import exceptions.io.github.matts.mercure.UnauthorizedPublisherException;

public class Main {

    public static void main(String[] args) throws UnauthorizedPublisherException, PublishRejectedException, HubNotFoundException {
        var token = "your.secret.token";
        var data = "{\"test\": 10}";

        var publisher = new Publisher("http://localhost:3000/.well-known/mercure", token);
        var message = new Message(data, "mytopic");
        var result = publisher.publish(message);
        System.out.println("Message ID: " + result.getId().toString());
    }
}

```

## Versioning

This project is prefixed by the version of the Mercure protocol it supports, followed by a build number. The current version is 0.14.4-1

## Credits

Forked from Vitor Villar https://github.com/vitorluis/java-mercure