package org.zerock.ticketapiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.ticketapiserver.dto.*;
import org.zerock.ticketapiserver.repository.ReservationRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {


    private final ReservationRepository reservationRepository;

    @Override
    public List<ReservationListDTO> getReservations(String email) {
        return reservationRepository.getReservationByEmail(email);
    }

}
