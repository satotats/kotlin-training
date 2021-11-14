package com.satotats.exercise.kotlin.exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

data class Person(
    val name: String,
    val hobby: String,
)

object Persons : IntIdTable("persons") {
    val name = varchar("name", 50)
    val hobby = varchar("hobby", 50)
}

class PersonEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonEntity>(Persons)

    val name by Persons.name
    val hobby by Persons.hobby

    fun toModel(): Person = Person(name = name, hobby = hobby)
}