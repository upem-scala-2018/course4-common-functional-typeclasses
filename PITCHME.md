# Cours 4: Typeclasses fonctionelles répandues

---

## Origines

- Premier papier en 1988
- Apparues en Haskell
- Philipp Wadler and Stephen Blott
- Puissant outil de polymorphisme ad-hoc
- Permet la programmation par effet

---

## Polymorphismes

- polymorphisme paramétré: Définition **unique** pour un type paramétré
- polymorphisme ad hoc: Définitions **multiples**, une par type éligible
- polymorphisme par sous-typage: Définitions **multiples**, une par type sous-type

---

## Première hiérarchie

- Semigroup
- Monoïde

---

### Semigroup

```scala
trait Semigroup[A] {
  def combine(x: A, y: A): A
}
```

Exemples de semigroup ?

---

### Exemples de semigroupes

- String
- Int
- Option[A] (A: Semigroup)
- List[A] (A: Semigroup)
- Map[K, V] (V: Semigroup)

- Function1[A, B] (B: Semigroup)

---

### Utilisation de semigroupes

```scala
def reduce[A](l: List[A])(implicit S: Semigroup[A]) =
    l.reduce(S.combine)
```

---

### Semigroupes - Propriétés 

- Composition interne
∀ x ∈ *E*, y ∈ *E*, x + y ∈ *E*

- Associativité
(x + y) + z = x + (y + z)

---

### Monoïde

```scala
trait Monoid[A] {
  def empty: A
  def combine(x: A, y: A): A
}
```

---

### Exemples de Monoïdes

- String
- Int
- Option[A] (A: Monoid)
- List[A]
- Map[K, V] (V: Monoid)

- Function1[A, B] (B: Monoid)

---

### Contre-exemples

- NonEmptyList[A]
- NonEmptyX...
- Non-empty strings

---

### Utilisation de monoïdes

```scala
def fold[A](l: List[A])(implicit M: Monoid[A]) =
    l.fold(M.empty)(M.combine)
```

---

### Aller plus loin

- Band[A]
- CommutativeSemigroup[A]
- Semilattice[A]
- ...

---

### Monoïdes - Propriétés

- Associativité
(x + y) + z = x + (y + z)

- Identité (droite et gauche)
x + *e* = *e* + x = x

---

## Deuxième hiérarchie

- Foncteur[F[_]]
- Applicative[F[_]]
- Monade[F[_]]

---

### Foncteur

```scala
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
```

---

### Foncteur - Illustration

![Illustration foncteur](assets/functor.png)


---

### Exemples de foncteurs

- List[_]
- Option[_]
- Either[A, _]
- Id[_]
- Const[K, _]

---

### Foncteurs - Propriétés

- Composition
fa.map(g.f) = fa.map(g).map(f)

- Identité
fa.map(identity) = fa

---

### Utilisation de foncteurs

```scala
Functor[Option].tuple(Some(3), 4) // Some((3, 4))
```

---

### Composition de foncteurs

Les foncteurs se composent

```scala
Functor[Future].compose(Functor[List]).compose(Functor[Option])
```

---

### Applicative

```scala
trait Applicative[F[_]] {
  def pure[A](x: A): F[A]
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}
```

---

### Applicative - Illustration

![Illustration applicative](assets/applicative.png)

---

### Exemples d'applicatives

- List[_]
- Option[_]
- Either[A, _]
- Id[_]

---

### Applicatives - Propriétés

- Identité
pure(identity).ap(x) = x

- Homomorphisme
pure(f).ap(pure(x)) = pure(f(x))

---

### Utilisation d'applicative

```scala
def pair[F[_], A, B](f1: F[A], f2: F[B])(implicit F: Applicative[F]): F[(A, B)] =
    F.ap[B, (A, B)](F.map(f1)(a => b => (a, b)))(f2)
```

---

### Contre-exemples (Foncteur sans Applicative)

- Const[A, _] when A is only a semigroup

---

### Monad

```scala
trait Monad[F[_]] {
  def pure[A](x: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}
```

---

### Monad - Illustration

![Illustration monad](assets/monad.png)

---

### Propriétés

- Identité gauche
pure(a).flatMap(f) = f(a)

- Identité droite
ma.flatMap(pure) = ma

- Associativité
ma.flatMap(f).flatMap(g) = ma.flatMap(a => f(a).flatMap(g))

---

### Utilisation de monades

```scala
for {
  f <- fib(n) //Option[Int]
  p <- isPair(n)
} yield p
```

---

### Contre-exemples (Applicative sans Monade)

- Validated[E, _]

---

### Aller plus loin

- Contravariant(Functor)[F[_]]
- Bifunctor[F[_, _]]
- MonadError[F[_]]
- CommutativeApplicative[F[_]]

---

## Effectful programming

- La programmation fonctionelle interdit les effets de bords
- Un programme sans effets de bords est inutile
- La programmation fonctionelle est elle inutile ?

---

### Description d'un programme

- De la donnée en tant que programme
- Les effets sont décrits au travers de Monades
- Les Monades sont de la donnée
- Un runtime interprete la description (donnée) du programme

---

### Exemple - program as data

```scala
sealed trait Program[A]
case class ReadLn[A](next: String => Program[A])      extends Program[A]
case class PrintLn[A](line: String, next: Program[A]) extends Program[A]
case class Exit[A](a: A)                              extends Program[A]
```

Interprétation du programme par un runtime, *démo*

---

## Troisième hiérarchie

- Foldable[_]
- Traverse[_]

---

## Autres typeclasses

- Eq
- Show
- Hash
- State
- Write

---

## Typeclasses en Scala

-- ![cats](https://typelevel.org/cats/)
-- ![scalaz](https://github.com/scalaz/scalaz)

---

## Typeclasses dans cats

![Illustration](assets/cats.svg)

---

### Instances de typeclasses

```scala
import cats.instances.list._
import cats.instances.either._
import cats.instances.option._
import cats.instances.string._
// ...
```

---

### Syntaxes de typeclasses

```scala
import cats.syntax.functor._
import cats.syntax.monad._
import cats.syntax.applicative._
import cats.syntax.traverse._
// ...
```

---

### D'autres outils

```scala
import cats.syntax.either._
import cats.data.Validated
import cats.data.Eval
// ...
```

---

# Bibliographie

- How to make ad􏰀hoc p olymorphism less ad hoc
- Monads for functional programming