package kr.co.dcon.taskserver.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.dcon.taskserver.common.constants.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> implements Serializable {
    private Date timestamp = new Date();

    private Integer code;

    private String codeName;

    private String desc;

    private String descKr;
// 제네릭타입 t를 사용하여 resultData에 어떤타입의 객체가 들어올지 몰라 일반화 시켜놓음
    private T resultData;

    public ResponseDTO(ResultCode resultCode) {
        init(resultCode);
    }

    public ResponseDTO(ResultCode resultCode, T resultData) {
        init(resultCode);
        this.resultData = resultData;
    }

    private void init(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.codeName = resultCode.name();
        this.desc = resultCode.getDescription();
        this.descKr = resultCode.getDescriptionKr();
    }
}
