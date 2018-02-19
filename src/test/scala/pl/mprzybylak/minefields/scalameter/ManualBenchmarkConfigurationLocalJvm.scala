package pl.mprzybylak.minefields.scalameter

import org.scalameter
import org.scalameter._
import org.scalameter.api.Gen
import org.scalameter.execution.LocalExecutor
import org.scalameter.reporting.LoggingReporter

object ManualBenchmarkConfigurationLocalJvm extends Bench[Double] {

  // local executor needs implicit pickler
  import org.scalameter.picklers.Implicits._

  // local executor runs tests on the same jvm that scala meter is running
  lazy val executor: Executor[Double] = LocalExecutor(
    new scalameter.Executor.Warmer.Default,

    // this will select the fastests result for each input set
    Aggregator.min[Double],
    measurer
  )

  // default measurer will run each test fixed amount of time
  lazy val measurer: Measurer[Double] = new Measurer.Default

  // logging reporter will put result on console
  lazy val reporter: Reporter[Double] = new LoggingReporter[Double]

  // we do not want to persist results so this is persistor that will do nothing
  lazy val persistor: Persistor = Persistor.None

  val sizes: Gen[Int] = Gen.range("size")(10000, 100000, 10000)

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
