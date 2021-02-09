[![Maven Central](https://img.shields.io/maven-central/v/com.echobox/ebx-unixtime-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.echobox%22%20AND%20a:%22ebx-unixtime-sdk%22) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://raw.githubusercontent.com/ebx/ebx-unixtime-sdk/master/LICENSE) [![Build Status](https://travis-ci.org/ebx/ebx-unixtime-sdk.svg?branch=dev)](https://travis-ci.org/ebx/ebx-unixtime-sdk)
# ebx-unixtime-sdk

A simple reusable implementation of a unix time construct.

This was originally created for use in Java 6-7 to allow for test mocking but since Java 8 we
 also have:

```java
import java.time.Clock;
...
long unixTimestamp = Clock.systemUTC().instant().getEpochSecond();
```
and
```java
import java.time.Instant;
...
long unixTimestamp = Instant.now().getEpochSecond();
```

Wherever unix times are required (and form part of business logic) the preferred usage is to
 inject `java.time.Clock` into constructors or failing that `Supplier<Long>`.

## Installation

For our latest stable release use:

```
<dependency>
  <groupId>com.echobox</groupId>
  <artifactId>ebx-unixtime-sdk</artifactId>
  <version>2.0.0</version>
</dependency>
```

## Example using `java.time.Clock`

Important: When testing please use `Clock.fixed(Instant.parse("2017-10-22T07:52
:11Z
 "), ZoneOffset.UTC)` or similar rather than mocking the clock to avoid NPEs.
 
```java
public class Test {

  private final Clock clock;

  public Test(Clock clock) {
    this.clock = clock;
  }
 
  public void doSomething() {
    long currentUnixTime = clock.instant().getEpochSecond();
    ...
  }
}
```

We could then instantiate by:

```java
new Test(Clock.systemUTC())
```

or

```java
new Test(Clock.fixed(Instant.parse("2017-10-22T07:52:11Z"), ZoneOffset.UTC))
```

## Example using `Supplier<Long>`
 
```java
public class Test {

  private final Long<Supplier> unixTimeSupplier;

  public Test(Long<Supplier> unixTimeSupplier) {
    this.unixTimeSupplier = unixTimeSupplier;
  }
 
  public void doSomething() {
    long currentUnixTime = unixTimeSupplier.get();
    ...
  }
}
```

We could then instantiate by:

```java
new Test(Instant.now()::getEpochSecond)
```

or where the existing class still has legacy mocks:

```java
new Test(UnixTime::now)
```

