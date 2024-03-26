package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardResponseDto {

    private boolean success;
    private Object data;
    private String error;

}
