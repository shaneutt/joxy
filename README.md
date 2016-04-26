Joxy - A simple load balancer and proxy server
===

[![Build Status](https://travis-ci.org/shaneutt/joxy.svg?branch=master)](https://travis-ci.org/shaneutt/joxy.svg?branch=master)

Joxy is a load balancer and proxy server with configurable routes.

Routes are configured in a configuration file, along with backends that are
provided for that route.

Routes configured in the config file can have different routing policies and
act independently of one another.

Requirements
---

Currently only tested on Linux, but should work on any Unix/Unixlike with:

    * bash
    * uuencode/uudecode
    * Java 7 or 8

Usage
---

If you want to just run the proxy server and start testing it from the git repo:

```shell
make run
```

You can also build an executable copy of Joxy with the following command

```shell
make bin
```

This will produce the entire application as a bash script in `build/bin/joxy` which should be portable.

Configuring
---

Joxy uses a YAML configuration file for the server, and for its services and routes:

```yaml
server:
    address: 127.0.0.1
    port:    8080
routes:
    - name: testservice1
      path: /testing/
      routes:
          - address: 127.0.0.1
            port:    8081
          - address: 127.0.0.1
            port:    8082
    - name: testservice2
      path: /testing2/
      routes:
          - address: 127.0.0.1
            port:    8083
          - address: 127.0.0.1
            port:    8084
```

The config above configures the Joxy server to listen on `127.0.0.1:8000` and has two services it's forwarding for.

Requests that match `/testing/` will all be forwarded to the routes defined for `testservice1`:

```yaml
routes:
    - address: 127.0.0.1
      port:    8081
    - address: 127.0.0.1
      port:    8082
```

These will be provided via the round-robin routing technique (by default, other policies are not yet implemented).

Similarly requests to `/testing2/` will be forwarded to the routes defined for `testservice2`.

Building
---

Cleaning:

```shell
make clean
```

Compiling:

```shell
make compile
```

Running the tests:

```shell
make test
```

Running the tests with grade `--info`:

```shell
make test-info
```

Building a JAR:

```shell
make jar
```

About
---

I started writing this application to learn Java8. I would not advise using this
in production.

