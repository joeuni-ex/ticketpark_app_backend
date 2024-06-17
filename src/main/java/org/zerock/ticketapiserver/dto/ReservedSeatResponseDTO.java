package org.zerock.ticketapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservedSeatResponseDTO {
    private List<String> reservedSeats = new ArrayList<>();
}
