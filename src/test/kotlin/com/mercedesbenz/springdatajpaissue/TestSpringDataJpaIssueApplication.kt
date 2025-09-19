package com.mercedesbenz.springdatajpaissue

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<SpringDataJpaIssueApplication>().with(TestcontainersConfiguration::class).run(*args)
}
