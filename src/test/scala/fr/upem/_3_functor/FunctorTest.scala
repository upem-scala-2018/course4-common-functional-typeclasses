package fr.upem._3_functor

import cats.{Applicative, Functor, Monad}
import fr.upem._3_functor.ContentType.{Json, Xml}
import fr.upem._3_functor.Tree.{Leaf, Node}
import org.scalatest.{FlatSpec, Matchers}

class FunctorTest extends FlatSpec with Matchers {

  "Functor" should "be available for List" in {
    // 2.1 Import the right typeclass
    implicit val ev: Functor[List] = ???

    Functor[List].map(List(1, 2, 3))(i => i + 1) should equal(List(2, 3, 4))
  }

  it should "be available for function" in {
    // 2.2 Import the right typeclass
    implicit val ev: Functor[List[Int] => ?] = ???

    val lastElem: List[Int] => Int = list => list.reverse.head
    val isPair: Int => Boolean     = _ % 2 == 0

    val mappedFun = Functor[List[Int] => ?].map(lastElem)(isPair)
    mappedFun(List(5, 5, 4)) should be(true)
    mappedFun(List(5, 5, 5)) should be(false)
  }

  it should "be available for function with option" in {
    // 2.3 Make the code safe for empty lists
    implicit val ev: Functor[List[Int] => ?] = ???

    val lastElem: List[Int] => Option[Int]   = list => list.reverse.headOption
    val isPair: Int => Boolean               = _ % 2 == 0
    val isPairLifted: Option[Int] => Boolean = ???

    val mappedFun = Functor[List[Int] => ?].map(lastElem)(isPairLifted)
    mappedFun(List(5, 5, 4)) should be(true)
    mappedFun(List(5, 5, 5)) should be(false)
    mappedFun(List()) should be(false)
  }

  it should "be available for HttpHeader" in {
    // 2.4 Use the HttpHeader Functor
    val mappedHeader = Functor[HttpHeader].map(HttpHeader("content-type", Json))(_ => Xml)

    mappedHeader should equal(HttpHeader("content-type", Xml))
  }

  "FunctorSyntax" should "be available for HttpHeader" in {
    // 2.5 Import the functor syntax and un-comment following line
    // HttpHeader("content-type", Json).map(_ => Xml) should equal(HttpHeader("content-type", Xml))
    ???
  }

  it should "be available for Tree" in {
    // 2.6 Implement tree functor and use functor syntax
    val empty: Tree[Int] = Leaf
    Functor[Tree].map(empty)(i => i * 2) should equal(Leaf)
  }

  // 2.7 Hard - Implement tree applicative
  "Applicative" should "be available for Tree" in {
    val values    = Node[Int](5, Leaf, Leaf)
    val functions = Node[Int => String](Integer.toString, Leaf, Leaf)

    Applicative[Tree].ap(functions)(values) should equal(Node("5", Leaf, Leaf))
  }

  it should "work with empty value trees" in {
    val values    = Leaf
    val functions = Node[Int => Int](_ + 1, Leaf, Leaf)

    Applicative[Tree].ap(functions)(values) should equal(Leaf)
  }

  it should "work with empty function trees" in {
    val values    = Node[Int](5, Leaf, Leaf)
    val functions = Leaf

    Applicative[Tree].ap(functions)(values) should equal(Leaf)
  }

  it should "work with complex trees" in {
    val values    = Node[Int](5, Leaf, Node(1, Node(2, Leaf, Leaf), Leaf))
    val functions = Node[Int => Int](_ * 2, Leaf, Node(_ + 10, Leaf, Node(_ + 2, Leaf, Leaf)))

    Applicative[Tree].ap(functions)(values) should equal(Node[Int](10, Leaf, Node(11, Leaf, Leaf)))
  }

}
