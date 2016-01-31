# http-testing

A bit of experimentation to get used to how error handling works
with [md/catch](https://github.com/ztellman/manifold/blob/master/docs/deferred.md#composing-with-deferreds)
and dereferencing deferred.

The main points to take away from this are (they may seem obvious):

1. Do not dereference a deferred early. Doing so will cause error handling for
that deferred to follow the normal java/clojure try and except flow.
2. *Do* dereference as close as possible to the point of consumption. Place any
deferred error handling that the asynchronous process needs, inside of its
deferred chain.

## what this is; how do I use it?

Use this by writing tests cases for behavior you want to understand. Run these
test cases with `lein test`.

This will spin up a small http server locally. I've chosen to test with http as my use
case was understanding how to handle error conditions returned when making http requests.
