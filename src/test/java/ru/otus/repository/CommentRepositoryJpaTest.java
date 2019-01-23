package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Comment;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        "spring.datasource.data=testdata.sql"
})
@Transactional
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa repositoryJpa;

    @Test
    void testCreate() {
        repositoryJpa.create(new Comment("user", "text", 2));
        List<Comment> comments = repositoryJpa.getByBookId(2);
        assertThat(comments.get(0))
                .hasFieldOrPropertyWithValue("userName", "user")
                .hasFieldOrPropertyWithValue("text", "text")
                .hasFieldOrPropertyWithValue("book.id", 2)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testDelete() {
        Comment comment = new Comment("user", "delete test", 2);
        repositoryJpa.create(comment);
        long countBefore = repositoryJpa.getByBookId(2).size();
        repositoryJpa.delete(comment.getId());
        long countAfter = repositoryJpa.getByBookId(2).size();
        assertThat(countAfter).isEqualTo(countBefore - 1);
    }

}