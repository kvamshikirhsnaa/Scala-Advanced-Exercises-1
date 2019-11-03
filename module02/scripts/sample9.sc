class Animal
class Dog extends Animal
class Puppy(val name: String) extends Dog
class Puppy1 extends Animal
class Puppy2 extends Puppy("tommy")

abstract class Bird

case class Parrot(name: String) extends Bird

class AnimalCarer{
  def display [T <: Puppy](t: T) = s"hi i am ${t}"
}

val animal = new Animal
val dog = new Dog
val puppy = new Puppy("tommy")
val puppy1 = new Puppy1
val puppy2 = new Puppy2

val parrot1 = Parrot("ramachiluka")

val animalCarer = new AnimalCarer

animalCarer.display(puppy)
animalCarer.display(puppy2)


//animalCarer.display(animal)
//animalCarer.display(puppy1)
//animalCarer.display(dog)
//animalCarer.display(parrot1)