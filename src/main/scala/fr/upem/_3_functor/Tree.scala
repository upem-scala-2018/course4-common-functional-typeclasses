package fr.upem._3_functor

import cats.{Applicative, Functor}

sealed trait Tree[+A]

object Tree {
  case object Leaf                                 extends Tree[Nothing]
  case class Node[A](a: A, l: Tree[A], r: Tree[A]) extends Tree[A]

  // 2.6 Implement a Functor instance for Tree
  implicit val functor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =
      fa match {
        case Leaf          => Leaf
        case Node(a, l, r) => Node(f(a), map(l)(f), map(r)(f))
      }
  }

  // 2.7 Hard - Implement an Applicative instance for Tree
  implicit val applicative: Applicative[Tree] = new Applicative[Tree] {
    override def pure[A](x: A): Tree[A] = Node(x, Leaf, Leaf)
    override def ap[A, B](ff: Tree[A => B])(fa: Tree[A]): Tree[B] =
      (fa, ff) match {
        case (Leaf, _)                          => Leaf
        case (_, Leaf)                          => Leaf
        case (Node(a, la, ra), Node(f, lf, rf)) => Node(f(a), ap(lf)(la), ap(rf)(ra))
      }
  }

  // 2.8 Hard - Implement an Applicative instance for Tree

}
