package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Priority;
import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    @Test
    void shouldAddTask() {
        TaskService service = new TaskService();

        var task = service.addTask("完成需求评审");

        assertEquals(1L, task.getId());
        assertEquals("完成需求评审", task.getTitle());
        assertFalse(task.isCompleted());
        assertEquals(1, service.listAll().size());
    }

    @Test
    void shouldRejectBlankTitle() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
                () -> service.addTask("   "));
    }

    @Nested
    class PriorityFiltering {

        @Test
        void shouldDefaultToMediumPriority() {
            TaskService service = new TaskService();

            var task = service.addTask("默认优先级任务");

            assertEquals(Priority.MEDIUM, task.getPriority());
        }

        @Test
        void shouldKeepExplicitPriority() {
            TaskService service = new TaskService();

            var task = service.addTask("高优先级任务", Priority.HIGH);

            assertEquals(Priority.HIGH, task.getPriority());
        }

        @Test
        void shouldReturnOnlyTasksMatchingPriority() {
            TaskService service = new TaskService();
            service.addTask("高数作业", Priority.HIGH);
            service.addTask("英语听力", Priority.LOW);
            service.addTask("实验报告", Priority.HIGH);
            service.addTask("社团活动", Priority.MEDIUM);

            var highTasks = service.filterByPriority(Priority.HIGH);

            assertEquals(2, highTasks.size());
            assertTrue(highTasks.stream().allMatch(t -> t.getPriority() == Priority.HIGH));
        }

        @Test
        void shouldReturnEmptyListWhenNoTaskMatches() {
            TaskService service = new TaskService();
            service.addTask("高数作业", Priority.HIGH);

            var lowTasks = service.filterByPriority(Priority.LOW);

            assertTrue(lowTasks.isEmpty());
        }

        @Test
        void shouldRejectNullPriority() {
            TaskService service = new TaskService();
            service.addTask("高数作业", Priority.HIGH);

            assertThrows(IllegalArgumentException.class,
                    () -> service.filterByPriority(null));
        }
    }
}
