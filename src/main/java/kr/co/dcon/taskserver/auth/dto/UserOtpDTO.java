package kr.co.dcon.taskserver.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserOtpDTO {
    private String id;
    private String label;
    private String createdDate;
}
