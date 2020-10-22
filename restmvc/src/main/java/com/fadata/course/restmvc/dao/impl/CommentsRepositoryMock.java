package com.fadata.course.restmvc.dao;

import com.fadata.course.restmvc.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CommentsRepositoryMock implements CommentsRepository {

    private AtomicLong nextId = new AtomicLong(0);

    private Map<Long, Comment> comments = new ConcurrentHashMap<>();

    public CommentsRepositoryMock() {
        List<Comment> tmpList = new ArrayList<>();
        tmpList.add(new Comment("test comment", "Zdravko"));
        tmpList.add(new Comment("test comment23", "Zdra232vko"));
        tmpList.add(new Comment("test comm213213ent", "Zdravk3434o"));
        tmpList.add(new Comment("test co23mment", "Zdra43vko"));
        tmpList.add(new Comment("test co2323mment", "Zdr545avko"));
        tmpList.forEach(this::create);
    }

    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(comments.values());
    }

    @Override
    public Comment findById(Long id) {
        return comments.get(id);
    }

    @Override
    public Comment create(Comment comment) {
        comment.setId(nextId.getAndIncrement());
        comments.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return comments.replace(comment.getId(), comment);
    }

    @Override
    public Comment deleteById(Long id) {
        return comments.remove(id);
    }

    @Override
    public long count() {
        return comments.size();
    }
}
