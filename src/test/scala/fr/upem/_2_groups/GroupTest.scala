package fr.upem._2_groups

import cats.data.NonEmptyMap
import cats.kernel.laws.discipline.{MonoidTests, SemigroupTests, SemilatticeTests}
import cats.kernel.{Band, CommutativeSemigroup, Monoid, Semigroup}
import fr.upem._2_groups.Format.{Amv, Mkv, Wmv}
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.SortedMap
import cats.implicits._
import fr.upem.CheckLaws

class GroupTest extends FlatSpec with PropertyChecks with Matchers with CheckLaws {

  "Semigroup" should "combine NonEmptyCaches" in {
    val cache1 = NonEmptyCache(NonEmptyMap("key1" -> "value1", SortedMap.empty))
    val cache2 = NonEmptyCache(NonEmptyMap("key2" -> "value2", SortedMap.empty))

    val expected = NonEmptyCache(NonEmptyMap("key1" -> "value1", SortedMap("key2" -> "value2")))
    Semigroup[NonEmptyCache].combine(cache1, cache2) should equal(expected)
  }

  checkAll("SemigroupLaws[NonEmptyCache]", SemigroupTests[NonEmptyCache].semigroup)

  "Monoid" should "combine Caches" in {
    val cache1 = Cache(Map("key1" -> "value1", "key2" -> "cache1"))
    val cache2 = Cache(Map("key2" -> "cache2"))

    val expected = Cache(Map("key1" -> "value1", "key2" -> "cache1cache2"))
    Monoid[Cache].combine(cache1, cache2) should equal(expected)
  }

  it should "create empty cache" in {
    Monoid[Cache].empty should equal(Cache(Map.empty))
  }

  checkAll("Monoid[Cache]", MonoidTests[Cache].monoid)

  implicit val playerArbitrary: Arbitrary[Player] = Arbitrary(Gen.someOf(Format.all).map(f => Player(f.toSet)))

  "Semilattice" should "combine Players" in {
    val player1 = Player(Set(Mkv, Amv))
    val player2 = Player(Set(Mkv, Wmv))

    val expected = Player(Set(Mkv, Amv, Wmv))
    Band[Player].combine(player1, player2) should equal(expected)
  }

  it should "idempotently combine Players" in {
    forAll { (player1: Player, player2: Player) =>
      val firstCombine  = Band[Player].combine(player1, player2)
      val secondCombine = Band[Player].combine(firstCombine, player2)
      val thirdCombine  = Band[Player].combine(secondCombine, player2)

      thirdCombine should equal(firstCombine)
    }
  }

  it should "commutativeley combine Players" in {
    forAll { (player1: Player, player2: Player) =>
      val player1Then2 = CommutativeSemigroup[Player].combine(player1, player2)
      val player2Then1 = CommutativeSemigroup[Player].combine(player2, player1)

      player1Then2 should equal(player2Then1)
    }
  }

  checkAll("Semilattice[Player]", SemilatticeTests[Player].semilattice)

}
