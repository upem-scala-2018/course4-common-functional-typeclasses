package fr.upem._2_groups

import cats.data.NonEmptyMap
import cats.kernel.{Monoid, Semigroup, Semilattice}

case class NonEmptyCache(values: NonEmptyMap[String, String])

object NonEmptyCache {

  // 2.1 Implement Semigroup instance for NonEmptyCache (it is possible to reuse existing NonEmptyMap instance)
  implicit val semigroup: Semigroup[NonEmptyCache] = ???

}


case class Cache(values: Map[String, String])

object Cache {

  // 2.2 Implement Monoid instance for Cache
  lazy implicit val monoid: Monoid[Cache] = ???

}


sealed trait Format
object Format {
  case object Mkv extends Format
  case object Webm extends Format
  case object Wmv extends Format
  case object Amv extends Format

  val all: List[Format] = List(Mkv, Webm, Wmv, Amv)
}

case class Player(supportedFormats: Set[Format])

object Player {

  // 2.3 Implement a semilattice instance for player
  // A semilattice is commutative and idempotent
  lazy implicit val semilattice: Semilattice[Player] = ???

}
