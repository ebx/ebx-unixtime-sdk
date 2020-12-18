# ebx-unixtime-sdk

A simple reusable implementation of a unix time construct.

This was originally created for use in Java 6-7 to allow for test mocking but since Java 8 we
 also have:

```java
import java.time.Instant;
...
long unixTimestamp = Instant.now().getEpochSecond();
```

Whenever unix times are required (and form part of business logic) the preferred usage is to
 inject `Supplier<Long>` into constructors:
 
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

We can then do:

```java
new Test(Instant.now()::getEpochSecond)
```

or where the existing class is being mocked:

```java
new Test(UnixTime::now)
```

