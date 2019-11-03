
object Eventually {

  case class MaxTries(value: Int)
  case class Interval(value: Int)

  implicit val maxTriesForEvaluating: MaxTries = MaxTries(5)
  implicit val intervalForEvaluating: Interval = Interval(10)

  def eventually[A](f: => Int)(implicit maxTries: MaxTries, interval: Interval): Int = {
    eventuallyWith(maxTries.value, interval.value)(f)
  }

   var failedCount = 0
  def eventuallyWith(maxTries: Int, interval: Int)(f: => Int): Int = {
    var succeeded = false
    if (failedCount < maxTries) {
      try { f }
        catch
        {
          case e: Exception =>
            failedCount += 1
            Thread.sleep(interval)
            eventuallyWith(maxTries, interval)(f)
        }
    }
    else throw new Exception
  }

}

import Eventually._

eventually{
  println("trying")
  1/0
}
