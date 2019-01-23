package ru.otus.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.Comment;
import ru.otus.service.CommentService;

import java.util.List;

@ShellComponent
public class CommentShell {

    private final CommentService commentService;

    public CommentShell(CommentService commentService) {
        this.commentService = commentService;
    }

    @ShellMethod("создать комментарий")
    public void createComment(
            @ShellOption(help = "id книги") int bookId,
            @ShellOption(help = "автор комментария")String userName,
            @ShellOption(help = "текст комментария") String text) {
        commentService.create(new Comment(userName, text, bookId));
    }

    @ShellMethod("Показать комментарии к книге")
    public String getComments(@ShellOption(help = "id книги") int id) {
        List<Comment> comments = commentService.getByBookId(id);
        return getInfo(comments);
    }

    @ShellMethod("удалить комментарий")
    public void deleteComment(@ShellOption(help = "id комментария") long id) {
        commentService.delete(id);
    }

    private String getInfo(List<Comment> comments) {
        StringBuilder sb = new StringBuilder();
        if (comments.isEmpty()) {
            return "Комментарии отсутствуют";
        }
        sb.append("Комментарии к книге ").append(comments.get(0).getBook().getName()).append("\n");
        for (Comment comment : comments) {
            sb.append(comment.getSendDate()).append(" ").append(comment.getUserName()).append("\n")
                    .append("\t").append(comment.getText()).append("\n");
        }

        return sb.toString();
    }

}
