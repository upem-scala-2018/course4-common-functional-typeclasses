package fr.upem._3_functor

import cats.Functor

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