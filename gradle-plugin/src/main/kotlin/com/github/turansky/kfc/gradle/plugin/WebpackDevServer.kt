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
      const childRunProcess = require('child_process').exec(
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
    """.trimIndent()
