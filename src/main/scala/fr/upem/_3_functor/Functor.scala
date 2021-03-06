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
  lazy implicit val functor: Functor[HttpHeader] = ???

  // 3.5 Can you implement an Applicative and a Monad for the HttpHeader
  // What would be the semantics of pure() and flatMap() ?

}

sealed trait Tree[+A]

object Tree {
  case object Leaf                                 extends Tree[Nothing]
  case class Node[A](a: A, l: Tree[A], r: Tree[A]) extends Tree[A]

  // 3.6 Implement a Functor instance for Tree
  lazy implicit val functor: Functor[Tree] = ???

}
