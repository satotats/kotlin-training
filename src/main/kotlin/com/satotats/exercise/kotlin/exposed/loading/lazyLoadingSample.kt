package com.satotats.exercise.kotlin.exposed.loading

import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val id = prepare()
    // ...前略。
    // DBに接続し、一対の親子レコードをinsertしておく。
    // insertした親レコードのidを持った状態でスタート。

    transaction {
        log.info { "start: ParentEntity.findById(id)" }
        val parent = ParentEntity.findById(id)!!
        log.info { "end:   ParentEntity.findById(id)" }

        val children = parent.children // ChildEntity(ParentEntityと1-N関係をとる子エンティティ)
        log.info { "parent.children     : $children" }
        val childName = children.first().name
        log.info { "parent.children.name: $childName" }
    }
}
