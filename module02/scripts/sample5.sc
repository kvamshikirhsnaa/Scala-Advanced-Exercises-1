case class Box[+A](value: A) {

  //def set(a: A): Box[A] = Box(a)

}

//You are declaring class Box as covariant in its type parameter A.
// This means that for any type X extending A (i.e. X <: A), Box[X] can be seen as a Box[A].

//  To give a clear example, let's consider the Animal type:

abstract class Animal

class Cat extends Animal
class Dog extends Animal

// If you define Dog <: Animal and Cat <: Animal,
// then both Box[Dog] and Box[Cat] can be seen as Box[Animal] and
// you can e.g. create a single collection containing both types and
// preserve the Box[Animal] type.
//
// Although this property can be very handy in some cases,
// it also imposes constraints on the operations you can make
// available on Box. This is why the compiler doesn't allow you to
// define def set.

//If you allow defining

//then I the following code is valid:

val cat = new Cat
val catBox = Box[Cat](cat)
val animalBox: Box[Animal] = catBox // valid because `Cat <: Animal`
val dog = new Dog
//animalBox.set(dog)
//catBox.set(dog) // This is non-sensical!

//The last line is obviously a problem because catBox will now contain a Dog!
//  The arguments of a method appear in what is called "contravariant position",
//which is the opposite of covariance. Indeed,
//if you define Box[-A], then Cat <: Animal implies Box[Cat] >: Box[Animal]
//(Box[Cat] is a supertype of Box[Animal]). For our example,
//this is of course non-sensical.
//
//  One solution to your problem is to make the
//Box class immutable (i.e. to not provide any way to
//  change the content of a Box), and instead use the apply
//method define in your case class companion to create new boxes.
//If you need to, you can also define set locally and not expose
//it anywhere outside Box by declaring it as private[this].
//The compiler will allow this because the private[this] guarantees
//that the last line of our faulty example will not compile since the
//set method is completely invisible outside of a specific instance of Box.




//Others have already given an answer why the code doesn't compile, but they haven't given a solution on how to make the code compile:
//
case class Box2[+A](v: A) {
  def set[B >: A](a: B) = Box2(a)
}
//defined class Box
trait Animal2
case class Cat2() extends Animal2
case class Dog2() extends Animal2
//defined trait Animal
//defined class Cat

val animal2 = new Animal2 {}
val cat2 = Cat2()
val dog2 = Dog2()

val catBox1 = Box2(cat2)
val catBox2 = Box2[Cat2](cat2)
// the above both are same return type Box2[Cat2]

val dogBox1 = Box2(dog2)
val dogBox2 = Box2[Dog2](dog2)
// the above both are same return type Box2[Dog2]

val animalBox1 = Box2[Animal2](cat2)
val animalBox2 = Box2[Animal2](dog2)
val animalBox3 = Box2[Animal2](new Animal2 {})

catBox1.set(cat2)
catBox1.set(dog2)
catBox1.set(animalBox1)
catBox1.set(animalBox2)
catBox1.set(animalBox3)


dogBox1.set(cat2)
dogBox1.set(dog2)
dogBox1.set(animalBox1)
dogBox1.set(animalBox2)
dogBox1.set(animalBox3)


animalBox1.set(cat2)
animalBox1.set(dog2)
animalBox1.set(animalBox1)
animalBox1.set(animalBox2)
animalBox1.set(animalBox3)

animalBox3.set(cat2)
animalBox3.set(dog2)
animalBox3.set(animalBox1)
animalBox3.set(animalBox2)
animalBox3.set(animalBox3)









