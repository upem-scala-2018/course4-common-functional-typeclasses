package fr.upem._2_groups

import cats.data.NonEmptyMap
import cats.kernel.{Monoid, Semigroup, Semilattice}
import cats.instances.map._
import cats.instances.string._


case class NonEmptyCache(values: NonEmptyMap[String, String])

object NonEmptyCache {

  // 2.1 Implement Semigroup instance for NonEmptyCache (it is possible to reuse existing NonEmptyMap instance)
  implicit val semigroup: Semigroup[NonEmptyCache] =
    (x: NonEmptyCache, y: NonEmptyCache) => NonEmptyCache(Semigroup[NonEmptyMap[String, String]].combine(x.values, y.values))


}


case class Cache(values: Map[String, String])

object Cache {

  // 2.2 Implement Monoid instance for Cache
  implicit val monoid: Monoid[Cache] = new Monoid[Cache] {
    override def empty: Cache = Cache(Map.empty)
    override def combine(x: Cache, y: Cache): Cache = Cache(Monoid[Map[String, String]].combine(x.values, y.values))
  }

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
  // A semilattice "combine" method is commutative and idempotent
  lazy implicit val semilattice: Semilattice[Player] = new Semilattice[Player] {
    override def combine(x: Player, y: Player): Player = Player(x.supportedFormats ++ y.supportedFormats)
  }

}
