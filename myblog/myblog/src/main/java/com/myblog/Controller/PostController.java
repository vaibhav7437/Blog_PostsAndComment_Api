package com.myblog.Controller;

import com.myblog.Payload.PostDto;
import com.myblog.Payload.PostResponse;
import com.myblog.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> SavePost(
          @Valid @RequestBody PostDto postDto,
          BindingResult result
    ){
        if(result.hasErrors()){
            return  new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
      PostDto dto = postService.savePost(postDto);
      return new ResponseEntity<>(dto, HttpStatus.CREATED);
}


    // http://localhost:8080/api/posts?pageNo=0&pageSize=3


   @GetMapping
    public PostResponse getALLList(
           @RequestParam(value = "pageNo", defaultValue = "0", required = false) int
                   pageNo,
           @RequestParam(value = "pageSize", defaultValue = "10", required = false) int
                   pageSize,
           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String
                   sortBy,
           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String
                   sortDir

   ){
        PostResponse postResponse =  postService.getALLPost(pageNo , pageSize,sortBy,sortDir);
        return postResponse;
   }


    @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
    ResponseEntity<?> delete_Post(@PathVariable("id") long id ){
        postService.delete_PostByID(id);
        return new ResponseEntity<>("Post is deleted by id "+id ,HttpStatus.OK);
   }


    @PreAuthorize("hasRole('ADMIN')")
   @PutMapping("/{id}")
    ResponseEntity<PostDto> update_Post (@PathVariable("id") long id ,@RequestBody PostDto postDto){

        PostDto postDto1 = postService.update_Post(id,postDto);

        return new ResponseEntity<>(postDto1 , HttpStatus.OK);
    }

}
