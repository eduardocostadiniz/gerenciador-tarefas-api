package com.eduardo.gerenciador_tarefas_api.schedulers;

import com.eduardo.gerenciador_tarefas_api.models.Task;
import com.eduardo.gerenciador_tarefas_api.services.TaskService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskProcessorScheduler {

  @Autowired private TaskService taskService;
  @Autowired private FF4j ff4j;

  @Scheduled(cron = "${gerenciador-tarefas.scheduler.task.task-scheduler-cron}")
  public void listAllTasksEveryFiveMinutes() {
    if (!ff4j.check("is-scheduling_enabled")) {
      log.info("Feature is-scheduling_enabled is not enabled. Skipping...");
      return;
    }

    final List<Task> tasks = this.taskService.getAll();
    log.info("--------------------------------------------------");
    for (Task task : tasks) {
      log.info(
          "{} | PROJECT: {} | TASK: {} | STATUS: {} \n",
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
          task.getProject().getName(),
          task.getName(),
          task.getStatus());
    }
  }
}
