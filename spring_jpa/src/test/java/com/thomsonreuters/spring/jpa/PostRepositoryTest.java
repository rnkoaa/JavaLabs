package com.thomsonreuters.spring.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by U0165547 on 7/22/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@Transactional
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testPostRepositoryNotNull(){
        assertNotNull(postRepository);
    }

    @Test
    public void saveAndRetrievePosts(){
        Post post = new Post();
        post.setBody("This is a post about wordpress");
        post.setTitle("wordpress test");

        Post savedPost = postRepository.save(post);

        assertNotNull(savedPost);
        assertNotNull(savedPost.getId());

        Post foundPost = postRepository.findOne(savedPost.getId());
        assertNotNull(foundPost);
        assertTrue(foundPost.getTitle().contains("wordpress"));
        assertTrue(foundPost.getBody().contains("about wordpress"));
    }

    @Test
    public void findByCriteriaBuilder(){
        Post post = new Post();
        post.setBody("This is a post about wordpress");
        post.setTitle("wordpress test");

        Post savedPost = postRepository.save(post);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> query = cb.createQuery(Post.class);
        Root<Post> postRoot = query.from(Post.class);

        query.select(postRoot)
                .where(cb.equal(postRoot.get("title"), "wordpress test"));

        TypedQuery<Post> postTypedQuery = em.createQuery(query);
        List<Post> posts = postTypedQuery.getResultList();

        assertNotNull(posts);
        assertTrue(posts.size() > 0);
        assertTrue(posts.size() == 1);
        for (Post post1 : posts) {
            System.out.println(post1.getTitle());
            System.out.println(post1.getBody());
        }


        //List<Post>
    }
}
