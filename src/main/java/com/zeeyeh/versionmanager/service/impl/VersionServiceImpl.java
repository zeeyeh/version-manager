package com.zeeyeh.versionmanager.service.impl;

import com.zeeyeh.versionmanager.entity.*;
import com.zeeyeh.versionmanager.repository.ProjectRepository;
import com.zeeyeh.versionmanager.repository.VersionRepository;
import com.zeeyeh.versionmanager.service.VersionService;
import com.zeeyeh.versionmanager.utils.VersionUtil;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {

    @Resource
    ProjectRepository projectRepository;
    @Resource
    VersionRepository versionRepository;

    @Override
    public Version create(long vid, long pid, String name, String url, int status) {
        Version searchVersion = versionRepository.findByVid(vid);
        if (searchVersion != null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_ALREADY_EXISTS);
        }
        Version version = Version.builder()
                .vid(vid)
                .pid(pid)
                .name(name)
                .url(url)
                .status(status)
                .build();
        return versionRepository.saveAndFlush(version);
    }

    @Override
    public Version delete(long vid) {
        Version searchVersion = versionRepository.findByVid(vid);
        if (searchVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        versionRepository.delete(searchVersion);
        versionRepository.flush();
        return searchVersion;
    }

    @Override
    public Version delete(long pid, String name) {
        List<Version> searchVersion = versionRepository.findByName(name);
        if (searchVersion.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        Version targetVersion = null;
        for (Version version : searchVersion) {
            if (version.getPid() == pid) {
                targetVersion = version;
            }
        }
        if (targetVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        versionRepository.delete(targetVersion);
        versionRepository.flush();
        return targetVersion;
    }

    @Override
    public String findUrl(long vid) {
        Version version = versionRepository.findByVid(vid);
        if (version == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        return version.getUrl();
    }

    @Override
    public String findUrl(long pid, String name) {
        List<Version> searchVersion = versionRepository.findByName(name);
        if (searchVersion.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        Version targetVersion = null;
        for (Version version : searchVersion) {
            if (version.getPid() == pid) {
                targetVersion = version;
            }
        }
        if (targetVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        return targetVersion.getUrl();
    }

    @Override
    public Version updateUrl(long vid, String nowUrl) {
        Version version = versionRepository.findByVid(vid);
        if (version == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        version.setUrl(nowUrl);
        return versionRepository.saveAndFlush(version);
    }

    @Override
    public Version updateUrl(long pid, String name, String nowUrl) {
        List<Version> searchVersion = versionRepository.findByName(name);
        if (searchVersion.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        Version targetVersion = null;
        for (Version version : searchVersion) {
            if (version.getPid() == pid) {
                targetVersion = version;
            }
        }
        if (targetVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        targetVersion.setUrl(nowUrl);
        return versionRepository.saveAndFlush(targetVersion);
    }

    @Override
    public Version rename(long vid, String nowName) {
        Version searchVersion = versionRepository.findByVid(vid);
        if (searchVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        searchVersion.setName(nowName);
        return versionRepository.saveAndFlush(searchVersion);
    }

    @Override
    public Version rename(long pid, String oldName, String nowName) {
        List<Version> searchVersion = versionRepository.findByName(oldName);
        if (searchVersion.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        Version targetVersion = null;
        for (Version version : searchVersion) {
            if (version.getPid() == pid) {
                targetVersion = version;
            }
        }
        if (targetVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        targetVersion.setName(nowName);
        return versionRepository.saveAndFlush(targetVersion);
    }

    @Override
    public Version allow(long vid) {
        Version searchVersion = versionRepository.findByVid(vid);
        if (searchVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        searchVersion.setStatus(VersionStatus.ALLOWED);
        return versionRepository.saveAndFlush(searchVersion);
    }

    @Override
    public Version allow(long pid, String name) {
        List<Version> searchVersion = versionRepository.findByName(name);
        if (searchVersion.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        Version targetVersion = null;
        for (Version version : searchVersion) {
            if (version.getPid() == pid) {
                targetVersion = version;
            }
        }
        if (targetVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        targetVersion.setStatus(VersionStatus.ALLOWED);
        return versionRepository.saveAndFlush(targetVersion);
    }

    @Override
    public Version disallow(long vid) {
        Version searchVersion = versionRepository.findByVid(vid);
        if (searchVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        searchVersion.setStatus(VersionStatus.ALLOWED);
        return versionRepository.saveAndFlush(searchVersion);
    }

    @Override
    public Version disallow(long pid, String name) {
        List<Version> searchVersion = versionRepository.findByName(name);
        if (searchVersion.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        Version targetVersion = null;
        for (Version version : searchVersion) {
            if (version.getPid() == pid) {
                targetVersion = version;
            }
        }
        if (targetVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        targetVersion.setStatus(VersionStatus.DISALLOWED);
        return versionRepository.saveAndFlush(targetVersion);
    }

    @Override
    public Version getLatest(long pid) {
        List<Version> allVersionEntities = versionRepository.findByPid(pid);
        if (allVersionEntities.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        VersionUtil.ascentSort(allVersionEntities);
        return allVersionEntities.get(allVersionEntities.size() - 1);
    }

    @Override
    public Version getLatest(String name) {
        Project project = projectRepository.findByName(name);
        if (project == null) {
            throw new RequestHandlerException(ErrorConstant.PROJECT_NOT_FOUND);
        }
        return getLatest(project.getPid());
    }

    @Override
    public Page<Version> page(int number, int size) {
        return versionRepository.findAll(PageRequest.of(number - 1, size));
    }

    @Override
    public Version query(long vid) {
        Version version = versionRepository.findByVid(vid);
        if (version == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        return version;
    }

    @Override
    public Version query(long pid, String name) {
        List<Version> searchVersion = versionRepository.findByName(name);
        if (searchVersion.isEmpty()) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        Version targetVersion = null;
        for (Version version : searchVersion) {
            if (version.getPid() == pid) {
                targetVersion = version;
            }
        }
        if (targetVersion == null) {
            throw new RequestHandlerException(ErrorConstant.VERSION_NOT_FOUND);
        }
        return targetVersion;
    }
}
