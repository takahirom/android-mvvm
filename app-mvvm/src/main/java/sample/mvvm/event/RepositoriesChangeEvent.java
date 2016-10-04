package sample.mvvm.event;

import java.util.List;

import sample.github.model.Repository;

public class RepositoriesChangeEvent {
    public final List<Repository> repositories;
    public RepositoriesChangeEvent(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
