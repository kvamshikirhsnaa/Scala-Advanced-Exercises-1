import com.google.common.cache.{CacheBuilder, CacheLoader}
import scala.concurrent._
import duration._
import ExecutionContext.Implicits.global


case class RecipesForFood(food: String)

object SlowDB {
  val foods = Map("eggs" -> List("ommelette", "french toast", "poached eggs"),
  "bread" -> List("beans on toast", "french toast", "marmite sandwich"),
  "peanuts" -> List("peanut butter", "trail mix", "pad thai"),
  "chicken" -> List("chicken biryani", "natu kodi pulusu", "boneless chicken"))

  def findRecipes(food: String): List[String] = {
    Thread.sleep(1000)
    foods.getOrElse(food, Nil)
  }
}

object DBCache {
  val recipeCache = CacheBuilder.newBuilder().
    build {
      new CacheLoader[String, Future[List[String]]] {
        def load(key: String) = Future(SlowDB.findRecipes(key))
      }
    }

  def recipesForFood(food: String): Future[List[String]] = recipeCache.get(food)
}

DBCache.recipesForFood("eggs")

val startTime = System.currentTimeMillis

val eggRecipes: Future[List[String]] = DBCache.recipeCache("eggs")
val eggRecipes2: Future[List[String]] = DBCache.recipeCache("eggs")
val eggRecipes3: Future[List[String]] = DBCache.recipeCache("eggs")

println("Back in %d millis".format(System.currentTimeMillis - startTime))

Await.result(eggRecipes, 10.seconds)
Await.result(eggRecipes2, 10.seconds)
Await.result(eggRecipes3, 10.seconds)

println("Total time %d millis".format(System.currentTimeMillis - startTime))


val startTime2 = System.currentTimeMillis

val chickenRecipes: Future[List[String]] = DBCache.recipeCache("chicken")
val chickenRecipes2: Future[List[String]] = DBCache.recipeCache("chicken")
val chickenRecipes3: Future[List[String]] = DBCache.recipeCache("chicken")

println("Back in %d millis".format(System.currentTimeMillis - startTime2))

Await.ready(chickenRecipes, 10.seconds)
Await.ready(chickenRecipes2, 10.seconds)
Await.ready(chickenRecipes3, 10.seconds)


Await.ready(chickenRecipes, 10.seconds).value
Await.ready(chickenRecipes2, 10.seconds).value
Await.ready(chickenRecipes3, 10.seconds).value


Await.result(chickenRecipes, 10.seconds)
Await.result(chickenRecipes2, 10.seconds)
Await.result(chickenRecipes3, 10.seconds)

println("Total time %d millis".format(System.currentTimeMillis - startTime2))


val eggs = DBCache.recipesForFood("eggs")
val chicken = DBCache.recipesForFood("chicken")
val bread = DBCache.recipesForFood("bread")

Await.ready(bread, 10.second)