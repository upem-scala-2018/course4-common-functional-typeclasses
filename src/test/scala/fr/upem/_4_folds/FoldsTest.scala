package fr.upem._4_folds

import cats.{Eval, Foldable}
import fr.upem._3_functor.Tree
import fr.upem._3_functor.Tree.{Leaf, Node}
import org.scalatest.{FlatSpec, Matchers}
import TreeFolds._
import cats.implicits._

class FoldsTest extends FlatSpec with Matchers {

  "Foldable" should "fold tree right side first" in {
    val tree = Node("b", Node("d", Leaf, Node("c", Leaf, Leaf)), Node("a", Leaf, Leaf))

    Foldable[Tree].foldRight(tree, Eval.now(""))((a, acc) => acc.map(a + _)).value should equal("abcd")
//    List("a", "b","c","d").foldRight("")((a, acc) => a + acc) should equal(5)
  }

  it should "fold tree left side first" in {
    val tree = Node("c", Node("a", Leaf, Node("b", Leaf, Leaf)), Node("d", Leaf, Leaf))

    Foldable[Tree].foldLeft(tree, "")(_ + _) should equal("abcd")
  }

}
