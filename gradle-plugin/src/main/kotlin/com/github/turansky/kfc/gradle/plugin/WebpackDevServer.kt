package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Task
import org.gradle.internal.os.OperatingSystem

internal fun devServerConfiguration(
    source: Task,
    port: Int
): String {
    val project = source.project
    val projectName = project.name
    val runTaskPath = "${project.path}:${source.name}"

    // language=JavaScript
    return """
      if (config.mode !== 'development') {
        return
      }
      
      console.log('Running $runTaskPath in background...')
      const childRun = require('child_process').exec(
        '$gradleWrapper $runTaskPath',
        {
          cwd: ${project.rootDir.toPathString()}
        },
        (err, stdout, stderr) => {
          if (err) {
            console.log('Cannot run $runTaskPath server: ' + err)
          }
        }
      )

      let isChildRunning = false

      const devServer = config.devServer = config.devServer || {}
      devServer.before = function (app, server, compiler) {
        if (isChildRunning) {
          return
        }
  
        isChildRunning = true
  
        const originalClose = server.middleware.close
        server.middleware.close = function () {
          childRun.kill('SIGINT')
          originalClose(arguments)
        }
      }
      
      const proxy = devServer.proxy = devServer.proxy || {}
      proxy['/$projectName'] = {
        target: 'http://localhost:$port',
        pathRewrite: {'^/$projectName' : ''},
        secure: false,
      }
    """.trimIndent()
}

private val gradleWrapper: String
    get() {
        val os = OperatingSystem.current()
        return when {
            os.isWindows -> "gradle.bat"
            else -> "./gradlew"
        }
    }

