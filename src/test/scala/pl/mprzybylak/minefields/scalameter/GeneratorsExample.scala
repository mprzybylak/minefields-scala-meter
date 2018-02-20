package pl.mprzybylak.minefields.scalameter

import org.scalameter.Bench
import org.scalameter.api.Gen
import pl.mprzybylak.minefields.scalameter.SetupForTestScenarios.{ranges, sizes, using}

object GeneratorsExample extends Bench.LocalTime {

  import org.scalameter.picklers.Implicits._


  // generator that returns no value
  val unit: Gen[Unit] = Gen.unit("unit generator")

  // generator that returns single, specified value
  val single = Gen.single[String]("single generator")("Hello world")

  // generator that returns values specified in input parameter
  val enumeration = Gen.enumeration[String]("enumeration generator")(".", ".....", "..........", "...............")

  performance of "Writing to console" in {
    measure method "print" in {
      // unit gen is good if our benchmark does not depends on inputs
      using(unit) in {
        r => print(".")
      }
    }

    measure method "println" in {
      // single gen is good if we would like to have signle value that does not change over the tests
      using(single) in {
        r => println(r)
      }
    }

    measure method "println for different number of characters as input" in {
      using(enumeration) in {
        r => println(r)
      }
    }
  }

  // generator that returns linear range of values
  val range: Gen[Int] = Gen.range("range generator")(1, 1000, 100)

  // generator that returns exponential range of fvalues
  val exponential = Gen.exponential("exponential generator")(2, 8192, 2)

  performance of "Algorithms" in {

    measure method "factorial" in {
      // range generator is good when we would like to check our algorithm against different inputs
      using(range) in {
        r => Algorithms.factorial(r)
      }

      measure method "fibbonacci" in {
        // exponential generator is good when we would like to check our algorithm against differnet input
        // and at the same time linear difference does not bring us any meaningful change in results
        using(exponential) in {
          r => Algorithms.fibonacci(r)
        }
      }
    }


  }


}
