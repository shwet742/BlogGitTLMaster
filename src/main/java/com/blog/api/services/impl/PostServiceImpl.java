package com.blog.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entity.Category;
import com.blog.api.entity.Post;
import com.blog.api.entity.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "user Id", userId));
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "category Id", categoryId));

		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=	this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post ", "postId", postId));
		
		post.setTitle(postDto.getTitle());	
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedpost= this.postRepo.save(post);
			return this.modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId ",postId));
		this.postRepo.delete(post);		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}	
		
		 Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		 
		 Page<Post> pagePost=this.postRepo.findAll(p);	 
		 List<Post> allPosts= pagePost.getContent();
		 List<Post> posts= this.postRepo.findAll();
		 List<PostDto> postDtos= allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setLastPage(pagePost.isLast());

	    return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
		
	    List<Post> posts=this.postRepo.findByCategory(cat);
	    
	    List<PostDto> postDtos=   posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
	User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		
	    List<Post> posts=this.postRepo.findByUser(user);
	    
	    List<PostDto> postDtos=   posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
	    		.collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> posts= this.postRepo.searchByTitle("%"+keyword+"%");
		
	    List<PostDto> postDto=	posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
