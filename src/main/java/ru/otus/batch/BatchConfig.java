package ru.otus.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.batch.domain.AuthorSql;
import ru.otus.batch.domain.BookSql;
import ru.otus.batch.domain.GenreSql;
import ru.otus.batch.repository.AuthorJpaRepository;
import ru.otus.batch.repository.BookJpaRepository;
import ru.otus.batch.repository.GenreJpaRepository;
import ru.otus.entity.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private MongoTemplate mongoTemplate;

    private BookJpaRepository bookRepository;

    private AuthorJpaRepository authorJpaRepository;

    private GenreJpaRepository genreJpaRepository;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       MongoTemplate mongoTemplate,
                       BookJpaRepository bookRepository,
                       AuthorJpaRepository authorJpaRepository,
                       GenreJpaRepository genreJpaRepository
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
        this.bookRepository = bookRepository;
        this.authorJpaRepository = authorJpaRepository;
        this.genreJpaRepository = genreJpaRepository;
    }

    @Bean
    public ItemReader<Book> reader() {
        return new MongoItemReaderBuilder<Book>()
                .name("bookItemReader")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(Collections.singletonMap("name", Sort.Direction.ASC))
                .build();
    }

    public ItemProcessor processor() {
        return (ItemProcessor<Book, BookSql>) book -> {
            BookSql bookSQL = new BookSql();
            bookSQL.setName(book.getName());
            bookSQL.setSummary(book.getSummary());
            List<AuthorSql> authorSqls = new ArrayList<>();
            for (String name : book.getAuthors()) {
                AuthorSql author = authorJpaRepository.findByName(name);
                if (author == null) {
                    author = new AuthorSql(name);
                    authorJpaRepository.save(author);
                }
                authorSqls.add(author);
            }
            bookSQL.setAuthors(authorSqls);
            List<GenreSql> genreSqls = new ArrayList<>();
            for (String name : book.getGenres()) {
                GenreSql genre = genreJpaRepository.findByName(name);
                if (genre == null) {
                    genre = new GenreSql(name);
                    genreJpaRepository.save(genre);
                }
                genreSqls.add(genre);
            }
            bookSQL.setGenres(genreSqls);
            System.out.println(bookSQL);
            return bookSQL;
        };
    }

    @Bean
    public ItemWriter<BookSql> writer() {
        return new RepositoryItemWriterBuilder<BookSql>()
                .repository(bookRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Job importBookJob(Step step1) {
        return jobBuilderFactory.get("importBookJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало миграции");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец миграции");
                    }
                })
                .build();
    }

    @Bean
    public Step step1(ItemWriter writer, ItemReader reader) {
        return stepBuilderFactory.get("step1")
                .chunk(5)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }

}
