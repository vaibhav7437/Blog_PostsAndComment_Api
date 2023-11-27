package com.myblog.Service;

import com.myblog.Payload.CommentDto;

import java.util.List;

public interface Commentservice {
    public CommentDto saveinfo(long id ,  CommentDto commentDto) ;

    List<CommentDto> getCommentByPost_id(long id);

     CommentDto getCommentByComment_id(long id);

    CommentDto updateCommentByComment_id(long id, CommentDto commentDto);
}
