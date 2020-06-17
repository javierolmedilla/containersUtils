package com.olmedilla

import java.io.File
import java.nio.file.{Path, Paths, Files, LinkOption, StandardCopyOption}
import java.nio.file.attribute.BasicFileAttributes
import scala.collection.immutable.Stream
import scala.collection.JavaConverters._
import java.io.{File,FileInputStream,FileOutputStream}

object FileUtils {  
  
  // Devuelve los ficheros abiertos que empiezan por prefix
  def getDirectoriesByPrefix(dir: File, prefix: List[String]): List[File] = {
    dir.listFiles.filter(_.isDirectory()).toList.filter { file =>
      prefix.exists(file.getName.startsWith(_))
    }   
  }
  
  def getPathsByPrefix(basePathName: String, prefix: String): Stream[Path] = 
    listFiles(basePathName)
      .filter(Files.isDirectory(_))
      .filter(_.getFileName.toString().startsWith(prefix))
  
  
  def listFiles(basePathName: String): Stream[Path] = {
    Files.list(Paths.get(basePathName)).iterator()
      .asScala.toStream
  }
  
  // Crea el directorio .kube
  def createKubeDir(dir: File): String = {
    val sep : String = separador(os)
    val directoryKube = dir.getAbsolutePath+sep+".kube"
    println("Directory: "+directoryKube)
    val path : Path = Paths.get(directoryKube)
    Files.exists(path, LinkOption.NOFOLLOW_LINKS) match {
      case false => Files.createDirectory(path)
      case _ => 
    }
    return directoryKube
  }

  // Borr el fichero .kube
  def deleteKubeFile(home: String): String = {
    val sep : String = separador(os)
    val directoryKube = home+sep+".kube"
    println("Directory: "+directoryKube)
    val path : Path = Paths.get(directoryKube)
    Files.deleteIfExists(path)
    return directoryKube
  }

  /*def deleteKubeDir(home: Path): Unit = {
    val sep : String = separador(os)
    val directoryKube = home.toString()+sep+".kube"
    println("Directory: "+directoryKube)
    val path : Path = Paths.get(directoryKube)
    deleteDirectoryRecursivo(path)
  }*/
  
  val os = System.getProperty("os.name");  
  val currentRelativePath : Path = Paths.get("");
 
  val Pattern = "Windows.*".r
    
  def separador(x: String): String = {
    val esWindows = x.startsWith("Windows")
    if (esWindows)
      "\\"
    else "/"
  }
  
  def usage(args: Array[String]):Unit = {
      System.out.println("Operating System: "+os);
      System.out.println("Current path: "+currentRelativePath.toAbsolutePath())
      if (args == null || args.size < 3) {
        System.out.println("Usage: kubeConfigCopier <config-file> <homes dir> <directory pattern>");
        System.exit(0)
      } else {
        System.out.println("Config file: "+args(0));
        System.out.println("Homes dir: "+args(1));
        System.out.println("Home pattern: "+args(2));
      }
  }
  
  def copyFileToKube(kubeConfig: String, kubeDirectory:String): Unit = {
     val src = new File(kubeConfig)
     val dst = new File(kubeDirectory+"/config")
     new FileOutputStream(dst) getChannel() transferFrom(
      new FileInputStream(src) getChannel, 0, Long.MaxValue )
  }
  
  /*def deleteDirectoryRecursivo(path : Path): Unit = java.nio.file.Files.exists(path,LinkOption.NOFOLLOW_LINKS) match {
    case true => {
      try {
        Files
          .list(path)
          .forEach((f:Path) => {
            if (Files.isDirectory(f, LinkOption.NOFOLLOW_LINKS)) {
              deleteDirectoryRecursivo(f)
              Files.delete(f)
            }
            else {
                Files.delete(f)
                println("Delete: "+f.toString())
            }
          }
        )
        Files.delete(path)
      } catch {
        case ex : Throwable => println(ex)
      }
    }
    case false => 
  }*/
  
  
  
}