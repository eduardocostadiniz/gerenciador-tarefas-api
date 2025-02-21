package com.eduardo.gerenciador_tarefas_api.schedulers;

import com.eduardo.gerenciador_tarefas_api.models.Task;
import com.eduardo.gerenciador_tarefas_api.services.TaskService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessorScheduler {

  @Autowired private TaskService taskService;

  @Scheduled(cron = "${gerenciador-tarefas.scheduler.task.task-scheduler-cron}")
  public void listAllTasksEveryFiveMinutes() {
    List<Task> tasks = this.taskService.getAll();
    System.out.println("--------------------------------------------------");
    for (Task task : tasks) {
      System.out.printf(
          "%s | PROJECT: %s | TASK: %s | STATUS: %s \n",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), task.getProject().getName(), task.getName(), task.getStatus());
    }
  }
}
