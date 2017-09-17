import java.io.File

import scala.util.control.NonFatal

class FilterChecker (filterStr : String) {

    def matches(content : String): Boolean = content.contains(filterStr)

    def findMatchedFiles(iOObjects : List[IOObject]): List[IOObject] =
        for(iOObject <- iOObjects
            if iOObject.isInstanceOf[FileObject]
            if matches(iOObject.name))
        yield iOObject

    def matchesFileContent(file : File) = {
	    import scala.io.Source
	    try {
		    val fileSource = Source.fromFile(file)
		    try {
			    fileSource.getLines().exists(line => matches(line))
		    } catch {
			    case NonFatal(_) => false
		    } finally {
			    fileSource.close()
		    }

	    } catch {
		    case NonFatal(_) => false
	    }
    }
}

object FilterChecker {
	def apply(filterStr: String): FilterChecker = new FilterChecker(filterStr)
}
