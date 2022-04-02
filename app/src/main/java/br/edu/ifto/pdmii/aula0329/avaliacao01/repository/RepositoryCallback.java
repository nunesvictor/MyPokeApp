package br.edu.ifto.pdmii.aula0329.avaliacao01.repository;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
