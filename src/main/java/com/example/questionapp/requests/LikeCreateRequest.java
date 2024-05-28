package com.example.questionapp.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {
    Long userId;
    Long postId;
}
