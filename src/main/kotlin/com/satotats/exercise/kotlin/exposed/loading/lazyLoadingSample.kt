package com.satotats.exercise.kotlin.exposed.loading

import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val idList = prepare()
    // ...前略
    // DBに接続し、2組の親子レコードをinsertしておく
    // insertした親レコードのidを持った状態でスタート

    transaction {
        log.info { "start: ParentEntity.find(idList)" }
        val parents = ParentEntity.find { ParentTable.id inList idList }
        log.info { "end:   ParentEntity.find(idList)" }

        parents.forEach { parent ->
            log.info { "start evaluation: parent.children" }
            val children = parent.children // ChildEntity(ParentEntityと1-N関係をとる子エンティティ)
            log.info { "end evaluation:   parent.children" }

            log.info { "start evaluation: children.first()" }
            val child = children.first()
            log.info { "end evaluation:   children.first()" }
        }
    }
}
