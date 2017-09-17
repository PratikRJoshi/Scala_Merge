import java.io.File

import org.scalatest.FlatSpec

/**
  * Created by pratik.joshi on 9/13/17.
  */

class MatcherTests extends FlatSpec {
	val testFilesRootPath = "/Users/pratik.joshi/Documents/Projects/FileSearcher/testFiles/"

	"Matcher that is passed a file matching the filter" should
		"return a list with that file name" in {
		val matcher = new Matcher("fake", "fakePath")

		val results = matcher.execute()

		assert(results == List(("/Users/pratik.joshi/Documents/Projects/FileSearcher/fakePath", None)))
	}

	"Matcher using a directory containing one file matching the filter " should
		"return a list with that file name" in {
		val matcher = new Matcher("txt", new File("testFiles").getCanonicalPath())

		val results = matcher.execute()

		assert(results == List((s"${testFilesRootPath}readme.txt", None)))
	}

	"Matcher that is not passed a root file location" should
		"use the current location" in {
		val matcher = new Matcher("filter")
		assert(matcher.rootLocation == new File(".").getCanonicalPath)
	}

	"Matcher with subfolder checking matching root location with two subtree files matching" should
	"return a list with those file names" in {
		val searchSubDirectories = true
		val matcher = new Matcher("txt", new File("testFiles").getCanonicalPath(), searchSubDirectories)

		val result = matcher.execute()

		assert(result == List((s"${testFilesRootPath}directory/notes.txt", None),
								(s"${testFilesRootPath}readme.txt", None)))
	}

	"Matcher given a path that has one file that matches the file filter and content filter" should
	"return a list with that file name" in {
		val matcher = new Matcher("data", new File(".").getCanonicalPath, true, Some("pluralsight"))
		val matchedFiles = matcher.execute()

		assert(matchedFiles == List((s"${testFilesRootPath}pluralsight.data", Some(3))))
	}

	"Matcher given a path that has no file that matches the file filter and content filter" should
		"return a list with that file name" in {
		val matcher = new Matcher("txt", new File(".").getCanonicalPath, true, Some("pluralsight"))
		val matchedFiles = matcher.execute()

		assert(matchedFiles == List())
	}
}
