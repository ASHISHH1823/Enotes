package com.ashish.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NoteResponse {
private List<NoteDto> notes;
private Integer pageNo;
private Integer pageSize;
private long totalElements;
private Integer totalPages;
private Boolean isFirst;
private Boolean isLast;
}
