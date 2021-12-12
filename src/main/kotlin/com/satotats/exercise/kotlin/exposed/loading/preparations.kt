package com.satotats.exercise.kotlin.exposed.loading

import com.satotats.exercise.kotlin.exposed.connectToDB
import mu.KotlinLogging
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object ParentTable : IntIdTable("parent") {
    val name = varchar("name", 50)
}

class ParentEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ParentEntity>(ParentTable)

    var name by ParentTable.name
    val children by ChildEntity referrersOn ChildTable.parent // 1-nということ
}

object ChildTable : IntIdTable("child") {
    val parent = reference("parent_id", ParentTable)
    val name = varchar("name", 50)
}

class ChildEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChildEntity>(ChildTable)

    var parent by ParentEntity referencedOn ChildTable.parent
    var name by ChildTable.name
}

fun prepare(): List<EntityID<Int>> {
    log.info { "-----------以下、準備------------" }
    connectToDB()
    return transaction { insert() }
        .also { log.info { "-----------以上、準備------------" } }
}

fun insert(): List<EntityID<Int>> = List(2) {
    ParentEntity.new { name = "parent${it + 1}" }
        .also { parent ->
            ChildEntity.new {
                this.parent = parent
                this.name = "${parent.name}.child"
            }
        }
        .let { it.id }
}


val log = KotlinLogging.logger("Logger ") // Exposedのlogger名と同じ高さのお名前にしたかっただけ