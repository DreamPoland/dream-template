package cc.dreamcode.template.persistence;

public interface PersistenceInitializer<V extends Repository> {

    V getRepositoryService();

}
