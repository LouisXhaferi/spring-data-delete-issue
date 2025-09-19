package com.mercedesbenz.springdatajpaissue

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import java.time.Instant.EPOCH
import java.util.UUID.randomUUID

@Import(TestcontainersConfiguration::class)
@SpringBootTest
@ActiveProfiles("test")
class DeleteByIssueTest(
    @param:Autowired private val deleteByIssueRepository: DeletableEntityRepository,
) {
    @BeforeEach
    fun setup() {
        deleteByIssueRepository.deleteAll()
    }

    @Test
    fun `can delete multiple by condition, skipping some`() {
        deleteByIssueRepository.saveAllAndFlush(
            listOf(
                DeletableEntity(randomUUID(), EPOCH.minusSeconds(1)),
                DeletableEntity(randomUUID(), EPOCH.minusSeconds(2)),
                DeletableEntity(randomUUID(), EPOCH),
            ),
        )

        val deleted = deleteByIssueRepository.deleteByCreatedAtBefore(EPOCH)

        assertThat(deleted).isEqualTo(2)
        assertThat(deleteByIssueRepository.findAll()).hasSize(1)
    }

    @Test
    fun `can delete one, skipping some`() {
        deleteByIssueRepository.saveAllAndFlush(
            listOf(
                DeletableEntity(randomUUID(), EPOCH.minusSeconds(1)),
                DeletableEntity(randomUUID(), EPOCH),
            ),
        )

        val deleted = deleteByIssueRepository.deleteByCreatedAtBefore(EPOCH)

        assertThat(deleted).isEqualTo(1)
        assertThat(deleteByIssueRepository.findAll()).hasSize(1)
    }

    @Test
    fun `can delete multiple by condition, using batch method, skipping some`() {
        deleteByIssueRepository.saveAllAndFlush(
            listOf(
                DeletableEntity(randomUUID(), EPOCH.minusSeconds(1)),
                DeletableEntity(randomUUID(), EPOCH.minusSeconds(2)),
                DeletableEntity(randomUUID(), EPOCH),
            ),
        )

        val deleted = deleteByIssueRepository.deleteAllInBatchByCreatedAtBefore(EPOCH)

        assertThat(deleted).isEqualTo(2)
        assertThat(deleteByIssueRepository.findAll()).hasSize(1)
    }

    @Test
    fun `can delete one, using batch method, skipping some`() {
        deleteByIssueRepository.saveAllAndFlush(
            listOf(
                DeletableEntity(randomUUID(), EPOCH.minusSeconds(1)),
                DeletableEntity(randomUUID(), EPOCH),
            ),
        )

        val deleted = deleteByIssueRepository.deleteAllInBatchByCreatedAtBefore(EPOCH)

        assertThat(deleted).isEqualTo(1)
        assertThat(deleteByIssueRepository.findAll()).hasSize(1)
    }
}
