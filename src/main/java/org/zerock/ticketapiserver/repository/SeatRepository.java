package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ticketapiserver.domain.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {


}
