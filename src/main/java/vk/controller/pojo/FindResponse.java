package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class FindResponse {

    private List<String> users;

}
