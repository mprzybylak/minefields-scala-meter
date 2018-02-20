package pl.mprzybylak.minefields.scalameter

import scala.annotation.tailrec

object Algorithms {

  def factorial(n: Int): Int = {

    @tailrec
    def factorial(n: Int, acc: Int = 1): Int = {
      if (n <= 0) acc else factorial(n - 1, n * acc)
    }

    factorial(n)
  }

  def fibonacci(n: Int): Int = {

    @tailrec
    def fib(n: Int, a: Int = 0, b: Int = 1): Int = {
      if (n < 1) a else fib(n - 1, a + b, a)
    }

    fib(n)
  }

}
