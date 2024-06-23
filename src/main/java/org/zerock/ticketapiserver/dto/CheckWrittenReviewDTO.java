package org.zerock.ticketapiserver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckWrittenReviewDTO {


    private Long rno;

    private boolean writtenReview;

}
