package com.satotats.exercise.kotlin.exposed.loading

import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val id = prepare()
    // ...前略。LazyLoadingの前提に同じ。

    transaction {
        log.info { "start: ParentEntity.findById(id)" }
        val parent = ParentEntity.findById(id)!!.load(ParentEntity::children) // ここだけちがう
        log.info { "end:   ParentEntity.findById(id)" }

        val children = parent.children // ChildEntity(ParentEntityと1-N関係をとる子エンティティ)
        log.info { "parent.children     : $children" }
        val childName = children.first().name
        log.info { "parent.children.name: $childName" }
    }
}

