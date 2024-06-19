package org.zerock.ticketapiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ticketapiserver.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {




}
