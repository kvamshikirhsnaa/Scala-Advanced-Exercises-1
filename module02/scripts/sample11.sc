trait Test { outer =>
  def send(item: String): String
  def anotherTest(other: Test): Test = {
    item: String => outer.send(item) + " and then"// + other.send(item)
  }
}

class Test2 extends Test {
  def send(item: String) = s"hi i am $item"
}

val t = new Test2

t.send("vamshikrishna")

val t2 = new Test2

t2.send("saikrishna")

t.anotherTest(t2)


abstract class Food {
  val name: String
}

abstract class Fruit extends Food

case class Apple(name: String) extends Fruit
case class Banana(name: String) extends Fruit

val fuji = Apple("Fuji")
val chiqita = Banana("Chiqita")

abstract class Animal {
  val name: String
}

case class Wild(name: String) extends Animal

val tiger = Wild("Tiger")

trait Sink[-T] { outer =>
  def send(item: T): String

  def andThenSink[U <: T](other: Sink[U]): Sink[U] = {
    (item: U) => outer.send(item) + " and then " + other.send(item)
  }
}


val s1 = new Sink[Fruit] {
  def send(item: Fruit) = s"yummy bowl of ${item.name}s"
}

val s2 = new Sink[Apple] {
  def send(item: Apple) = s"yummy bowl of ${item.name}s"
}

val s3 = new Sink[Banana] {
  def send(item: Banana) = s"yummy bowl of ${item.name}s"
}


s1.send(fuji)
s1.send(chiqita)

s2.send(fuji)
//s2.send(chiqita)

s3.send(chiqita)
//s3.send(fuji)

//s1.send(tiger)
//s2.send(tiger)
//s3.send(tiger)

s1.andThenSink(s1)
s1.andThenSink(s2)
s1.andThenSink(s3)

s2.andThenSink(s2)
s2.andThenSink(s1)
s2.andThenSink(s3)

s3.andThenSink(s3)
s3.andThenSink(s1)
s3.andThenSink(s2)


s1.andThenSink[Fruit](s1)
s1.andThenSink[Apple](s2)
s1.andThenSink[Banana](s3)

s2.andThenSink[Apple](s2)
s2.andThenSink[Apple](s1)

//s2.andThenSink[Banana](s1)
//s2.andThenSink[Banana](s3)

s3.andThenSink[Banana](s3)
s3.andThenSink[Banana](s1)
//s3.andThenSink[Fruit](s1)
//s3.andThenSink[Banana](s1)
//s3.andThenSink[Apple](s1)
//s3.andThenSink[Apple](s2)




