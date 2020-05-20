package priv.patrick.springBucks.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private Boolean success;

    private String code;

    private String message;

    private T data;

}
