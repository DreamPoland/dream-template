package cc.dreamcode.template.persistence;

public interface RepositoryInitializer<V extends Repository> {

    V getRepositoryService();

}
