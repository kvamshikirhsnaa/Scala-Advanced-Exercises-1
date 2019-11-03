class Sample(val name: String, var age: Int) {
  def newAge: Int = age
  def newAge_= (newValue: Int): Unit = {
    age = age + 2
  }


}

val s = new Sample("saikrishna", 22)

s.newAge
s.newAge = 1

class Foo {
  var hight: Int = _   // only var is allowed for this type assignment
  private[this] val yo1: String = "Yo"

  def yo2: String = "Hello"
  def greet(name: String): String = s"$yo1 $name"
}

val c = new Foo

c.yo2
c.greet("saikrishna")
c.hight


val c2 = new Foo

c2.yo2
c2.greet("aravind")
c2.hight



trait HeightAndWeight {
  var height: Double
  var weight: Double
}


class TruckLoad extends HeightAndWeight {
  import scala.collection.mutable
  private[this] var propsMap = mutable.Map.empty[String, Double]

  def height: Double = propsMap.getOrElse("height", 0.0)
  def height_= (ht: Double) = propsMap("height") = ht

  def weight: Double = propsMap.getOrElse("weight", 0.0)
  def weight_= (wt: Double) = propsMap("weight") = wt
}

val truck = new TruckLoad

truck.height
truck.weight

truck.height = 5
truck.height

truck.weight = 100
truck.weight


class Test {
  private[this] var _bar: Double = _

  def bar: Double = _bar

  def bar_= (d: Double) = _bar = d * 2
}

val t = new Test
t.bar
t.bar = 2.0
t.bar


object Test {
  private[this] var _bar: Double = _

  def bar: Double = _bar

  def bar_= (d: Double) = _bar = d * 2
}

Test.bar
Test.bar = 3.0
Test.bar


class Test2 {
  private[this] val _bar: Double = 0.0

  def bar: Double = _bar

  def bar_= (d: Double) = println("hello")
}

val t2 = new Test2
t2.bar
t2.bar = 2.0
t2.bar


class Test3 {
  val a = 15
  def weight: Double = 40 - a
  def weight_= (x: Int) = x * 2
}

val t3 = new Test3
t3.weight
t3.weight = 20

// here weight_= is setter method when we assign
// any value by calling it will internally calls
// weight method only, this is useful for if we
//dealing with mutable values if the value of weight
// is changing frequently then make a private[this]
// var and write logic
// we should try to avoid using mutable in program




