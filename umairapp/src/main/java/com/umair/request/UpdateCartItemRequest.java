package com.umair.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

private Long cartItemId;

private int quantity;

}
