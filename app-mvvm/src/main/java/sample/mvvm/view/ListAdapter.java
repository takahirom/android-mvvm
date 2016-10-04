package sample.mvvm.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import sample.github.model.Repository;
import sample.mvvm.R;
import sample.mvvm.databinding.ListItemBinding;
import sample.mvvm.di.ActivityComponent;
import sample.mvvm.viewmodel.ListItemViewModel;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ActivityComponent component;
    private List<Repository> repositories;

    ListAdapter(ActivityComponent component) {
        this.component = component;
        this.repositories = Collections.emptyList();
    }

    void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item,
                parent,
                false
        );
        return new ViewHolder(binding, component);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ListItemBinding binding;
        final ActivityComponent component;

        ViewHolder(ListItemBinding binding, ActivityComponent component) {
            super(binding.getRoot());
            this.binding = binding;
            this.component = component;
        }
        void bind(Repository repository) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ListItemViewModel(component, repository));
            } else {
                binding.getViewModel().setRepository(repository);
            }
        }
    }

}
