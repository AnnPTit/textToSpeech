package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
	private String msg;
	private String status;
}
