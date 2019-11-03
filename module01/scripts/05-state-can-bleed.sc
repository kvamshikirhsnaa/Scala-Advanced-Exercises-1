class Person(val name: String, var weight: Double) {
	override def toString: String = s"Person($name, $weight)"
}

val alice = new Person("Alice", 123)
val bob = new Person("Bob", 124)

val all = Seq(alice, bob)

def heaviestPerson(people: Seq[Person]): Person =
	people.maxBy(x => x.weight)


heaviestPerson(all)

bob.weight = 122

heaviestPerson(all)


// here weight is mutable so when we calling same method with same
// arguments output is changing
// as per functional programming when we call a method how many times
// with same arguments the result should not change
// that is we have to work with mostly using vals

