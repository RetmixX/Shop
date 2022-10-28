package com.retmix.shop.shop.response.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddObject {
    private Long id;
    private String message;
}
