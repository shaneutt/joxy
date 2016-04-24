Joxy - A simple Proxy Server
===

Joxy is a proxy server that does simple round-robin style HTTP proxy routes
for simple, quick proxying.

Routes are configured in a configuration file, along with backends that are
provided for that route.

Requirements
---

Currently only tested on Linux, but should work on any Unix/Unixlike with:

    * bash
    * uuencode/uudecode
    * gradle

Usage
---

You can build an executable copy of Joxy with the following command

```shell
make bin
```

This will produce the entire application as a bash script in `build/bin/joxy`.

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

Building a JAR:

```shell
make jar
```

About
---

I started writing this application to learn Java8. I would not advise using this
in production.
