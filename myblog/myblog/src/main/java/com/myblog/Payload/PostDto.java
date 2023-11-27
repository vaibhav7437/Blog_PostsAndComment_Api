package com.myblog.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 2 , message="Post title should be at least 2 charactor")
    private String title;

    @NotEmpty
    @Size(min = 5 , message="Post description should be at least 5 charactor")
    private String description;

    @NotEmpty
    @Size(min = 5 , message="Post Content should be at least 5 charactor")

    private String content;

    }

