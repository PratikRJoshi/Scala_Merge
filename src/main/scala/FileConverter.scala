import java.io.File

/**
  * Created by pratik.joshi on 9/13/17.
  */
object FileConverter {
	def convertToIOObject(file : File) =
		if(file.isDirectory()) DirectoryObject(file)
		else FileObject(file)
}
