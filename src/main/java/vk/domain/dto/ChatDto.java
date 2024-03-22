package vk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatDto {
    private Long user_id;
    private Long count;
}
