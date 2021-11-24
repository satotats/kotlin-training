package com.satotats.exercise.kotlin.exposed

import org.jetbrains.exposed.sql.Database

fun connectToDB() = Database.connect(
    url = "jdbc:h2:file:~/test", // 何の変哲もないh2
    driver = "org.h2.Driver"
)