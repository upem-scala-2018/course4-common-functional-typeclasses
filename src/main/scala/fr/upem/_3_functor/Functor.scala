package fr.upem._3_functor

import cats.{Applicative, Functor, Monad}

sealed trait ContentType

object ContentType {
  case object Json extends ContentType
  case object Xml  extends ContentType
}
case class HttpHeader[A](name: String, value: A)

object HttpHeader {
  // 2.4 Create a functor for HttpHeader on the value side
  implicit val functor: Functor[HttpHeader] = ???
  // 2.5 Can you implement an Applicative and a Monad for the HttpHeader
  // What would be the semantics of pure() and flatMap() ?

}

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

  // 2.8 Hard
  implicit val monad: Monad[Tree] = new Monad[Tree] {
    override def pure[A](x: A): Tree[A] = Node(x, Leaf, Leaf)

    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = ???

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = ???
  }

}
