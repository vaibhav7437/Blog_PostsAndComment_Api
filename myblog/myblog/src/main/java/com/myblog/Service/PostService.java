package com.myblog.Service;

import com.myblog.Payload.PostDto;
import com.myblog.Payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto savePost(PostDto postDto);

    PostResponse getALLPost(int pageSize, int pageNo, String sortBy, String sortDir);

    void delete_PostByID(long id);

    PostDto update_Post(long id, PostDto postDto);
}
