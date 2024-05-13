package com.veerajk.demo.services.impl;

import com.veerajk.demo.dtos.SprintDto;
import com.veerajk.demo.dtos.SprintTaskCountResponse;
import com.veerajk.demo.model.*;
import com.veerajk.demo.repo.*;
import com.veerajk.demo.services.SprintService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintServiceImpl implements SprintService {

    SprintRepo sprintRepo;
    TeamRepo teamRepo;

    BacklogRepo backlogRepo;
    BacklogTaskRepo backlogTaskRepo;
    TaskCommentRepo taskCommentRepo;
    TaskRepo taskRepo;
    ColumnRepo columnRepo;
    BacklogTaskCommentRepo backlogTaskCommentRepo;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    public SprintServiceImpl(SprintRepo sprintRepo, TeamRepo teamRepo, BacklogRepo backlogRepo, BacklogTaskRepo backlogTaskRepo, TaskCommentRepo taskCommentRepo, TaskRepo taskRepo, ColumnRepo columnRepo, BacklogTaskCommentRepo backlogTaskCommentRepo) {
        this.sprintRepo = sprintRepo;
        this.teamRepo = teamRepo;
        this.backlogRepo = backlogRepo;
        this.backlogTaskRepo = backlogTaskRepo;
        this.taskCommentRepo = taskCommentRepo;
        this.taskRepo = taskRepo;
        this.columnRepo = columnRepo;
        this.backlogTaskCommentRepo = backlogTaskCommentRepo;
    }

    public SprintDto addSprint(SprintDto sprintDto,Long teamid){
        Sprint sprint = mapToEntity(sprintDto);

        sprint.setTeam(teamRepo.findById(teamid).orElseThrow());

        Column column = new Column();
        column.setTitle("Done");
        column.setLocation(1);
        column.setSprint(sprint); // Set the Sprint reference for the Column
        column.setIsdone(true);

        List<Column> columns = new ArrayList<>();
        columns.add(column);
        sprint.setColumns(columns);
        sprint.setColumns(columns);

        Sprint sprintRes = sprintRepo.save(sprint);
        SprintDto sprintDtoRes = mapToDto(sprintRes);

        return sprintDtoRes;
    }

    public SprintDto getSprint(Long teamid) throws Exception {
        Team team = teamRepo.findById(teamid).orElseThrow(() -> new Exception("Team not found"));
        Sprint sprint = team.getSprint();
        if (sprint == null){
            throw  new Exception("No active sprints!");
        }
        SprintDto sprintDto = mapToDto(sprint);
        return sprintDto;
    }

    public List<SprintDto> getAllSprints() {
        List<Sprint> sprintList = sprintRepo.findAll();
        List<SprintDto> sprintDtos = sprintList.stream().map((sprint -> mapToDto(sprint))).toList();

        return sprintDtos;
    }

    @Override
    public String deleteSprint(Long id) throws Exception {
        Sprint sprint = sprintRepo.findById(id).orElseThrow(()-> new Exception("Sprint not found!"));
        sprintRepo.deleteById(id);
        return "Deleted sprint successfully!";
    }

    @Override
    public SprintTaskCountResponse getTaskCounts(Long teamid) throws Exception{
        Team team = teamRepo.findById(teamid).orElseThrow();
        Sprint sprint = team.getSprint();
        if(sprint==null){
            throw new Exception("No active Sprints");
        }
        long completedTasksCount = 0;
        long remainingTasksCount = 0;

        // Iterate over the columns in the sprint
        for (Column column : sprint.getColumns()) {
            completedTasksCount += column.getTasks().stream()
                    .filter(task -> task.isIscompleted())
                    .count();

            remainingTasksCount += column.getTasks().stream()
                    .filter(task -> !task.isIscompleted())
                    .count();
        }

        return new SprintTaskCountResponse(completedTasksCount, remainingTasksCount);

    }

    @Override
    @Transactional
    public String completeSprintAndMoveTasksToBacklog(Long teamId) throws Exception{
        Team team = teamRepo.findById(teamId).orElseThrow();
        Sprint sprint = team.getSprint();

        if(sprint==null){
            throw new Exception("No active sprints found!");
        }
        Backlog backlog = team.getBacklog();

        List<Task> incompleteTasks = sprint.getColumns().stream()
                .flatMap(column -> column.getTasks().stream())
                .filter(task -> !task.isIscompleted())
                .collect(Collectors.toList());

        List<BacklogTask> backlogTasks = incompleteTasks.stream()
                .map(task -> {
                    BacklogTask backlogTask = new BacklogTask();
                    backlogTask.setId(task.getId());
                    backlogTask.setTitle(task.getTitle());
                    backlogTask.setDescription(task.getDescription());
                    backlogTask.setUser(task.getUser());
                    backlogTask.setType(task.getType());
                    backlogTask.setStoryPoints(task.getStoryPoints());
                    backlogTask.setBacklog(backlog);
                    backlogTask.setSprint(sprint.getName());

                    // Copy comments from the original task to the backlog task
                    List<BacklogTaskComment> backlogComments = task.getComments().stream()
                            .map(comment -> {
                                BacklogTaskComment backlogComment = new BacklogTaskComment();
                                backlogComment.setId(comment.getId());
                                backlogComment.setDescription(comment.getDescription());
                                backlogComment.setUser(comment.getUserid());
                                backlogComment.setBacklogTask(backlogTask);
                                return backlogComment;
                            })
                            .collect(Collectors.toList());

                    backlogTaskRepo.save(backlogTask);
                    backlogTaskCommentRepo.saveAll(backlogComments);
//                    backlogTask.setBacklogTaskComments(backlogComments);
                    return backlogTask;
                })
                .collect(Collectors.toList());


        entityManager.flush();
        entityManager.clear();
        // Delete Comments
        for (Column column : sprint.getColumns()) {
            for (Task task : column.getTasks()) {
                for (TaskComment comment : task.getComments()) {
                   taskCommentRepo.delete(comment);
                }
            }
        }

        for (Column column : sprint.getColumns()) {
            for (Task task : column.getTasks()) {
                taskRepo.delete(task);
            }
        }

        for (Column column : sprint.getColumns()) {
            columnRepo.delete(column);
        }

        entityManager.flush();
        entityManager.clear();
        // Delete Sprint
        sprintRepo.deleteSprint(sprint.getId());

        return "Sprint Completed Successfully!";
    }

    protected SprintDto mapToDto(Sprint sprint){
        SprintDto sprintDto = new SprintDto();
        sprintDto.setId(sprint.getId());
        sprintDto.setName(sprint.getName());
        sprintDto.setStartdate(sprint.getStartdate());
        sprintDto.setDuration(sprint.getDuration());
        sprintDto.setDescription(sprint.getDescription());
        sprintDto.setEnddate(sprint.getEnddate());
        return sprintDto;
    }

    protected Sprint mapToEntity(SprintDto sprintDto){
        Sprint sprint = new Sprint();
        sprint.setName(sprintDto.getName());
        sprint.setDescription(sprintDto.getDescription());
        sprint.setStartdate(sprintDto.getStartdate());
        sprint.setDuration(sprintDto.getDuration());
        sprint.setEnddate(sprintDto.getEnddate());
        return sprint;
    }
}
