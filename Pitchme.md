# Cours 4: Typeclasses répandues


---

## Origines

- 1988
- Philipp Wadler and Stephen Blott
- apparues en Haskell


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

### Monoïde

```scala
trait Monoid[A] {
  def empty: A
  def combine(x: A, y: A): A
}
```

---

### Exemples de Monoïdes

Les mêmes que les semigroupes.

---

### Contre-exemples

- NonEmptyList[A]
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

### Exemples de foncteurs

- List
- Option
- Id
- Either[A, _]

---

### Applicative

```scala
trait Applicative[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def pure[A](x: A): F[A]
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}
```

---

### Exemples d'applicatives

- 

---

### Utilisation d'applicative

```scala
def pair[F[_], A](f1: F[A], f2: F[A])(implicit A: Applicative[F]) =
    A.ap[A, (A, A)](A.map(f1)(a => b => (a, b)))(f2)
```

---

### Contre-exemples (Foncteur sans Applicative)


---

### Aller plus loin

- Contravariant(Functor)[F[_]]
- Bifunctor[F[_, _]]
- MonadError[F[_]]
- CommutativeApplicative[F[_]]

---

## Troisième hiérarchie

- Foldable
- Traverse

---

## Autres typeclasses

- Eq
- Show

---

## Typeclasses dans cats

![Illustration](assets/cats.svg)

---

Bibliographie

- How to make ad􏰀hoc p olymorphism less ad hoc
- Monads for functional programming
