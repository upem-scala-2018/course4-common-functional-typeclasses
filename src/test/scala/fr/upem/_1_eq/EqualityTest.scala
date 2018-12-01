package fr.upem._1_eq

import cats.kernel.Eq
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks

class EqualityTest extends FlatSpec with PropertyChecks {

  "Equality" should "compare ints" in {
    // 1.1 Import the right typeclass instances
    implicit val ev: Eq[Int] = ???

    forAll { i: Int =>
      Eq[Int].eqv(i, i)
    }
  }

  it should "compare string" in {
    // 1.2 Import the right typeclass instances
    implicit val ev: Eq[String] = ???

    forAll { s: String =>
      Eq[String].eqv(s, s)
    }
  }

  case class Person(name: String, age: Int)
  implicit val arbitraryPerson: Arbitrary[Person] =
    Arbitrary(Gen.alphaStr.flatMap(name => Gen.choose(0, 100).map(age => Person(name, age))))

  it should "compare persons" in {
    // 1.3 Write an Eq[Person] instance
    implicit val ev: Eq[Person] = ???

    forAll { person: Person =>
      Eq[Person].eqv(person, person)
    }
  }

}
