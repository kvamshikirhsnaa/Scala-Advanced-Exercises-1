abstract class Type[-T] {
  def typename: Unit
}

class SuperType extends Type[AnyVal] {
  override def typename: Unit = println("super type")
}

class SubType extends Type[Int] {
  override def typename: Unit = println("sub type")
}

class TypeCarer{
  def display(t: Type[Int]) = t.typename
}

val superType = new SuperType

val subType = new SubType

val typeCarer = new TypeCarer

typeCarer.display(subType)
typeCarer.display(superType)






abstract class Animal {
  val name: String
}
case class Puppy(name: String) extends Animal
class SmallPuppy extends Puppy("chotu")
case class Pig(name: String) extends Animal

abstract class Bird {
  val name: String
}

case class Parrot(name: String) extends Bird

class AnimalCarer{
  def display [T <: Puppy](t: T) = s"this is ${t}"
}

val animal = new Animal{
  override val name = "Loin"
}

val sp = new SmallPuppy
val puppy = Puppy("motu")
val pig = Pig("pandi")

val bird = new Bird {
  override val name: String = "eagle"
}
val parrot = Parrot("rama chiluka")

val animalCarer = new AnimalCarer

//animalCarer.display(animal)
animalCarer.display(puppy)
animalCarer.display(sp)
//animalCarer.display(pig)
//animalCarer.display(bird)
//animalCarer.display(parrot)








