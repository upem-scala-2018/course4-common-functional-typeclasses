package fr.upem

import cats.{Foldable, Id}
import fr.upem._3_functor.Tree
import fr.upem._3_functor.Tree.{Leaf, Node}
import fr.upem._4_folds.TreeFolds._

object Sandbox extends App {

  val F = Foldable[Tree]
  val fa = Node("test", Leaf, Node("2", Leaf, Node("3", Leaf, Leaf)))
  val b = "base"
  val f: (String, String) => String = (s, s2) =>  s + s2 + "c"

  println(F.foldLeft(fa, b)(f))
  println(F.foldM[Id, String, String](fa, b)(f))

  println(List("a", "b", "c").foldRight("s")(_ + _))
  println(List("a", "b", "c").foldLeft("s")(_ + _))

}
