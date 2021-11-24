package com.satotats.exercise.kotlin.exposed.dynamicwhere

import com.satotats.exercise.kotlin.exposed.connectToDB
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.compoundAnd
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class Person(
    val name: String?,
    val hobby: String?,
)

object Persons : IntIdTable("persons") {
    val name = varchar("name", 50).nullable()
    val hobby = varchar("hobby", 50).nullable()
}

class PersonEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PersonEntity>(Persons)

    val name by Persons.name
    val hobby by Persons.hobby

    fun toModel(): Person = Person(name = name, hobby = hobby)
}


fun selectByDSL(name: String?, hobby: String?): List<Person> {
    val query = Persons.selectAll()

    name?.let {
        query.andWhere { Persons.name eq it }

    }

    hobby?.let {
        query.andWhere { Persons.hobby eq it }
    }

    return PersonEntity.wrapRows(query).map { it.toModel() }
}


fun selectByDao(name: String?, hobby: String?): List<Person> {
    val entities = PersonEntity.find {
        mutableListOf<Op<Boolean>>(Op.TRUE).apply {
            name?.let {
                add(Persons.name eq it)
            }
            hobby?.let {
                add(Persons.hobby eq it)
            }
        }.compoundAnd()
    }
    return entities.map { it.toModel() }
}

fun main() {
    connectToDB()
    val res = transaction {
        selectByDao("ビル・ゲイツ", null)
    }
    println(res)
}