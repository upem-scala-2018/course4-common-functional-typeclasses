package fr.upem._4_folds

import cats.{Eval, Foldable}
import fr.upem._3_functor.Tree
import fr.upem._3_functor.Tree.{Leaf, Node}

object TreeFolds
{
  // 4.1 Write a foldable instance for Tree
  implicit val foldable: Foldable[Tree] = new Foldable[Tree] {
    override def foldLeft[A, B](fa: Tree[A], b: B)(f: (B, A) => B): B =
      fa match {
        case Leaf => b
        case Node(a, l, r) =>
          val lhs = foldLeft(l, b)(f)
          val curr = f(lhs, a)
          val rhs = foldLeft(r, curr)(f)
          rhs
      }

    override def foldRight[A, B](fa: Tree[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa match {
        case Leaf => lb
        case Node(a, l, r) =>
          val lhs = foldRight(l, lb)(f)
          val curr = f(a, lhs)
          val rhs = foldRight(r, curr)(f)
          rhs
      }
  }

  // 4.2 Write a traverse instance for Tree

}
