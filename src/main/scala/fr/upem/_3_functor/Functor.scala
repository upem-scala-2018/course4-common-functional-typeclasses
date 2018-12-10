package fr.upem._3_functor

import cats.{Applicative, Functor}

sealed trait ContentType

object ContentType {
  case object Json extends ContentType
  case object Xml  extends ContentType
}

case class HttpHeader[A](name: String, value: A)

object HttpHeader {
  // 3.4 Create a functor for HttpHeader
  lazy implicit val functor: Functor[HttpHeader] = new Functor[HttpHeader] {
    override def map[A, B](fa: HttpHeader[A])(f: A => B): HttpHeader[B] =
      fa.copy(value = f(fa.value))
  }

  // 3.5 Can you implement an Applicative and a Monad for the HttpHeader
  // What would be the semantics of pure() and flatMap() ?

}

sealed trait Tree[+A]

object Tree {
  case object Leaf                                 extends Tree[Nothing]
  case class Node[A](a: A, l: Tree[A], r: Tree[A]) extends Tree[A]

  // 3.6 Implement a Functor instance for Tree
  implicit val functor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =
      fa match {
        case Leaf          => Leaf
        case Node(a, l, r) => Node(f(a), map(l)(f), map(r)(f))
      }
  }


}
