import java.io.File

import scala.annotation.tailrec

/**
  * Created by pratik.joshi on 9/13/17.
  */
/**
  * This is the main entry point for checking the file system via supplied specs
  *
  * @param filter The filter that will be used to match against the file names
  * @param rootLocation The starting location to search
  * @param checkSubFolders A boolean denoting whether or not to search all the subfolders
  * @param contentFilter A filter that will be used to match against the file contents
  */
class Matcher(filter: String, val rootLocation: String = new File(".").getCanonicalPath,
              checkSubFolders: Boolean = false, contentFilter: Option[String] = None) {
	val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

	/**
	  * This searches for the files that match the supplied specs
	  * @return A list of filename, content matched count pairs
	  */
	def execute() = {
		@tailrec
		def recursiveMatch(files : List[IOObject], currentList: List[FileObject]) : List[FileObject] =
			files match {
				case List() => currentList
				case iOOBject :: rest =>
					iOOBject match {
						case file : FileObject if FilterChecker(filter).matches(file.name) =>
							recursiveMatch(rest, file :: currentList)
						case directory : DirectoryObject =>
							recursiveMatch(rest ::: directory.children(), currentList)
						case _ =>
							recursiveMatch(rest, currentList)
					}
			}
		val matchedFiles = rootIOObject match {
			case file: FileObject if FilterChecker(filter).matches(file.name) => List(file)
			case directory: DirectoryObject =>
				if (checkSubFolders) recursiveMatch(directory.children(), List())
				else FilterChecker(filter).findMatchedFiles(directory.children())
			case _ => List()
		}

		val contentFilteredFiles = contentFilter match {
			case Some(dataFilter) =>
				matchedFiles.map(iOObject =>
					(iOObject, Some(FilterChecker(dataFilter)
									.findMatchedContentCount(iOObject.file))))
					.filter(matchTuple => matchTuple._2.get > 0)
			case None => matchedFiles.map(iOObject => (iOObject, None))
		}

		contentFilteredFiles map {
			case (iOObject, count) => (iOObject.fullName, count)
		}
	}


}
