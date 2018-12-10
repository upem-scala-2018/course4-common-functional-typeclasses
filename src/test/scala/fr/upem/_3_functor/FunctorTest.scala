package fr.upem._3_functor

import cats.laws.discipline.{ApplicativeTests, FunctorTests}
import cats.{Applicative, Functor}
import fr.upem.CheckLaws
import fr.upem._3_functor.ContentType.{Json, Xml}
import fr.upem._3_functor.Tree.{Leaf, Node}
import org.scalatest.{FlatSpec, Matchers}

class FunctorTest extends FlatSpec with Matchers with CheckLaws {

  "Functor" should "be available for List" in {
    // 3.1 Import the right typeclass
    implicit val ev: Functor[List] = ???

    Functor[List].map(List(1, 2, 3))(i => i + 1) should equal(List(2, 3, 4))
  }

  it should "be available for function" in {
    // 3.2 Import the right typeclass
    implicit val ev: Functor[List[Int] => ?] = ???

    val lastElem: List[Int] => Int = list => list.reverse.head
    val isPair: Int => Boolean     = _ % 2 == 0

    val mappedFun = Functor[List[Int] => ?].map(lastElem)(isPair)
    mappedFun(List(5, 5, 4)) should be(true)
    mappedFun(List(5, 5, 5)) should be(false)
  }

  it should "be available for function with option" in {
    // 3.3 Make the code safe for empty lists
    implicit val ev: Functor[List[Int] => ?] = ???

    val lastElem: List[Int] => Option[Int]   = list => list.reverse.headOption
    val isPair: Int => Boolean               = _ % 2 == 0
    val isPairLifted: Option[Int] => Boolean = _.exists(isPair)

    val mappedFun = Functor[List[Int] => ?].map(lastElem)(isPairLifted)
    mappedFun(List(5, 5, 4)) should be(true)
    mappedFun(List(5, 5, 5)) should be(false)
    mappedFun(List()) should be(false)
  }

  it should "be available for HttpHeader" in {
    // 3.4 Use the HttpHeader Functor
    val mappedHeader = Functor[HttpHeader].map(HttpHeader("content-type", Json))(_ => Xml)

    mappedHeader should equal(HttpHeader("content-type", Xml))
  }

  {
    import cats.instances.int._
    checkAll("Functor[HttpHeader]", FunctorTests[HttpHeader].functor[Int, String, Int])
  }

  "FunctorSyntax" should "be available for HttpHeader" in {
    // 3.5 Import the functor syntax and un-comment following line
    // HttpHeader("content-type", Json).map(_ => Xml) should equal(HttpHeader("content-type", Xml))
    ???
  }

  it should "be available for Tree" in {
    // 3.6 Implement tree functor and use functor syntax
    val empty: Tree[Int] = Leaf
    Functor[Tree].map(empty)(i => i * 2) should equal(Leaf)
  }

}
