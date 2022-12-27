// Explanation of monads, as simple as possible //

// Let's start with an example: type Option[Int] (Int here marks the type that is stored in Option)
// and function `flatMap` for the Option type. `flatMap` is a part of the monadic interface.

// Monad can be defined in different ways (e.g. with bind operator, with fish operator), but
// we will define it with `flatMap` and `unit`. Scala does not provide monadic interface
// by-default, but many types provides `flatMap` function. Type constructors are used instead of
// `unit`.

// The value of type `Option` can be `Some(v)` (contains value `v`) or `None`.
val a: Option[Int] = Some(42)

// We simulate the server response to some request (which is of Int type) with Option. The response
// can be successful or not (e.g. no network), and if successful, it contains some Int:
def response(request: Int): Option[Int] = {
  if (request % 5 == 0) None // failure
  else Some(request + 1)     // success
}

response(42) // Some(43)
response(45) // None

// Say you have a function from Int to Option[Int] (as `response` does), but in the real life
// the request starts from another (previous) response, hence you rather have request of type
// Option[Int] than pure Int.
// `flatMap` function can deal exactly with this case. `flatMap` in our case consumes a function
// Int => Option[Int] and applies it to Option[Int], i.e. it allows us to make a request
// based on a value of the previous response:
Some(42).flatMap(response) // Some(43)

// Note that you don't get Some(Some(43)) here and also None values are handled naturally:
None.flatMap(response) // None

// hence with `flatMap` we can do chain requests easily:
Some(42)
  .flatMap(response) // Some(43)
  .flatMap(response) // Some(44)
  .flatMap(response) // Some(45)
  .flatMap(response) // None
  .flatMap(response) // None

// Without monadic interface one should check every time (with pattern matching or `if`) that the
// response is not None (such a wierd code for only two calls of `response`!):
if (b.isEmpty) {
  None
} else {
  val b1 = response(b.get)
  if (b1.isEmpty) {
    None
  } else {
    val b2 = response(b1.get) // etc.
  }
}

// // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //
// Thus, `flatMap` makes work with _context_ easy. The context in the example is that there can
// be no value. Also, the context remains the same,
// but the type of values in the context can change. Look:
Some("42")                                                                 // Some[String]
  .flatMap { s: String => s.toIntOption /* Some(value) if successful */ }  // Some[Int]
  .flatMap { i: Int => if (i!=0) Some(1 / i.toDouble) else None }          // Some[Double]
// // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

// A lot of types also provide context. For instance, for `List` the context is
// that there are multiple values. Similarly to eliminating nested Some(Some(...))
// with `flatMap` one can flatten lists:
val xs = List(1,2,3)
def twice(x: Int) = List(x, x)
xs.map(twice)     // [[1,1], [2,2], [3,3]]
xs.flatMap(twice) // [1,1,2,2,3,3]

val ys = List(List(1,2), List(3,4)) // [[1,2], [3,4]]
ys.flatten            // [1,2,3,4]
ys.flatMap { x => x } // [1,2,3,4] (!)

// // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //
// Therefore, `flatMap` works as `map` followed by `flatten`.
// // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

// It is not all `flatMap` magic. The next trick is that `flatMap` can pick elements from one
// context and the function to the other context. The context of the function can be somehow
// eliminated, or flattened:
val ys = List(44, 45, 46)
ys.map(response) // [Some(45), None, Some(47)]
ys.flatMap(response) // [45, 47]
//
// `flatMap` for List expects a function that returns a subtype of
// IterableOnce, not anything else. List, surely, is also a subtype of IterableOnce.
// Now it's time to recall the formal definition. In it the monad
// is a single parametric type M[T] with `flatMap` (and `unit`, monad laws...)

// Now, do you understand the examples below?
List(1,2).flatMap { x => List(x, -x) } // List(1, -1, 2, -2)
List("1", "hi!", "2").flatMap(_.toIntOption) // List(1,2)
List("Ab", "Cd").flatMap(_.toLowerCase) // List('a','b','c','d')

// And what about this code?
(1 to 10).toList.flatMap { x => if (x%2 == 1) List(x) else List() }
// List(1,3,5,7,9)

// How about this one? "%c%c".format(a,b) is the same as a.toStr + b.toStr
val chars = ('a' to 'z').toList
chars.toList.flatMap { a =>
  chars.flatMap { b =>
    if (a!=b) List("%c%c".format(a,b)) else List()
  }
}
// This yields all pairs of different letters: ab, ac, ad, ... , zx, zy
//...
