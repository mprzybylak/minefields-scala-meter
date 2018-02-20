package pl.mprzybylak.minefields.scalameter.executors

import org.scalameter
import org.scalameter._
import org.scalameter.api.Gen
import org.scalameter.execution.LocalExecutor
import org.scalameter.reporting.LoggingReporter
import pl.mprzybylak.minefields.scalameter.ManualBenchmarkConfigurationLocalJvm.{measurer, sizes, using}

class LocalExecutorBenchmark extends Bench[Double] {

  lazy val executor: Executor[Double] = LocalExecutor(
    new scalameter.Executor.Warmer.Default,
    Aggregator.min[Double],
    measurer
  )

  lazy val measurer: Measurer[Double] = new Measurer.Default

  lazy val reporter: Reporter[Double] = new LoggingReporter[Double]

  lazy val persistor: Persistor = Persistor.None

  val sizes: Gen[Int] = Gen.range("size")(10000, 100000, 10000)

  val ranges: Gen[Range] = for {
    size <- sizes
  } yield 0 until size

  performance of "Sequence" in {
    measure method "map" config(
      Key.exec.minWarmupRuns -> 10, // minimum number of warmup runs
      Key.exec.maxWarmupRuns -> 20, // maxium number of warmup runs
      Key.exec.warmupCovThreshold -> 0.1, // CoV threshold - whatever it means...
      Key.exec.benchRuns -> 10 // how many time we should run tests
    ) in {
      using(ranges) in {
        r => Seq.range(r.head, r.last).map(_ + 1)
      }
    }
  }
}
