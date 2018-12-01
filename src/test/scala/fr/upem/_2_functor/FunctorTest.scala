package fr.upem._2_functor

import cats.Functor
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
    val isPair: Int => Boolean = _ % 2 == 0

    val mappedFun = Functor[List[Int] => ?].map(lastElem)(isPair)
    mappedFun(List(5, 5, 4)) should be(true)
    mappedFun(List(5, 5, 5)) should be(false)
  }

  it should "be available for function with option" in {
    // 2.3 Make the code safe for empty lists
    implicit val ev: Functor[List[Int] => ?] = ???

    val lastElem: List[Int] => Option[Int] = list => list.reverse.headOption
    val isPair: Int => Boolean = _ % 2 == 0
    val isPairLifted: Option[Int] => Boolean = ???

    val mappedFun = Functor[List[Int] => ?].map(lastElem)(isPairLifted)
    mappedFun(List(5, 5, 4)) should be(true)
    mappedFun(List(5, 5, 5)) should be(false)
    mappedFun(List()) should be(false)
  }

}
