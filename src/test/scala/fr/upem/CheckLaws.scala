package fr.upem

import cats.Eq
import cats.data.NonEmptyMap
import fr.upem._2_groups.{Cache, NonEmptyCache, Player}
import fr.upem._3_functor.Tree
import org.scalacheck.Arbitrary
import org.scalatest.FlatSpecLike
import org.scalatest.prop.Checkers
import org.typelevel.discipline.Laws
import org.scalacheck.ScalacheckShapeless._
import cats.implicits._

import scala.collection.immutable.SortedMap

trait CheckLaws extends Checkers { self: FlatSpecLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet): Unit = {
    for ((id, prop) ← ruleSet.all.properties)
      (name + "." + id) should "be satisfied" in {
        check(prop)
      }
  }

  implicit def eqNonEmptyCache: Eq[NonEmptyCache] = Eq.fromUniversalEquals
  implicit val arbitraryNonEmptyCache: Arbitrary[NonEmptyCache] =
    Arbitrary(
      for {
        head <- implicitly[Arbitrary[(String, String)]].arbitrary
        tail <- implicitly[Arbitrary[SortedMap[String, String]]].arbitrary
      } yield NonEmptyCache(NonEmptyMap(head, tail))
    )

  implicit def eqCache: Eq[Cache]               = Eq.fromUniversalEquals
  implicit val arbitraryCache: Arbitrary[Cache] = Arbitrary(implicitly[Arbitrary[Map[String, String]]].arbitrary.map(Cache(_)))

  implicit def eqPlayer: Eq[Player]               = Eq.fromUniversalEquals
  implicit val arbitraryPlayer: Arbitrary[Player] = implicitly[Arbitrary[Player]]

  implicit def eqTree[A: Eq]: Eq[Tree[A]] = Eq.fromUniversalEquals
  implicitly[Arbitrary[Tree[String]]]

}
