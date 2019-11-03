abstract class Food {
  val name: String
}

abstract class Fruit extends Food

case class Apple(name: String) extends Fruit
case class Orange(name: String) extends Fruit
case class Banana(name: String) extends Fruit

abstract class Cereal extends Food

case class Granola(name: String) extends Cereal
case class Muesli(name: String) extends Cereal

trait Describe {
  val description: String
}

case class Taste(description: String) extends Describe
case class Texture(description: String) extends Describe


val juicyFruit: Fruit => Taste = fruit => Taste(s"this ${fruit.name} is nice and juicy")

val sweetApple: Apple => Texture = apple => Texture(s"this ${apple.name} is sweet")

val bumpyOrange: Orange => Texture = orange => Texture(s"this ${orange.name} is bumpy")

val tastyApple: Apple => Describe = apple => new Describe {
  override val description: String = s"this ${apple.name} is yummy"
}

juicyFruit(Apple("fuji"))
juicyFruit(Orange("santra"))
juicyFruit(new Fruit {
  override val name: String = "mango"
})

sweetApple(Apple("fuji"))
//sweetApple(Orange("santra"))
//sweetApple(new Fruit {
//  override val name: String = "mango"
//})

bumpyOrange(Orange("santra"))
//bumpyOrange(Apple("fuji"))
//bumpyOrange(new Fruit {
//  override val name: String = "mango"
//})

tastyApple(Apple("fuji"))
//tastyApple(Orange("santra"))


def describeAnApple(fn: Apple => Describe) = fn(Apple("fuji"))

describeAnApple(juicyFruit)
describeAnApple(sweetApple)
// describeAnApple(bumpyOrange)
describeAnApple(tastyApple)

def describeAFruit(fn: Fruit => Describe) = fn(Apple("fuji"))

describeAFruit(juicyFruit)
//describeAFruit(bumpyOrange)
//describeAFruit(sweetApple)
//describeAFruit(tastyApple)


def describeAFruit2(fn: Fruit => Describe) = {
  fn(new Fruit {
    override val name: String = "mango"
  })
}

describeAFruit2(juicyFruit)
//describeAFruit2(sweetApple)  // only same type or super type allows for input
//describeAFruit2(bumpyOrange)  // Fruit accepts Fruit type or super type of Fruit
//describeAFruit2(tastyApple)



//def describeAFruit3(fn: Orange => Describe) = fn(Apple("fuji"))
// the above function is wrong cuz it's expecting Orange input type
// but in function body we are passing Apple Type so it throws error


// Note: here in above all functions the input type allows same type or super type
// and output allows same type or sub type


