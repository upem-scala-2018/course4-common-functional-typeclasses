package fr.upem._2_groups

import cats.data.NonEmptyMap
import cats.implicits._
import cats.kernel.{Band, Monoid, Semigroup}
import fr.upem._2_groups.Codec.{Amv, Mkv, Wmv}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.SortedMap


class GroupTest extends FlatSpec with Matchers {

  "Semigroup" should "combind NonEmptyCaches" in {
    val cache1 = NonEmptyCache(NonEmptyMap("key1" -> "value1", SortedMap.empty))
    val cache2 = NonEmptyCache(NonEmptyMap("key2" -> "value2", SortedMap.empty))

    val expected = NonEmptyCache(NonEmptyMap("key1" -> "value1", SortedMap("key2" -> "value2")))
    Semigroup[NonEmptyCache].combine(cache1, cache2) should equal(expected)
  }

  "Monoid" should "combine Cache" in {
    val cache1 = Cache(Map("key1" -> "value1", "key2" -> "cache1"))
    val cache2 = Cache(Map("key2" -> "cache2"))

    val expected = Cache(Map("key1" -> "value1", "key2" -> "cache1cache2"))
    Monoid[Cache].combine(cache1, cache2) should equal(expected)
  }

  it should "create empty cache" in {
    Monoid[Cache].empty should equal(Cache(Map.empty))
  }

  "Band" should "idempotently combine Players" in {
    val player1 = Player(Set(Mkv, Amv))
    val player2 = Player(Set(Mkv, Wmv))

    val expected = Player(Set(Mkv, Amv, Wmv))
    val firstCombine = Band[Player].combine(player1, player2)
    val secondCombine = Band[Player].combine(firstCombine, player2)
    val thirdCombine = Band[Player].combine(secondCombine, player2)
    thirdCombine should equal(expected)
  }

}
