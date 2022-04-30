package com.blog.api.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.config.AppConstants;
import com.blog.api.entity.Post;
import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.services.FileService;
import com.blog.api.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	
	@Autowired
	private PostService postService;
	
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDto createPost= this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
		
		List<PostDto> posts=this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	
	//get by category
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId)
		{
			
			List<PostDto> posts=this.postService.getPostsByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
			
		}
	
		
		//get list of post
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getPosts(
				@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer PageNumber,
				@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
				@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
				@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir)
		{
			
			PostResponse postResponse= this.postService.getAllPost(PageNumber,pageSize,sortBy,sortDir);
			return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
			
		}
		
		
		//get Single post by id
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getPost(@PathVariable Integer postId)
		{
			
			PostDto postDto= this.postService.getPostById(postId);
			return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
			
		}

		//update 
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto PostDto,@PathVariable Integer postId)
		{
			
			PostDto updatePost=	this.postService.updatePost(PostDto,postId);
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
			
		}
		
		//delete
		@DeleteMapping("/posts/{postId}")
		public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
		{
			
			this.postService.deletePost(postId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted successfully !!",true),HttpStatus.OK);
			
		}
	
	
		
		//search
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable ("keywords") String keywords){
		List<PostDto> result=	this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
			
		}
		
		
		//post image upload
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
			PostDto postDto= this.postService.getPostById(postId);
	
		String fileName=this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		}
		
		
		//method to serve file
		@GetMapping(value="post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		
		public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response )throws IOException{
			
		InputStream resource=	this.fileService.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
