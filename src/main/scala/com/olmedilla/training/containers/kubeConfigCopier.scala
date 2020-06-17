package com.olmedilla

import java.io.File
import java.nio.file.{Path, Paths, Files, LinkOption}
import java.nio.file.attribute.BasicFileAttributes

//import scala.jdk.StreamConverters._
  /* Arguments: 
   * 	Kube config file name: ej. .kube/config
   *  Base home directories: ej. /home
   *  Pattern to match Name of directories: ej. course, course-math matchs 
   *  will copy the config file into .kube, created if does not existe and copy 
   *  config file into all /home/course/.kube
  */

object kubeConfigCopier extends App {
  import FileUtils._
  usage(args)
  val configKubeFile = args(0)
  val baseDir = args(1)
  val pattern = List(args(2))
  val homesFile = new File(baseDir);
  val homes = getDirectoriesByPrefix(homesFile,pattern)
  System.out.println("Ficheros encontrados: ")
  homes
    .map(createKubeDir)
    .foreach(copyFileToKube(configKubeFile,_))
}