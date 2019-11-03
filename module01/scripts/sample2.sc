
/* Copyright (C) 2010-2017 Escalate Software, LLC. All rights reserved. */
import com.google.common.cache.{CacheLoader, CacheBuilder}
import scala.concurrent._
import duration._
import ExecutionContext.Implicits.global

  /*
   * Retrofit this simple calorie tracker with properties that enforce the following rules:
   * the dailyMax must be larger than 0 and smaller than 5000 calories
   * the currentDaily may only be set to values in a range from -500 to dailyMax + 500
   * See the tests below for the expected behavior and exceptions. When the tests pass, you have a solution.
   */
  class CalorieTracker {
    private[this] var _dailyMax: Int = _
    private[this] var _currentDaily: Int = 0

    def dailyMax = _dailyMax

    def dailyMax_= (x: Int) = {
      require(x > 0 && x < 5000, "sachipotavu ra")
      _dailyMax = x
    }

    def currentDaily = _currentDaily

    def currentDaily_= (y: Int) = {
      require(y > -500 && y < (_dailyMax + 500), "May not deviate from daily range by 500 or more calories")
      _currentDaily = y
    }

    def eat(cals: Int) { currentDaily -= cals }
    def exercise(cals: Int) { currentDaily += cals }

  }


val tracker = new CalorieTracker
tracker.dailyMax = 2500 // should be fine
tracker.currentDaily = 1200 // should also be fine
tracker.dailyMax  //should be (2500)
tracker.currentDaily // should be (1200)



val tracker2 = new CalorieTracker
//intercept[IllegalArgumentException] { tracker.dailyMax = 0 }
//  intercept[IllegalArgumentException] { tracker.dailyMax = -100 }
//  intercept[IllegalArgumentException] { tracker.dailyMax = 5000 } // should allow up to 4999 calories only


val tracker3 = new CalorieTracker
val tracker4 = new CalorieTracker

tracker.dailyMax = 2000
tracker2.dailyMax = 3000

tracker.currentDaily = 1500
tracker2.currentDaily = 1500

tracker.dailyMax  // should be (2000)
tracker2.dailyMax   // should be (3000)
tracker.currentDaily   //should be (1500)
tracker2.currentDaily    //should be (1500)

    // now lets add some earned calories
//intercept[IllegalArgumentException] { tracker1.currentDaily += 1000 }
//  tracker2.currentDaily += 1000

  tracker.currentDaily   // should be (1500)
  tracker2.currentDaily   // should be (2500)


val tracker1 = new CalorieTracker
val tracker6 = new CalorieTracker

tracker1.dailyMax = 2000
tracker6.dailyMax = 3000

tracker1.currentDaily = 1500
tracker6.currentDaily = 1500

tracker1.dailyMax    // should be (2000)
tracker6.dailyMax    // should be (3000)
tracker1.currentDaily   // should be (1500)
tracker6.currentDaily   // should be (1500)

// now lets add some earned calories
//    intercept[IllegalArgumentException] { tracker1.exercise(1000) }
//    tracker2.exercise(1000)

tracker1.currentDaily   // should be (1500)
tracker2.currentDaily    //should be (2500)

tracker1.eat(1000)
tracker2.eat(1000)

tracker1.currentDaily   // should be (500)
tracker2.currentDaily   // should be (1500)

//    intercept[IllegalArgumentException] { tracker1.eat(1000) }
//    tracker2.eat(1000)

tracker1.currentDaily   //  should be (500)
tracker2.currentDaily   // should be (500)

  /*
   * the following is a simple but slow DB that pretends to front for a database. Queries against
   * the database take a second each time it is called (not uncommon in real life) and so you want to
   * provide a cache for common lookups to speed things up.
   * The tests below will help you satisfy the desired API, which will rely on a cache in front of the
   * database that returns a future containing the results (rather than just the results - so we get the
   * least blocking possible). The cache must also ensure that any given lookup is only done once, never
   * more than once. Uncomment the tests below this implementation of SlowDB, and then write the DBCache
   * object to satisfy all test conditions using Guava's CacheBuilder and CacheLoader
   */

case class RecipesForFood(food: String)

object SlowDB {
    val foods = Map("eggs" -> List("ommelette", "french toast", "poached eggs"),
      "bread" -> List("beans on toast", "french toast", "marmite sandwich"),
      "peanuts" -> List("peanut butter", "trail mix", "pad thai"),
      "ground beef" -> List("chili", "meat sauce", "swedish meatballs"))

    def findRecipes(food: String): List[String] = {
      Thread.sleep(1000); foods.getOrElse(food, Nil)
    }
}

object DBCache {
    val recipeCache = CacheBuilder.newBuilder().
      build {
        new CacheLoader[String, Future[List[String]]] {
          def load(key: String) = Future(SlowDB.findRecipes(key))
        }
      }
    def recipesForFood(food: String): Future[List[String]] = recipeCache.get(food)  // implement convenient accessor
}



val startTime = System.currentTimeMillis

val eggRecipes: Future[List[String]] = DBCache.recipesForFood("eggs")
val eggRecipes2: Future[List[String]] = DBCache.recipesForFood("eggs")
val eggRecipes3: Future[List[String]] = DBCache.recipesForFood("eggs")

println("Back in %d millis".format(System.currentTimeMillis - startTime))
// if ((System.currentTimeMillis - startTime) > 1000) fail("Took too long to do lookup from DB")

// now check that the futures are all the same for the egg recipes (should be the same instance)
//assert(eggRecipes eq eggRecipes2)
//assert(eggRecipes eq eggRecipes3)

// and getting the lists from all three of the egg recipes should take no more than around 1 second
Await.result(eggRecipes, 10.seconds) //should be (List("ommelette", "french toast", "poached eggs"))
Await.result(eggRecipes2, 10.seconds) //should be (List("ommelette", "french toast", "poached eggs"))
Await.result(eggRecipes3, 10.seconds) //should be (List("ommelette", "french toast", "poached eggs"))

println("Total time %d millis".format(System.currentTimeMillis - startTime))
// if ((System.currentTimeMillis - startTime) > 1500) //fail("Took too long, must be doing more than one DB lookup for eggs")


