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

### Propriétés

---

### Utilisation de monades

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

- Foldable[_]
- Traverse[_]

---

## Autres typeclasses

- Eq
- Show
- State
- Write

---

## Typeclasses en Scala

-- cats
-- scalaz

---

## Typeclasses dans cats

![Illustration](assets/cats.svg)

---

### Instances de typeclasses

---

### Syntaxes de typeclasses

---

# Bibliographie

- How to make ad􏰀hoc p olymorphism less ad hoc
- Monads for functional programming
