# dropwizard-logging-config

[![Build Status](https://travis-ci.org/stuartgunter/dropwizard-logging-config.png?branch=master)](https://travis-ci.org/stuartgunter/dropwizard-logging-config)

The `dropwizard-logging-config` library allows you to configure Logback within a Dropwizard app at runtime. It provides
a Dropwizard `Task` that can be made accessible via the admin interface to allow for runtime changes to the logging
level of any logger in the application. This is intended to provide similar functionality to Logback's `JmxConfigurator`,
but using a standard Dropwizard approach.

## Usage

To use this task, simply register it in your Dropwizard application. The dependency can be found in Maven Central
with the following coordinates:

```xml
<dependency>
  <groupId>org.stuartgunter</groupId>
  <artifactId>dropwizard-logging-config</artifactId>
  <version>${dropwizard-logging-config.version}</version>
</dependency>
```

Once you have the dependency registered, register the `LogConfigurationTask` in your application as follows:

```java
public class YourApp extends Application<YourConfig> {

    @Override
    public void run(YourConfig configuration, Environment environment) throws Exception {
        environment.admin().addTask(new LogConfigurationTask());
    }
}
```

That's all you need to do in code. All that remains is to call the tasks endpoint correctly. Below are some examples
of how you can do that.

### Configure a user-defined log level for a single `Logger`

```shell
curl -X POST -d "logger=org.stuartgunter.dropwizard&level=INFO" http://localhost:8081/tasks/log-level
```

### Configure a user-defined log level for multiple `Logger`s

```shell
curl -X POST -d "logger=org.stuartgunter.dropwizard&logger=io.dropwizard.core&level=INFO" http://localhost:8081/tasks/log-level
```

### Configure the default log level for a single `Logger`

```shell
curl -X POST -d "logger=org.stuartgunter.dropwizard" http://localhost:8081/tasks/log-level
```
