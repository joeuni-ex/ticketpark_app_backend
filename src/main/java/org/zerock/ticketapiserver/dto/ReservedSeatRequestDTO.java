package org.zerock.ticketapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedSeatRequestDTO {

    private Long gno;

    private String time;

}
