package com.distribuit.datos.common

import com.datos.vfs.{ FileSystemManager, VFS }
import com.typesafe.config.{ Config, ConfigFactory }

import scala.io.Source

/**
 * Copyright (c) 2016 Distribuit Inc.
 * @author paulson.vincent
 */
object DatosSettings {
  val config: Config = ConfigFactory.parseString(Source.fromFile("conf/application.conf").mkString)
  val tempDirectory: String = "_staging"
  val fileManager: FileSystemManager = VFS.getManager
}
