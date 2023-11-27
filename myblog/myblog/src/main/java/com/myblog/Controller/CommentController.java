package com.myblog.Controller;

import com.myblog.Payload.CommentDto;
import com.myblog.Service.Commentservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private Commentservice commentservice;

    public CommentController(Commentservice commentservice){
        this.commentservice = commentservice;
    }


    @PostMapping("/{Post_id}")
    public ResponseEntity<CommentDto> saveComment(@PathVariable("Post_id")  long id,@RequestBody CommentDto commentDto){
        CommentDto dto = commentservice.saveinfo( id ,commentDto);
        return new ResponseEntity<>(dto , HttpStatus.CREATED);
    }


    @GetMapping("/post_id/{Post_id}")
    public List<CommentDto> getCommentByPost_id (@PathVariable("Post_id") long id){
        List<CommentDto> list = commentservice.getCommentByPost_id(id);
        return  list;
    }
    @GetMapping("/comment_id/{comment_id}")
    public CommentDto getCommentByComment_id (@PathVariable("comment_id") long id){
        CommentDto commentDto = commentservice.getCommentByComment_id(id);
        return commentDto ;
    }

    @PutMapping("/comment_id/{comment_id}")
    public ResponseEntity<CommentDto> updateCommentByComment_id(@PathVariable ("comment_id") long id , @RequestBody CommentDto commentDto){

        CommentDto commentDto1 = commentservice.updateCommentByComment_id(id,commentDto);
        return new ResponseEntity<>(commentDto1 , HttpStatus.OK);

    }

}
