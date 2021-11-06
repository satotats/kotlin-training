package com.satotats.exercise.kotlin.exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ParentTable : IntIdTable("parent") {
    val happiness = integer("happiness")
}

object ChildTable : IntIdTable("child", "parent_id") {
    override val id = reference("parent_id", ParentTable)
}

class ChildEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<ChildEntity>(ChildTable)
}