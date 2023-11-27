package com.myblog.Service.impl;

import com.myblog.Entity.Comment;
import com.myblog.Entity.Post;
import com.myblog.Exception.ResourceNotFound;
import com.myblog.Payload.CommentDto;
import com.myblog.Repository.CommentRepository;
import com.myblog.Repository.PostRepository;
import com.myblog.Service.Commentservice;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Commentservcieimpl implements Commentservice {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public Commentservcieimpl(CommentRepository commentRepository
    , PostRepository postRepository,
                              ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository= postRepository;
        this.modelMapper= modelMapper;
    }

    @Override
    public CommentDto saveinfo(long id , CommentDto commentDto) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("post is not find by id: "+id)
        ) ;
        Comment comment = maptoComment(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);

        CommentDto dto = maptoCommentDto(comment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentByPost_id(long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        if (comments.isEmpty()) {
            throw new ResourceNotFound("Comment is not present by Post_id " + postId);
        }
        List<CommentDto> dtos = comments.stream().map(this::maptoCommentDto).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public CommentDto getCommentByComment_id(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Comment is not found by  comment_id")
        );
        CommentDto dto = maptoCommentDto(comment);

        return dto;
    }

    @Override
    public CommentDto updateCommentByComment_id(long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Comment is not found by id :"+id)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment comment1 = commentRepository.save(comment);
        CommentDto dto = maptoCommentDto(comment1);
        return dto;
    }

    public Comment maptoComment(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto , Comment.class);
        return comment;
    }
    public CommentDto maptoCommentDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment , CommentDto.class);
        return commentDto;
    }
}
