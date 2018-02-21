package pl.mprzybylak.minefields.scalameter.measurers

import org.scalameter
import org.scalameter._
import org.scalameter.api.Gen
import org.scalameter.execution.LocalExecutor
import org.scalameter.reporting.LoggingReporter

object IgnoreGcMeasurer extends Bench[Double] {

  import org.scalameter.picklers.Implicits._

  lazy val executor: Executor[Double] = LocalExecutor(
    new scalameter.Executor.Warmer.Default,
    Aggregator.min[Double],
    measurer
  )

  // this measurer will ignore results where GC was running
  lazy val measurer: Measurer[Double] = new Measurer.IgnoringGC

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
