package fr.upem

import java.util.UUID

import cats.Eq
import cats.data.NonEmptyMap
import fr.upem._2_groups.{Cache, NonEmptyCache, Player}
import fr.upem._3_functor.{HttpHeader, Tree}
import org.scalacheck.Arbitrary
import org.scalatest.FlatSpecLike
import org.scalatest.prop.Checkers
import org.typelevel.discipline.Laws
import cats.implicits._
import fr.upem._3_functor.Tree.{Leaf, Node}

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

  implicit def eqPlayer: Eq[Player] = Eq.fromUniversalEquals

  implicit def eqHttpHeader[A: Eq]: Eq[HttpHeader[A]] = Eq.fromUniversalEquals
  implicit def arbitraryHttpHeader[A: Arbitrary]: Arbitrary[HttpHeader[A]] =
    Arbitrary(
      implicitly[Arbitrary[A]].arbitrary.map(HttpHeader(UUID.randomUUID().toString, _))
    )

  implicit def eqTree[A: Eq]: Eq[Tree[A]] = Eq.fromUniversalEquals
  implicit def arbitraryTree[A: Arbitrary]: Arbitrary[Tree[A]] = {
    def inner(depth: Int): Arbitrary[Tree[A]] =
      Arbitrary(
        implicitly[Arbitrary[A]].arbitrary.map(a => {
          if (Math.random() > 0.2 && depth < 2)
            Node(a, inner(depth + 1).arbitrary.sample.get, inner(depth + 1).arbitrary.sample.get)
          else
            Leaf
        })
      )

    inner(0)
  }


}
