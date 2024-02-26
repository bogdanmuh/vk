package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageModel {

    private String name;
    private String type;
    private byte[] picByte;

}
