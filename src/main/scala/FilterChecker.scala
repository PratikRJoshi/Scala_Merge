class FilterChecker (filterStr : String) {

    def matches(content : String): Boolean = content.contains(filterStr)

    def findMatchedFiles(iOObjects : List[IOObject]): List[IOObject] =
        for(iOObject <- iOObjects
            if iOObject.isInstanceOf[FileObject]
            if matches(iOObject.name))
        yield iOObject
}

object FilterChecker {
	def apply(filterStr: String): FilterChecker = new FilterChecker(filterStr)
}
