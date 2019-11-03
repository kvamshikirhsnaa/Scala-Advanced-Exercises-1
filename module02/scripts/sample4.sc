trait Food {
  val name: String
  override def toString: String = s"Yummy $name"
}

trait Fruit extends Food

case class Apple(name: String) extends Fruit
case class Orange(name: String) extends Fruit

trait Sink[T] {
  def send(item: T): String
}

object AppleSink extends Sink[Apple] {
  def send(item: Apple): String = s"mmm, i really like those ${item.name}s"
}

object OrangeSink extends Sink[Orange] {
  def send(item: Orange): String = s"mmm, i really like those ${item.name}s"
}

object FruitSink extends Sink[Fruit] {
  def send(item: Fruit): String = s"mmm, i really like those ${item.name}s"
}

object AnySink extends Sink[Any] {
  def send(item: Any): String = item.toString
}

def appleSink(item: Sink[Apple]) = {
  item.send(Apple("fuji"))
}

appleSink(AppleSink)
// appleSink(OrangeSink)
// appleSink(FruitSink) // it won't allow cuz expecting AppleType
                        // passing FruitType
                        // if do contra varient using -T it will succeed
                        // cuz Fruit is parent for Apple
// appleSink(AnySink)


def orangeSink(item: Sink[Orange]) = {
  item.send(Orange("Mosambi"))
}

orangeSink(OrangeSink)
//orangeSink(AppleSink)
//orangeSink(FruitSink)
//orangeSink(AnySink)

def fruitSink(item: Sink[Fruit]) = {
  item.send(new Fruit{ val name = "mango"})
}

fruitSink(FruitSink)
//fruitSink(AppleSink)
//fruitSink(OrangeSink)
//fruitSink(AnySink)


def anySink(item: Sink[Any]) = {
  item.send(new Fruit{ val name = "guava"})
}

anySink(AnySink)
//anySink(AppleSink)
//anySink(OrangeSink)
//anySink(FruitSink)



// co-varient

trait Sink2[+T] {
  def send(item: T): String
}

object AppleSink2 extends Sink2[Apple] {
  def send(item: Apple): String = s"mmm, ${item.name} ante naku baga istam"
}

object OrangeSink2 extends Sink2[Orange] {
  def send(item: Orange): String = s"mmm, ${item.name} ante naku baga istam"
}

object FruitSink2 extends Sink2[Fruit] {
  def send(item: Fruit): String = s"mmm, ${item.name} ante naku baga istam"
}

object AnySink2 extends Sink2[Any] {
  def send(item: Any): String = item.toString
}

def appleSink2(item: Sink2[Apple]) = {
  item.send(Apple("fuji"))
}

appleSink2(AppleSink2)
//appleSink2(FruitSink2)
//appleSink2(OrangeSink2)  // here only Orange n Apple both are different types
//ppleSink2(AnySink2)


def orangeSink2(item: Sink2[Orange]) = {
  item.send(Orange("Mosambi"))
}

orangeSink2(OrangeSink2)
//orangeSink2(AppleSink2)
//orangeSink2(FruitSink2)
//orangeSink2(AnySink2)


def fruitSink2(item: Sink2[Fruit]) = {
  item.send(new Fruit{ val name = "mango"})
}

fruitSink2(FruitSink2)
fruitSink2(AppleSink2)
fruitSink2(OrangeSink2)
//fruitSink2(AnySink2)  // co-varient allows only child type

def anySink2(item: Sink2[Any]) = {
  item.send(new Fruit{ val name = "guava"})
}

anySink2(AnySink2)
anySink2(AppleSink2)
anySink2(OrangeSink2)
anySink2(FruitSink2)


OrangeSink2.send(Orange("mosambi"))
//OrangeSink2.send(Apple("mosambi"))
//OrangeSink2.send(new Fruit{ val name = "grapes"})