package com.personal_organization.domain.project;

import com.personal_organization.domain.exception.BusinessException;
import com.personal_organization.domain.valueobject.CategoryId;
import com.personal_organization.domain.valueobject.Money;
import com.personal_organization.domain.valueobject.ProjectId;
import com.personal_organization.domain.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void shouldRejectZeroBudget() {
        Money zeroBudget = new Money(new BigDecimal("0"));

        Executable action = () -> new Project(
                new ProjectId("p1"),
                new UserId("user1"),
                "mudanca",
                "Mudança", LocalDate.now(),
                zeroBudget, ProjectStatus.ACTIVE, List.of()
        );

        assertThrows(BusinessException.class, action);
    }


    @Test
    void shouldAllowItemWithZeroValue() {

        Project project = new Project(
                new ProjectId("p1"),
                new UserId("user1"),
                "viagem",
                "Viagem", LocalDate.now(),
                new Money(new BigDecimal("1000")), ProjectStatus.ACTIVE, List.of());

        project.addItem(new ProjectItem(
                "i1",
                new ProjectId("p1"),
                new UserId("user1"),
                "Item gratuito",
                new Money(new BigDecimal("0")),
                new CategoryId("lazer"),
                null
        ));

        assertEquals(new BigDecimal("0"), project.calculateTotalSpent().getAmount());
    }
}