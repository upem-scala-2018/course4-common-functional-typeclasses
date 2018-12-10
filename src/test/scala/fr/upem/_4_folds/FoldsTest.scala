package fr.upem._4_folds

import cats.{Eval, Foldable, Traverse}
import fr.upem._3_functor.Tree
import fr.upem._3_functor.Tree.{Leaf, Node}
import org.scalatest.{FlatSpec, Matchers}
import TreeFolds._
import cats.implicits._
import cats.laws.discipline.{FoldableTests, TraverseTests}
import fr.upem.CheckLaws

class FoldsTest extends FlatSpec with Matchers with CheckLaws {

  "Foldable" should "fold tree right side first" in {
    val tree = Node("b", Node("d", Leaf, Node("c", Leaf, Leaf)), Node("a", Leaf, Leaf))

    Foldable[Tree].foldRight(tree, Eval.now(""))((a, acc) => acc.map(a + _)).value should equal("abcd")
  }

  it should "fold tree left side first" in {
    val tree = Node("c", Node("a", Leaf, Node("b", Leaf, Leaf)), Node("d", Leaf, Leaf))

    Foldable[Tree].foldLeft(tree, "")(_ + _) should equal("abcd")
  }

  checkAll("Foldable[Tree]", FoldableTests[Tree].foldable[Int, Int])

  val isTwo: Int => Option[Int] = x =>  if (x == 2) Some(2) else None

}
