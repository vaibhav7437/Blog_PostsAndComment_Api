package com.myblog.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {

    private List<PostDto> content;
    private int totalPage;
    private int pageNo;
    private int totalElement;
    private int pageSize;
    private boolean last;


}
