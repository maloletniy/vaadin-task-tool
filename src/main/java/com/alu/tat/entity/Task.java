package com.alu.tat.entity;

import com.alu.tat.entity.schema.Schema;

import javax.persistence.*;

/**
 * Created by imalolet on 6/10/2015.
 */
@NamedQueries({
        @NamedQuery(
                name = "findTaskByFolder",
                query = "from Task t where t.folder = :folder"
        ),
        @NamedQuery(
                name = "findTaskBySchema",
                query = "from Task t where t.schema = :schema"
        ),
        @NamedQuery(
                name = "findTaskByUser",
                query = "from Task t where t.author = :user"
        ),

        @NamedQuery(
                name = "findTasksWOStatus",
                query = "from Task t where t.status is null"
        )
})
@Entity
@Table(name = "task")
public class Task extends BaseEntity {
    public enum Status {
        NEW("NEW"), DONE("DONE");

        private String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    ;

    @Column(name = "name")
    private String name;

    @Column(name = "descr", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schema_id")
    private Schema schema;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Task) o).name);
    }
}
