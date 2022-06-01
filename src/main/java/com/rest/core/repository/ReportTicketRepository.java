package com.rest.core.repository;

import com.rest.core.model.ReportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTicketRepository extends JpaRepository<ReportTicket,Long> {
}
