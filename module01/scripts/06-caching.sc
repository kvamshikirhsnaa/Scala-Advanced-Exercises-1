def fakeWeatherLookup(wxCode: String) = {
	Thread.sleep(2000)
	wxCode.toList.map(_.toInt).sum / 10.0
}

fakeWeatherLookup("PDX")

fakeWeatherLookup("SFO")

fakeWeatherLookup("PDX")

import com.google.common.cache.{CacheLoader, CacheBuilder}
import scala.concurrent._
import duration._
import ExecutionContext.Implicits.global


object FakeWeatherLookup {
	private val cache = CacheBuilder.newBuilder().
		build {
			new CacheLoader[String, Future[Double]] {
				def load(key: String) = Future(fakeWeatherLookup(key))
			}
		}

	def apply(wxCode: String) = cache.get(wxCode)
}

val f1 = FakeWeatherLookup("SFO")
val f2 = FakeWeatherLookup("SFO")  // here we calling same value so it will retrieve fast cuz it's cached
val f3 = FakeWeatherLookup("saikrishna")


Await.ready(f1, 10.seconds)
Await.ready(f2, 10.seconds)  // here we calling same value so it will retrieve fast cuz it's cached
Await.ready(f3, 10.seconds)


Await.result(f1, 10.seconds)
Await.result(f2, 10.seconds)  // here we calling same value so it will retrieve fast cuz it's cached
Await.result(f3, 10.seconds)


