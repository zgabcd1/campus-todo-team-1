package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Priority;
import edu.hbuas.campustodo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务应用服务。学生将在功能分支中逐步扩展该类。
 */
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public Task addTask(String title) {
        return addTask(title, Priority.MEDIUM);
    }

    public Task addTask(String title, Priority priority) {
        Task task = new Task(nextId++, title, priority);
        tasks.add(task);
        return task;
    }

    public List<Task> listAll() {
        return List.copyOf(tasks);
    }

    /**
     * 按优先级筛选任务。
     *
     * @param priority 目标优先级，不允许为 null
     * @return 命中任务快照；无命中时返回空列表
     */
    public List<Task> filterByPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("优先级不能为空");
        }
        List<Task> matched = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                matched.add(task);
            }
        }
        return List.copyOf(matched);
    }
}
