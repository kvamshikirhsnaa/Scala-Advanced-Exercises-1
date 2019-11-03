val nums: List[Int] = List(1,2,3)
val numsAny: List[Any] = nums

// the above are covarient


val setNums: Set[Int] = Set(1,2,3)
//val setAny: Set[Any] = setNums

// the above are invarient


// while defining generics types for
// class or case class or trait
// if we simply put generic type [T] then
// it is invarient
// it means only those type support


// if we put [+T] it's covarient
// it's support those types and child types

// if we put [-T] it's contra varient
// it's support those types and parent types


// invarient -> only allows same type
// covarient -> allows same type and child type
// contra-varient -> allows same type and parent type
























