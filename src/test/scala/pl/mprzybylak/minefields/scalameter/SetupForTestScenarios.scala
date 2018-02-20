package pl.mprzybylak.minefields.scalameter

import org.scalameter._
import org.scalameter.api.{Bench, Gen}

object SetupForTestScenarios extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.range("size")(10000, 100000, 10000)

  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  performance of "Sequence" in {
    measure method "map with default conf" in {
      using(ranges) in {
        r => Seq.range(r.head, r.last).map(_ + 1)
      }
    }
  }

  performance of "Sequence" in {
    measure method "map with 10 bench runs" config (
      // here we are specifing that we would like to have only 10 repetitions of our benchmark before moving to next range
      Key.exec.benchRuns -> 10,

      // here we are specifing that we should take at least 10 rounds for warming up the JVM
      Key.exec.minWarmupRuns -> 10,

      // here we are specifing that we should take at most 20 rounds for warming up the JVM
      Key.exec.maxWarmupRuns -> 20
    ) in {
      using(ranges) in {
        r => Seq.range(r.head, r.last).map(_ + 1)
      }
    }
  }

}
