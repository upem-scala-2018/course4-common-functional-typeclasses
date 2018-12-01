package fr.upem

import cats.Applicative
import cats.kernel.{CommutativeMonoid, Monoid, Semigroup}
import cats.implicits._
import cats.syntax.semigroup._

object Sandbox extends App {

  println(Semigroup[List[String]].combine(List(), List()))
  println(Semigroup[Option[String]].combine(None, None))
  println(Semigroup[Map[Int, String]].combine(Map(1 -> "b"), Map(1 -> "c", 2 -> "d")))
  println(Semigroup[Int => String].combine(s => Integer.toString(s) + "1", s => Integer.toString(s) + "2")(55))
  println(Semigroup[Int => String].combine(s => Integer.toString(s) + "1", s => Integer.toString(s) + "2")(55))
  println(Monoid[List[String]].combine(List(), List()))
  println(CommutativeMonoid[Int])

  println(Applicative[Option])
  println(Applicative[Option].ap[Int, (Int, Int)](Applicative[Option].map(Some(3))(b => (a: Int) => (a, b)))(Some(5)))


  def pair[F[_], A](f1: F[A], f2: F[A])(implicit A: Applicative[F]) =
    A.ap[A, (A, A)](A.map(f1)(a => b => (a, b)))(f2)

  // 0000 0000
  // 1010 1100
  // 1010 1100

  def fold[A](l: List[A])(implicit M: Monoid[A]) =
    l.fold(M.empty)(M.combine)

  def reduce[A](l: List[A])(implicit S: Semigroup[A]) =
    l.reduce(S.combine)

  println("Hello world!")

}
