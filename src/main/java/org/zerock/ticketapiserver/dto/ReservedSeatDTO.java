package org.zerock.ticketapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservedSeatDTO {

    private List<String> reservedSeatNumber = new ArrayList<>();
}
