package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

internal fun devServerConfiguration(
    project: Project
): String =
    // language=JavaScript
    """
      if (config.mode !== 'development') {
        return
      }
      
      const runTaskName = '${project.path}:run'
      const serverUrl = 'http://localhost:8081'

      console.log('Running ' + runTaskName + ' in background...')
      const rootDir = ${project.rootDir.toPathString()}
      const childRun = require('child_process').exec(
        './gradlew ' + runTaskName,
        {
          'cwd': rootDir
        },
        (err, stdout, stderr) => {
          if (err) {
            console.log('Cannot run ' + runTaskName + ' server: ' + err)
          }
        }
      )

      let isChildRunning = false

      config.devServer = config.devServer || {}
      config.devServer.before = function (app, server, compiler) {
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
    """.trimIndent()
