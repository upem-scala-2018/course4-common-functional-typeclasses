package fr.upem._1_eq

import cats.kernel.Eq

case class Person(name: String, age: Int)

object Person {

  implicit val eq: Eq[Person] = (x: Person, y: Person) => x == y

}
