package fr.upem._2_groups

import cats.data.NonEmptyMap
import cats.kernel.{Band, Monoid, Semigroup}
import cats.implicits._

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


sealed trait Codec
object Codec {
  case object Mkv extends Codec
  case object Webm extends Codec
  case object Wmv extends Codec
  case object Amv extends Codec
}

case class Player(supportedFormats: Set[Codec])

object Player {
  implicit val band: Band[Player] =
    (x: Player, y: Player) => Player(x.supportedFormats ++ y.supportedFormats)
}
