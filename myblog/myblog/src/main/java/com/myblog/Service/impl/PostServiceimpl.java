package com.myblog.Service.impl;

import com.myblog.Entity.Post;
import com.myblog.Exception.ResourceNotFound;
import com.myblog.Payload.PostDto;
import com.myblog.Payload.PostResponse;
import com.myblog.Repository.PostRepository;
import com.myblog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceimpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceimpl(PostRepository postRepository
    , ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());


        Post save = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setTitle(save.getTitle());
        dto.setContent(save.getContent());
        dto.setDescription(save.getDescription());

        return dto;
    }

    @Override
    public PostResponse  getALLPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
         Page<Post> all =    postRepository.findAll(pageable);
         List<Post>  post = all.getContent();

        List<PostDto> postDtos = post.stream().map(p -> maptoDto(p)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalElement((int) all.getTotalElements());
        postResponse.setTotalPage(all.getTotalPages());
        postResponse.setLast(all.isLast());



        return postResponse;
    }

    @Override
    public void delete_PostByID(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("Post is not find by id "+id)
        );
        postRepository.deleteById(id);


    }

    @Override
    public PostDto update_Post(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("id is not find in Post check again "+id)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(post.getDescription());
        Post post1 = postRepository.save(post);
        PostDto dto = maptoDto(post1);

        return dto;
    }

    public PostDto maptoDto(Post post){
        PostDto dto = modelMapper.map(post,PostDto.class);
        return dto;
    }

    public Post maptoPost(PostDto dto){
        Post post = modelMapper.map(dto,Post.class);
        return post;
    }
}
