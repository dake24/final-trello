package kz.didar.trello.repository;

import jakarta.transaction.Transactional;
import kz.didar.trello.models.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoldersRepository extends JpaRepository<Folders, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM folders_task_categories WHERE folder_id = :folderId", nativeQuery = true)
    void clearCategoriesForFolder(@Param("folderId") Long folderId);
}
