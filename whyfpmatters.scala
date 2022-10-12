import scala.math.{Pi, sin}

object MyMain extends App {
  println("Hello, Scala! Answer = %d, Pi = %f".format(42, Pi))

  // ----------------------------------------------------------------------//
  // Inspired by 'Why Functional Programming Matters' by John Hughes, 1990 //
  //-----------------------------------------------------------------------//

  //// Functions are the first-class passengers ////

  // any function of Pi; note functional type here
  def ofPi(f: Double => Double): Double = f(Pi)

  val r = ofPi(sin)
  println("%f".format(r)) // a very small number

  // Composition of functions, f.g: (f.g)(x) = f(g(x))
  def compose[A,B,C](f: B => C, g: A => B): A => C = (g andThen f)

  // `n` times apply `f`;
  // Recursion instead of a loop;
  // Without `x`, just functions and composition
  def applyNTimes(f: Double => Double, n: Int): Double => Double =
    if (n == 1) f else compose(applyNTimes(f, n - 1), f)

  // let's try
  def g(x: Double) = x * x
  def h(x: Double) = applyNTimes(g, 2)(x) // (x^2)^2 = x^4
  println("%f".format(h(3.0))) // 81.0

  //// Algebraic data types: product types (e.g. pair) and sum types (e.g. variant) ////
  
  // How such types can be arranged inside?

  def printMaybe(x: Option[Int]) = x match {
    case Some(value) => println("%d".format(value))
    case None => println("None")
  }

  val num = Some(5)
  printMaybe(num) // 5

  //// It's time to make some lists... ////

  /* "Modular design brings with it great productivity improvements.
  First of all, small modules can be coded quickly and easily. Second,
  general-purpose modules can be reused, leading to faster development
  of subsequent programs. Third, the modules of a program can be tested
  independently, helping to reduce the time spent debugging."
  John Hughes
   */

  // `::` is a list constructor
  // []
  val a = Nil
  // [1]
  val b = 1 :: Nil
  // [1, 2]
  val c = 1 :: (2 :: Nil)

  // simple recursive function
  def sum(list: List[Int]): Int = list match {
    case Nil => 0
    case x :: xs => x + sum(xs)
  }

  println("sum([1,2]) = %d".format(sum(c))) // 3

  /* Only `0` and `+` in the definition of `sum` are specific to computing a sum.
  This means that the computation of a sum can be modularized by gluing together
  a general recursive pattern with `0` and `+`. This recursive pattern is
  conventionally called `foldr` (right fold).
   */

  // pattern matching anonymous function
  def foldr[A,B](plus: (A, B) => B, zero: B): List[A] => B = {
    case Nil => zero
    case x :: xs => plus(x, foldr(plus, zero)(xs))
  }

  def newSum = foldr[Int,Int]( { (x, y) => x + y }, 0 )
  println("newSum([1,2]) = %d".format(newSum(List(1,2)))) // 3

  /* And now `foldr` can be reused! */

  // These functions return functions, 'higher order functions'
  // note that zero = 1 here
  def product = foldr[Int, Int]((_*_), 1)
  def anyTrue = foldr[Boolean,Boolean]((_ || _), false)
  def allTrue = foldr[Boolean,Boolean]((_ && _), true)

  // [4, 1, 2]
  val ints = 4 :: c
  println("product([4,1,2]) = %d".format(product(ints))) // 8
  // [false, true]
  val bools = List(false, true)
  println("anyTrue([0,1]) = " + anyTrue(bools).toString) // true
  println("allTrue([0,1]) = " + allTrue(bools).toString) // false

  /* "One way to understand `foldr` is as a function that replaces all occurrences
  of `::` in a list by `plus`, and all occurrences of `Nil` by `zero`."
  1 :: 2 :: Nil
  1 +  2 +  0
   */

  // Thus foldr(::, Nil)(xs) = xs, and we can reuse `foldr` even further,
  // e.g. we can substitute `Nil` with another list:
  def append(xs: List[Int], ys: List[Int]): List[Int] =
    foldr[Int,List[Int]]((_::_), ys)(xs)
  println( "append([1,2], [3,4]) = " + append(List(1,2), List(3,4)).toString )

  // That if we substitute `Cons()` with `count`?
  def count[T](x: T, n: Int): Int = n + 1
  // `count` increments 0 as many times as there are `::`
  def length = foldr[Int,Int](count, 0)
  println("length of [4,1,2] = %d".format(length(ints))) // 3

  /* Now we can write a function which `plusOne` a number and then joins it to
     a list.
   */
  def plusOne(n: Int, xs: List[Int]) = (n + 1) :: xs
  // This can be generalized (here `Cons` comes from Constructor, ::)
  def fAndCons[A,B](f: A => B): (A, List[B]) => List[B] = { (x, ys) =>
    f(x) :: ys
  }
  // And we comes to a `map` - another useful function, which applies any function `f`
  // to all the elements of a list
  def map[A,B](f: A => B): List[A] => List[B] =
    foldr(fAndCons(f), Nil)

  println(map[Int,Int](_*2)(List(1,2,3)).toString) // [2,4,6]

  // TODO: mean, root-mean-square with map
}
