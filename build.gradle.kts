tasks {
    val build by registering {
        doLast {
            println("Build success!")
        }
    }

    wrapper {
        gradleVersion = "6.1.1"
        distributionType = Wrapper.DistributionType.ALL
    }
}
