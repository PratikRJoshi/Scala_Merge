import org.scalatest.FlatSpec

/**
  * Created by pratik.joshi on 9/13/17.
  */

class MatcherTests extends FlatSpec {
		"Matcher that is passed a file matching the filter" should
		"return a list with that file name" in {
			val matcher = new Matcher("fake", "fakePath")

			val results = matcher.execute()

			assert(results == List("fakePath"))
		}
}
