package fr.upem._1_eq

import cats.kernel.Eq

case class Person(name: String, age: Int)

object Person {

  // 1.3 Implement Eq instance for Person
  implicit val eq: Eq[Person] = ???

}
