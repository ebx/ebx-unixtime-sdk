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

## Getting in touch

* **[GitHub Issues](https://github.com/ebx/ebx-unixtime-sdk/issues/new)**: If you have ideas, bugs, 
or problems with our library, just open a new issue.

## Contributing

If you would like to get involved please follow the instructions 
[here](https://github.com/ebx/ebx-unixtime-sdk/tree/master/CONTRIBUTING.md)

## Releases

We use [semantic versioning](https://semver.org/).

All merges into DEV will automatically get released as a maven central snapshot, which can be easily
included in any downstream dependencies that always desire the latest changes (see above for 
'Most Up To Date' installation).

Each merge into the MASTER branch will automatically get released to Maven central and github 
releases, using the current library version. As such, following every merge to master, the version 
number of the dev branch should be incremented and will represent 'Work In Progress' towards the 
next release. 

Please use a merge (not rebase) commit when merging dev into master to perform the release.

To create a full release to Maven central please follow these steps:
1. Ensure the `CHANGELOG.md` is up to date with all the changes in the release, if not please raise 
a suitable PR into `DEV`. Typically the change log should be updated as we go.
3. Create a PR from `DEV` into `MASTER`. Ensure the version in the `pom.xml` is the 
correct version to be released. Merging this PR into `MASTER` will automatically create the maven 
and github releases. Please note that a release is final, it can not be undone/deleted/overwritten.
5. Once the public release has been successful create a final PR into `DEV` that contains an 
incremented `pom.xml` version to ensure the correct snapshot gets updated on subsequent merges
into `DEV`. This PR should also include:
    * An update to the `README.md` latest stable release version number.
    * A 'Work In Progress' entry for the next anticipated release in `CHANGELOG.md`.