package com.zeeyeh.versionmanager.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.zeeyeh.versionmanager.entity.ErrorConstant;
import com.zeeyeh.versionmanager.entity.Project;
import com.zeeyeh.versionmanager.entity.ProjectStatus;
import com.zeeyeh.versionmanager.entity.RequestHandlerException;
import com.zeeyeh.versionmanager.repository.ProjectRepository;
import com.zeeyeh.versionmanager.service.ProjectService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    public ProjectRepository projectRepository;

    @Override
    public Project create(long pid, String name, List<String> authors, int status) {
        Project searchProject = projectRepository.findByPid(pid);
        if (searchProject != null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_ALREADY_EXISTS);
        }
        JSONArray objects = new JSONArray();
        objects.addAll(authors);
        Project project = Project.builder()
                .pid(pid)
                .name(name)
                .authors(objects.toJSONString())
                .status(status)
                .build();
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public Project delete(long pid) {
        Project searchProject = projectRepository.findByPid(pid);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        projectRepository.delete(searchProject);
        projectRepository.flush();
        return searchProject;
    }

    @Override
    public Project delete(String name) {
        Project searchProject = projectRepository.findByName(name);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        projectRepository.delete(searchProject);
        projectRepository.flush();
        return searchProject;
    }

    @Override
    public Project addAuthor(String projectName, String authorName) {
        Project searchProject = projectRepository.findByName(projectName);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        JSONArray objects = JSONArray.parseArray(searchProject.getAuthors());
        if (objects.contains(authorName)) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_ALREADY_EXISTS);
        }
        objects.add(authorName);
        searchProject.setAuthors(objects.toJSONString());
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project addAuthor(long pid, String authorName) {
        Project searchProject = projectRepository.findByPid(pid);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        JSONArray objects = JSONArray.parseArray(searchProject.getAuthors());
        if (objects.contains(authorName)) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_AUTHOR_ALREADY_EXISTS);
        }
        objects.add(authorName);
        searchProject.setAuthors(objects.toJSONString());
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project deleteAuthor(String projectName, String authorName) {
        Project searchProject = projectRepository.findByName(projectName);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        JSONArray objects = JSONArray.parseArray(searchProject.getAuthors());
        if (!objects.contains(authorName)) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_AUTHOR_NOT_FOUND);
        }
        objects.remove(authorName);
        searchProject.setAuthors(objects.toJSONString());
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project deleteAuthor(long pid, String authorName) {
        Project searchProject = projectRepository.findByPid(pid);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        JSONArray objects = JSONArray.parseArray(searchProject.getAuthors());
        if (!objects.contains(authorName)) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_AUTHOR_NOT_FOUND);
        }
        objects.remove(authorName);
        searchProject.setAuthors(objects.toJSONString());
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project active(long pid) {
        Project searchProject = projectRepository.findByPid(pid);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        searchProject.setStatus(ProjectStatus.ACTIVE);
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project active(String name) {
        Project searchProject = projectRepository.findByName(name);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        searchProject.setStatus(ProjectStatus.ACTIVE);
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project inactive(long pid) {
        Project searchProject = projectRepository.findByPid(pid);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        searchProject.setStatus(ProjectStatus.INACTIVE);
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project inactive(String name) {
        Project searchProject = projectRepository.findByName(name);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        searchProject.setStatus(ProjectStatus.INACTIVE);
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project deserted(long pid) {
        Project searchProject = projectRepository.findByPid(pid);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        searchProject.setStatus(ProjectStatus.DESERTED);
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Project deserted(String name) {
        Project searchProject = projectRepository.findByName(name);
        if (searchProject == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        searchProject.setStatus(ProjectStatus.DESERTED);
        return projectRepository.saveAndFlush(searchProject);
    }

    @Override
    public Page<Project> page(int number, int size) {
        number = number <= 0 ? 0 : --number;
        return projectRepository.findAll(PageRequest.of(number, size));
    }

    @Override
    public Project query(long pid) {
        Project project = projectRepository.findByPid(pid);
        if (project == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        return project;
    }

    @Override
    public Project query(String name) {
        Project project = projectRepository.findByName(name);
        if (project == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        return project;
    }

    @Override
    public String getInfos(long pid) {
        return null;
    }

    @Override
    public String getInfos(String name) {
        return null;
    }
}
