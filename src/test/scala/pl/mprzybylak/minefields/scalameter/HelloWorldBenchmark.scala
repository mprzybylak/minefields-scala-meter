package pl.mprzybylak.minefields.scalameter

import org.scalameter.api._

/**
  * Bench.LocalTime class is the most simple predefined test class which fits best for start playing with
  * microbenchmarks
  */
object HelloWorldBenchmark extends Bench.LocalTime {

  // Generator of test data with name 'size' - will generate input from 10k to 100k with step of 10k
  val sizes: Gen[Int] = Gen.range("size")(10000, 100000, 10000)

  // Create ranges from generators
  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  performance of "Sequence" in {
    measure method "map" in {
      using(ranges) in {
        r => Seq.range(r.head, r.last).map(_ + 1)
      }
    }
  }


}
