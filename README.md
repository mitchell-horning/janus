# janus

A [Luminus](http://www.luminusweb.net/) template turned into a multi-user digraph editing tool.

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Development

The template for this project was generated using Luminus version "3.53" by running

```console
$ lein new luminus janus +postgres +reagent +shadow-cljs
```

The development workflow differs slightly from the Luminus default because we are using [shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html). In order to launch an external development REPL, you must run `lein run` and `lein shadow watch app` in two separate terminal processes. Then, you may connect to the nREPL server using your tool of choice.

### Emacs+CIDER
If you would like to develop from within Emacs+CIDER, first run `lein shadow compile app` to create an initial build. Then, run `M-x cider-jack-in` and choose `shadow` as the REPL to start.

Once the REPL has been launched, you must launch a shadow-cljs watch on the build as well as the http server which hosts the app:

```Clojure
shadow.user> (shadow/watch :app)
shadow.user> (shadow/repl :app)
cljs.user> :cljs/quit
user> (start)
```

Note that [shadow-cljs's documentation](https://shadow-cljs.github.io/docs/UsersGuide.html#user-config) suggests that CIDER users add `[cider/cider/nrepl "0.23.0-SNAPSHOT"]` to the dependencies vector of their `~/.shadow-cljs/config.edn` files.

## License

Copyright © 2019 Mitchell Horning
