// Invariant: allows same type only
class Dog1 {
  override def toString: String = s"this is dog"
}

class Puppy1 extends Dog1

class Animal1[T](val animal: T) {
  override def toString: String = s"hey ${animal}"
}

class AnimalCarer1(val dog: Animal1[Dog1]) {
  override def toString: String = s"hello ${dog}"
}



val dog1 = new Dog1

val puppy1 = new Puppy1

val animal1: Animal1[Dog1] = new Animal1[Dog1](dog1)
val animal2: Animal1[Puppy1] = new Animal1[Puppy1](puppy1)
val animal3: Animal1[Dog1] = new Animal1[Dog1](puppy1)

//val animal4 = new Animal1[Puppy1](dog1)

val animalCarer1 = new AnimalCarer1(animal1)
//val animalCarer2 = new AnimalCarer1(animal2)
val animalCarer3 = new AnimalCarer1(animal3)

// Covaraint: Allows sameType and subType parameters



class NewAnimalCarer[T](val dog: Animal1[T]) {
  override def toString: String = s"hello ${dog}"
}

val newAnimal1 = new NewAnimalCarer(new Animal1(dog1))

val newAnimal2 = new NewAnimalCarer[Dog1](new Animal1(puppy1))

val newAnimal3 = new NewAnimalCarer[Puppy1](new Animal1(puppy1))



