package fr.upem._1_eq

import cats.kernel.Eq

case class Person(name: String, age: Int)

object Person {

  // 1.3 Implement Eq instance for Person
  lazy implicit val eq: Eq[Person] = Eq.fromUniversalEquals[Person]

  // Ou plus classiquement
//  lazy implicit val eq: Eq[Person] = new Eq[Person] {
//    override def eqv(x: Person, y: Person): Boolean = x == y
//  }

}
