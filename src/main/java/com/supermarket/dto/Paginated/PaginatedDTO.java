package com.supermarket.dto.Paginated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedDTO {
    private Object list;
    private Long count;
}
