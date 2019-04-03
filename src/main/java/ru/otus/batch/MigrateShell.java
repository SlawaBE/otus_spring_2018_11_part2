package ru.otus.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MigrateShell {

    private JobLauncher jobLauncher;
    private Job importBookJob;

    public MigrateShell(JobLauncher jobLauncher, Job importBookJob) {
        this.jobLauncher = jobLauncher;
        this.importBookJob = importBookJob;
    }

    @ShellMethod("Миграция в SQL БД")
    public void migrate() throws Exception {
        jobLauncher.run(importBookJob, new JobParametersBuilder().toJobParameters());
    }
}
