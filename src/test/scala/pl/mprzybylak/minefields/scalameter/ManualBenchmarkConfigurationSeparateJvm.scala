package pl.mprzybylak.minefields.scalameter

import org.scalameter
import org.scalameter._
import org.scalameter.api.Gen
import org.scalameter.execution.SeparateJvmsExecutor
import org.scalameter.reporting.LoggingReporter

object ManualBenchmarkConfigurationSeparateJvm extends Bench[Double] {

  // separate jvms executor needs implicit pickler
  import org.scalameter.picklers.Implicits._

  // separate jvm executor will create at least
  lazy val executor: Executor[Double] = SeparateJvmsExecutor(
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
    measure method "map" in {
      using(ranges) in {
        r => Seq.range(r.head, r.last).map(_ + 1)
      }
    }
  }
}
