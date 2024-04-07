package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.domain.dto.UserDto;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindResponse {

    private List<UserDto> users;

}
