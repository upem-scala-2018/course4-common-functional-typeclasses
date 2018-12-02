package fr.upem._4_folds

import cats.{Applicative, Eval, Foldable, Traverse}
import fr.upem._3_functor.Tree
import fr.upem._3_functor.Tree.{Leaf, Node}

object TreeFolds {
  // 4.1 Write a foldable instance for Tree
//  implicit val foldable: Foldable[Tree] = ???

  // 4.2 Hard - Write a traverse instance for Tree
  // You can remove the above Foldable instance since Traverse extends Foldable
  implicit val traverse: Traverse[Tree] = new Traverse[Tree] {
    override def traverse[G[_], A, B](fa: Tree[A])(f: A => G[B])(implicit ev: Applicative[G]): G[Tree[B]] =
      fa match {
        case Leaf => ev.pure(Leaf)
        case Node(a, l, r) =>
          val lhs = traverse(l)(f)
          val rhs = traverse(r)(f)

          val lhsRsh = ev.ap[Tree[B], (Tree[B], Tree[B])](ev.map(lhs)(a => (b: Tree[B]) => (a, b)))(rhs)
          val lhsRshA = ev.ap[B, ((Tree[B], Tree[B]), B)](ev.map(lhsRsh)(a => (b: B) => (a, b)))(f(a))
          ev.map(lhsRshA) {
            case ((lhsb, rhsb), b) => Node(b, lhsb, rhsb)
          }
      }

    override def foldLeft[A, B](fa: Tree[A], b: B)(f: (B, A) => B): B =
      fa match {
        case Leaf => b
        case Node(a, l, r) =>
          val lhs  = foldLeft(l, b)(f)
          val curr = f(lhs, a)
          val rhs  = foldLeft(r, curr)(f)
          rhs
      }

    override def foldRight[A, B](fa: Tree[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa match {
        case Leaf => lb
        case Node(a, l, r) =>
          val lhs  = foldRight(l, lb)(f)
          val curr = f(a, lhs)
          val rhs  = foldRight(r, curr)(f)
          rhs
      }
  }

}
