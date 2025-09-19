package com.mercedesbenz.springdatajpaissue

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@SpringBootApplication
class SpringDataJpaIssueApplication

fun main(args: Array<String>) {
    runApplication<SpringDataJpaIssueApplication>(*args)
}

@Entity
@Table(name = "deletable_entities")
class DeletableEntity(
    @Id
    @Column(name = "id")
    val id: UUID,
    @Column(name = "created_at")
    val createdAt: Instant = Instant.now(),
)

interface DeletableEntityRepository : JpaRepository<DeletableEntity, UUID> {
    @Transactional
    @Modifying
    fun deleteByCreatedAtBefore(createdAt: Instant): Int

    @Transactional
    @Modifying
    fun deleteAllInBatchByCreatedAtBefore(createdAt: Instant): Int
}