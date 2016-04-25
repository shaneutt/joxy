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

