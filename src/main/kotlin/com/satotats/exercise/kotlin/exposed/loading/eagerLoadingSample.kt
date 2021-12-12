package com.satotats.exercise.kotlin.exposed.loading

import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val idList = prepare()
    // ...前略。LazyLoadingの前提に同じ

    transaction {
        log.info { "start: ParentEntity.find(idList)" }
        val parents = ParentEntity.find { ParentTable.id inList idList }
            .with(ParentEntity::children) // ここだけちがう
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

