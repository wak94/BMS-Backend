package com.wak.backend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequest {
    private String username;
    private String phone;
    private int currentPage;
    private int pageSize;
}
