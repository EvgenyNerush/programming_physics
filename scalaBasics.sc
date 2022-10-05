// Cheatsheet with Scala basics

println("Hello, Scala!") // like `print` + new line

// function; argument type annotation is necessary
def twice(i: Int) = 2 * i
twice(2) // 4

// `if` returns
if (true) 42 else 35 // yields 42

// recursion; note the result type
def fib(n: Int): Int = {
  if (n <= 0)
    0
  else if (n == 1)
    1
  else
    fib(n - 1) + fib(n - 2)
}
fib(3) // yields 2

// tests
assert(twice(2) == 4) // ok
assert(fib(3) == 2) // ok, no exception

def a = 0 // evaluated every time when called (as a function)
val b = 123 // evaluated immediately (constant value)
// b += 1 is error
lazy val b1 = 345 // evaluated when needed for the first time
var c = 456 // variable
c += 1 // 457

// Lists are immutable and strict (constructed upfront)
val xs = List(1,2,3) ++ List(4,5) // 1,2,3,4,5
// use dot to call members of a class
xs.head // 1
xs.tail // 2,3,4,5

// Range
(1 to 5).toList // 1,2,3,4,5

// map, filter, reduce
val ys = (1 to 5).toList
ys.map(twice) // 2,4,6,8,10
ys map twice // 2,4,6,8,10
ys map (_*2) // 2,4,6,8,10
ys reduce { (x,y) => x + y } // 1+2+3+4+5=15; note lambda-function
ys reduce (_+_) // 15 again
ys filter (_%2 == 0) // evens: 2,4
ys filter (_%2 == 1) // odds: 1,3,5

// constructor operator, `Nil` is the empty list
1 :: Nil // List(1)
0 :: List(1,2) // 0,1,2

// reverse sequence of natural numbers
// (tail recursion in Scala is optimised to loop);
def revNats(n: Int): List[Int] = {
  if (n <= 0)
    Nil
  else
    n :: revNats(n - 1)
}
revNats(5).reverse // 1,2,3,4,5
assert(revNats(5).reverse == List(1,2,3,4,5))

// lazy evaluation: elements are computed when needed;
// infinite list of natural numbers:
def from(n: Int): LazyList[Int] = n #:: from(n + 1)
// `#::` is a constructor for LazyLists
// from(n) = n #:: (n + 1) #:: (n + 2) ...
from(1) // LazyList(<not computed>)
from(1).take(5).toList // List(1,2,3,4,5)

// Sieve of Eratosthenes
def sieve(xs: LazyList[Int]): LazyList[Int] = {
  val head = xs.head
  val notDivisibleByHead = xs.tail.filter(_ % head != 0)
  head #:: sieve( notDivisibleByHead ) // recursion!
}
// one-line version:
// def sieve(xs: LazyList[Int]): LazyList[Int] =
//   xs.head #:: sieve( xs.tail filter (_ % xs.head != 0) )

// prime numbers
val primes = sieve(from(2))

primes.take(10).toList // List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
assert(primes.take(1000).reverse.head == 7919)

// pattern matching
def sum(xs: List[Int]): Int = xs match {
    case Nil => 0
    case head :: tail => head + sum(tail)
}
assert( sum( List(9,1,8,2) ) == 20 )

// if code not used, it can be undefined
def notYetDefined = ???

