package fr.upem._4_folds

import cats.implicits._
import cats.laws.discipline.FoldableTests
import cats.{Eval, Foldable}
import fr.upem.CheckLaws
import fr.upem._3_functor.Tree
import fr.upem._3_functor.Tree.{Leaf, Node}
import fr.upem._4_folds.TreeFolds._
import org.scalatest.{FlatSpec, Matchers}

class FoldsTest extends FlatSpec with Matchers with CheckLaws {

  "Foldable" should "fold tree right side first" in {
    val tree = Node("b", Node("d", Leaf, Node("c", Leaf, Leaf)), Node("a", Leaf, Leaf))

    Foldable[Tree].foldRight(tree, Eval.now("z"))((a, acc) => acc.map(a + _)).value should equal("abcdz")
  }

  it should "fold tree left side first" in {
    val tree = Node("b", Node("d", Leaf, Node("c", Leaf, Leaf)), Node("a", Leaf, Leaf))

    Foldable[Tree].foldLeft(tree, "z")(_ + _) should equal("zabcd")
  }

  checkAll("Foldable[Tree]", FoldableTests[Tree].foldable[Int, Int])

}
