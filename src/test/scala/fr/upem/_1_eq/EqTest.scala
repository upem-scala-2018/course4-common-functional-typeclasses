package fr.upem._1_eq

import cats.kernel.Eq
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks

class EqTest extends FlatSpec with PropertyChecks {

  "Equality" should "compare ints" in {
    // 1.1 Import the right typeclass instances
    import cats.instances.int._

    forAll { i: Int =>
      Eq[Int].eqv(i, i)
    }
  }

  it should "compare string" in {
    // 1.2 Import the right typeclass instances
    import cats.instances.string._

    forAll { s: String =>
      Eq[String].eqv(s, s)
    }
  }

  implicit val arbitraryPerson: Arbitrary[Person] =
    Arbitrary(Gen.alphaStr.flatMap(name => Gen.choose(0, 100).map(age => Person(name, age))))

  it should "compare persons" in {
    // 1.3 Write an Eq[Person] instance
    forAll { person: Person =>
      Eq[Person].eqv(person, person)
    }
  }

}
