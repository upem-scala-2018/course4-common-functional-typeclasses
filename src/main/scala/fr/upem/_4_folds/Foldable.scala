package fr.upem._4_folds

import cats.{Applicative, Eval, Foldable, Traverse}
import fr.upem._3_functor.Tree
import fr.upem._3_functor.Tree.{Leaf, Node}

object TreeFolds {
  // 4.1 Write a foldable instance for Tree
  lazy implicit val foldable: Foldable[Tree] = ???

  // 4.2 Hard - Write a traverse instance for Tree
  // You can remove the above Foldable instance since Traverse extends Foldable
  lazy implicit val traverse: Traverse[Tree] = ???

}
